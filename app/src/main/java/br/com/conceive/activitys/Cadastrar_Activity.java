package br.com.conceive.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import br.com.conceive.R;

/**
 * Created by denis on 04/12/2016.
 */

public class Cadastrar_Activity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton rb_arquiteto;
    private RadioButton rb_cliente;
    private EditText edt_nome;
    private EditText edt_email;
    private EditText edt_cau;
    private EditText edt_telefone;
    private EditText edt_senha;
    private EditText edt_repete_senha;
    private TextView voltar_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        iniViews();

        rb_arquiteto.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==rb_arquiteto.getId()){
                    edt_cau.setVisibility(View.VISIBLE);
                } else if(radioGroup.getCheckedRadioButtonId() == rb_cliente.getId()){
                    edt_cau.setVisibility(View.GONE);
                }
            }
        });

       int op = radioGroup.getCheckedRadioButtonId();

        if(op == rb_arquiteto.getId()){

        } else if(op == rb_cliente.getId()){

        }

        voltar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastrar_Activity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }


    private void iniViews(){
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_arquiteto = (RadioButton) findViewById(R.id.rb_aquiteto);
        rb_cliente = (RadioButton) findViewById(R.id.rb_cliente);
        edt_nome = (EditText) findViewById(R.id.edt_cadastro_nome);
        edt_email = (EditText) findViewById(R.id.edt_cadastro_email);
        edt_cau = (EditText) findViewById(R.id.edt_cadastro_cau);
        edt_senha = (EditText) findViewById(R.id.edt_cadastro_senha);
        edt_repete_senha = (EditText) findViewById(R.id.edt_cadastro_repete_senha);
        voltar_login = (TextView) findViewById(R.id.voltar_login);
    }

}
