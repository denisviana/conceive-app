package br.com.conceive.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class FinalizarCadastro_Activity extends AppCompatActivity implements View.OnClickListener{

    private Arquiteto arquiteto;
    private CircularImageView imagem_perfil;
    private TextView nome_usuario;
    private EditText edt_cau;
    private EditText edt_telefone;
    private Button bt_completar_registro;
    private Retrofit retrofit;
    private ProgressDialog progress;
    private String token;

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

    private void salvarLocal(Arquiteto arq){

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();

        if(arquiteto!=null){
            realm.beginTransaction();
            Arquiteto arquiteto = realm.copyToRealm(arq);
            realm.commitTransaction();
        }

    }

    private void cadastrarArquiteto(String token, final Arquiteto arquiteto){

        retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setTitle("Aguarde...");
        progress.show();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<Integer> requestArquiteto = retrofitInterface.cadastrarArquiteto(token,arquiteto);

        requestArquiteto.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code()==200){
                    if(response.body() != -1){
                        arquiteto.setId(response.body());
                        salvarLocal(arquiteto);
                        Intent intent = new Intent(FinalizarCadastro_Activity.this,PermissaoDrive_Activity.class);
                        startActivity(intent);
                        if(progress.isShowing()){
                            progress.cancel();
                        }
                        FinalizarCadastro_Activity.this.finish();
                    }else
                        Toast.makeText(getApplicationContext(),"Falha ao salvar no servidor",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Falha ao salvar no servidor",Toast.LENGTH_LONG).show();
            }
        });
        if(progress.isShowing()){
            progress.cancel();
        }

    }
}