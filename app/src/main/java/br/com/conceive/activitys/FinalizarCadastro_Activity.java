package br.com.conceive.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.R;
import br.com.conceive.dao.ArquitetoDAO;
import br.com.conceive.dao.ArquitetoRetrofit;
import br.com.conceive.dao.RetrofitWebService;
import br.com.conceive.retrofit.RetrofitInterface;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by denis on 20/12/2016.
 */

public class FinalizarCadastro_Activity extends RetrofitWebService implements View.OnClickListener{

    private static final String TAG = "Finalizar Cadastro: ";
    private Arquiteto arquiteto;
    private CircularImageView imagem_perfil;
    private TextView nome_usuario;
    private EditText edt_cau;
    private EditText edt_telefone;
    private Button bt_completar_registro;
    private ArquitetoDAO arquitetoDAO;
    private String token;
    private ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pos_login_);

        iniViews();

        if(getIntent().getExtras()!=null){
            token = getIntent().getStringExtra("token");
            arquiteto = (Arquiteto) getIntent().getSerializableExtra("arquiteto");
            preencheInfo(arquiteto);
        }

        bt_completar_registro.setOnClickListener(this);

        progress = new ProgressDialog(this);
        progress.setTitle("Aguarde...");
        progress.setIndeterminate(true);

    }

    public void iniViews(){
        imagem_perfil = (CircularImageView) findViewById(R.id.foto_usuario_finalizar_cadastro);
        nome_usuario = (TextView) findViewById(R.id.txt_completar_cadastro);
        edt_cau = (EditText) findViewById(R.id.edt_cau_completar_cadastro);
        edt_telefone = (EditText) findViewById(R.id.edt_telefone_completar_cadastro);
        bt_completar_registro = (Button) findViewById(R.id.bt_completar_registro);
    }

    public void preencheInfo(Arquiteto arquiteto){

        Picasso.with(FinalizarCadastro_Activity.this).load(arquiteto.getUri_foto()).into(imagem_perfil);
        nome_usuario.setText("Olá "+arquiteto.getNome());

    }

    @Override
    public void onClick(View view) {
        if(view == bt_completar_registro){
            if(edt_cau.getText().toString().equalsIgnoreCase("")){
                edt_cau.setError("Oi, esse campo é obrigatório.");
            } else if(edt_cau.getTextSize()<8){
                edt_cau.setError("O registro do CAU precisa ter 8 dígitos");
            }else {
                arquiteto.setCau(Integer.parseInt(edt_cau.getText().toString()));
                arquiteto.setTelefone(edt_telefone.getText().toString());
                cadastrarArquiteto(token,arquiteto);


            }
        }
    }

    private boolean salvarLocal(Arquiteto arquiteto){

        arquitetoDAO = new ArquitetoDAO(this);

        if(arquiteto!=null){
            return arquitetoDAO.novoArquiteto(arquiteto);
        } else
        return false;
    }

    private void cadastrarArquiteto(final String token, final Arquiteto arquiteto){

        showProgress();

        Call<Integer> requestArquiteto = getRetrofitInterface().cadastrarArquiteto(token,arquiteto);

        requestArquiteto.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch(response.code()){
                    case OK:
                        Log.i(TAG,"Sucesso");
                        if(response.body() != -1){
                            arquiteto.setId(response.body());

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if(salvarLocal(arquiteto)){
                                        Log.i(TAG,"Arquiteto salvo no BD local");
                                    }else{
                                        Log.i(TAG,"Arquiteto não foi salvo no BD local");
                                    }
                                }
                            }).start();

                            Intent intent = new Intent(FinalizarCadastro_Activity.this,PermissaoDrive_Activity.class);
                            intent.putExtra("arquiteto",arquiteto);
                            intent.putExtra("token",token);
                            startActivity(intent);
                            finishProgress();
                            FinalizarCadastro_Activity.this.finish();
                        }
                        break;
                    case NO_CONTENT:
                        Log.i(TAG,"Sem Conteúdo");
                        finishProgress();
                        break;
                    case FORBBIDEN:
                        Log.i(TAG,"Validação de token negada");
                        finishProgress();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i(TAG,"Falha na comunicação com o WebService");
                finishProgress();
            }
        });

        finishProgress();
    }

    public void showProgress(){
        if(!progress.isShowing()){
            progress.show();
        }
    }

    public void finishProgress(){
        if(progress.isShowing()){
            progress.dismiss();
        }
    }
}