package br.com.conceive.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.conceive.POJO.Arquiteto;
import br.com.conceive.R;

/**
 * Created by denis on 20/12/2016.
 */

public class FinalizarCadastro_Activity extends AppCompatActivity {

    private Arquiteto arquiteto;
    private CircularImageView imagem_perfil;
    private TextView nome_usuario;
    private EditText edt_cau;
    private EditText edt_telefone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pos_login_);

        iniViews();

        if(getIntent().getExtras()!=null){
            arquiteto = (Arquiteto) getIntent().getSerializableExtra("arquiteto");
            preencheInfo(arquiteto);
        }


    }

    public void iniViews(){
        imagem_perfil = (CircularImageView) findViewById(R.id.foto_usuario_finalizar_cadastro);
        nome_usuario = (TextView) findViewById(R.id.txt_completar_cadastro);
        edt_cau = (EditText) findViewById(R.id.edt_cau_completar_cadastro);
        edt_telefone = (EditText) findViewById(R.id.edt_telefone_completar_cadastro);
    }

    public void preencheInfo(Arquiteto arquiteto){

        Picasso.with(FinalizarCadastro_Activity.this).load(arquiteto.getUri_foto()).into(imagem_perfil);
        nome_usuario.setText("Olá "+arquiteto.getNome());

    }
}
