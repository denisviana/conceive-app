package br.com.conseive;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Denis Viana on 29/11/2016.
 */

public class Criar_Projeto_Activity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_nome_projeto;
    private Button bt_add_projeto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_projeto);

        initViews();

        bt_add_projeto.setOnClickListener(this);

    }

    private void initViews(){
        edit_nome_projeto = (EditText) findViewById(R.id.edit_nome_projeto);
        bt_add_projeto = (Button) findViewById(R.id.bt_add_projeto);
    }

    @Override
    public void onClick(View view) {
        if(edit_nome_projeto.getText().length()==0){
            Toast.makeText(Criar_Projeto_Activity.this,"Digite o nome do projeto", Toast.LENGTH_LONG).show();
        }else{
            new File(Environment.getExternalStorageDirectory()+"/gau/"+edit_nome_projeto.getText()).mkdir();
            Toast.makeText(Criar_Projeto_Activity.this,"Projeto criado com sucesso", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
