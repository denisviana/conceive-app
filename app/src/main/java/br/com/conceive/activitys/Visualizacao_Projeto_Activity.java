package br.com.conceive.activitys;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import br.com.conceive.POJO.Etapa_Projeto;
import br.com.conceive.POJO.Projeto;
import br.com.conceive.R;
import br.com.conceive.adapter.Adapter_Etapas_Projeto;

/**
 * Created by Denis Viana on 30/11/2016.
 */

public class Visualizacao_Projeto_Activity extends AppCompatActivity {

    private ArrayList<Etapa_Projeto> etapas;
    private Adapter_Etapas_Projeto adapter;
    private GridView grid_etapas;
    private Projeto projeto;
    private Toolbar toolbar;
    private TextView nome_projeto,
            nome_cliente,
            data_inicio;
    private AlertDialog dialog_add_etapa;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao_projeto);

        initViews();

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        etapas = new ArrayList<>();

        if(getIntent().getExtras()!=null){
            projeto = (Projeto) getIntent().getSerializableExtra("projeto");
            nome_projeto.setText(projeto.getNome_projeto());
            nome_cliente.setText(projeto.getNome_cliente());
            data_inicio.setText(projeto.getData_inicio());
        }

        String root_sd = Environment.getExternalStorageDirectory().toString();

        File dir = new File(root_sd+"/gau/"+projeto.getNome_projeto()+"/");
        File[] file_list = dir.listFiles();
        String[] nomes_etapas = new String[file_list.length];

        for(int i=0;i < nomes_etapas.length;i++){
            Etapa_Projeto etapa = new Etapa_Projeto();
            etapa.setNome_etapa(file_list[i].getName());
            etapas.add(etapa);
        }


        grid_etapas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Etapa_Projeto etapa = (Etapa_Projeto) adapter.getItem(i);
                    Intent intent = new Intent(Visualizacao_Projeto_Activity.this,Etapa_Activity.class);
                    intent.putExtra("etapa",etapa);
                    startActivity(intent);
            }
        });

        adapter = new Adapter_Etapas_Projeto(this,etapas);

        grid_etapas.setAdapter(adapter);

    }

    private void addEtapa (){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_projeto,null);

        final EditText edit_add_etapa = (EditText) view.findViewById(R.id.edit_add_etapa);
        Button bt_add_etapa = (Button) view.findViewById(R.id.bt_add_etapa);
        Button bt_cancel_etapa = (Button) view.findViewById(R.id.bt_cancel_etapa);

        bt_add_etapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_add_etapa.getText().length()==0){
                    Toast.makeText(Visualizacao_Projeto_Activity.this,"Digite o nome da etapa",Toast.LENGTH_LONG).show();
                } else {
                    String path = Environment.getExternalStorageDirectory()+"/gau/"+projeto.getNome_projeto()+"/"+edit_add_etapa.getText();
                    new File(path).mkdir();
                    Etapa_Projeto etapa = new Etapa_Projeto();
                    etapa.setNome_etapa(edit_add_etapa.getText().toString());
                    etapas.add(etapa);
                    adapter = new Adapter_Etapas_Projeto(Visualizacao_Projeto_Activity.this,etapas);
                    grid_etapas.setAdapter(adapter);
                    Toast.makeText(Visualizacao_Projeto_Activity.this,"Etapa criada com sucesso",Toast.LENGTH_LONG).show();
                    dialog_add_etapa.dismiss();
                }
            }
        });

        bt_cancel_etapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_add_etapa.dismiss();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogStyle);
        builder.setTitle("Adicionar Etapa");


        builder.setView(view);

        dialog_add_etapa = builder.create();
        dialog_add_etapa.show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_add_etapa){
            addEtapa();
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_projeto_interno,menu);

        return true;
    }

    private void initViews(){
        grid_etapas = (GridView) findViewById(R.id.grid_etapas_projeto);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nome_projeto = (TextView) findViewById(R.id.titulo_projeto_interno);
        nome_cliente = (TextView) findViewById(R.id.nome_cliente_projeto_interno);
        data_inicio = (TextView) findViewById(R.id.data_inicio_projeto_interno);
    }


}
