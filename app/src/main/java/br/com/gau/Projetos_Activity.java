package br.com.gau;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import br.com.gau.POJO.Projeto;
import br.com.gau.adapter.Adapter_Projetos;

public class Projetos_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lista_projetos;
    private Adapter_Projetos adapter;
    private ArrayList<Projeto> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projetos_activity);

        lista = new ArrayList<>();

        String root_sd = Environment.getExternalStorageDirectory().toString();

        File dir = new File(root_sd+"/gau/");
        File[] file_list = dir.listFiles();
        String[] nomes_pastas = new String[file_list.length];

        for(int i=0;i < nomes_pastas.length;i++){
            Projeto projeto = new Projeto();
            projeto.setNome_cliente("Denis Viana Costa");
            projeto.setNome_projeto(file_list[i].getName());
            projeto.setEtapa_atual("Projeto Executivo");
            projeto.setData_inicio("13/11/2016");
            lista.add(projeto);
        }

        lista_projetos = (ListView) findViewById(R.id.list_projetos);


        adapter = new Adapter_Projetos(lista,Projetos_Activity.this);

        lista_projetos.setAdapter(adapter);

        //lista_projetos.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista_));


        lista_projetos.setOnItemClickListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       if(item.getItemId() == R.id.menu_add_projetos){
            startActivity(new Intent(Projetos_Activity.this,Criar_Projeto_Activity.class));
       }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_projetos,menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Projetos_Activity.this, Visualizacao_Projeto_Activity.class);
        Projeto projeto = (Projeto)adapter.getItem(i);
        intent.putExtra("projeto",projeto);
        startActivity(intent);
    }
}
