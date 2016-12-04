package br.com.gau;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by denis on 04/12/2016.
 */
public class Login_Activity extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private Button bt_login;
    private TextView cadastrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edt_email.getText().equals("teste") || !edt_senha.getText().equals("teste")){
                    Intent intent = new Intent(Login_Activity.this,Dashboard_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
                }
            }
        });


        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,Cadastrar_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void initView(){
        edt_email = (EditText) findViewById(R.id.idt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        bt_login = (Button) findViewById(R.id.bt_login);
        cadastrar = (TextView) findViewById(R.id.bt_cadastrar);
    }
}
