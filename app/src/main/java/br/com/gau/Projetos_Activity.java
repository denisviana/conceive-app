package br.com.gau;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.gau.POJO.Projeto;
import br.com.gau.adapter.Adapter_Projetos;

public class Projetos_Activity extends AppCompatActivity {

    private ListView lista_projetos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projetos_activity);
        LinearLayout ac_projetos = (LinearLayout) findViewById(R.id.activity_projetos);

        lista_projetos = (ListView) findViewById(R.id.list_projetos);

        ArrayList<Projeto> lista = new ArrayList<>();

        Projeto projeto = new Projeto();
        projeto.setNome_cliente("Denis Viana Costa");
        projeto.setNome_projeto("Sala de Jantar");
        projeto.setEtapa_atual("Projeto Executivo");
        projeto.setData_inicio("13/11/2016");
        lista.add(projeto);

        projeto = new Projeto();
        projeto.setNome_cliente("Ramon Felipe");
        projeto.setNome_projeto("Cozinha");
        projeto.setEtapa_atual("Sem Etapas");
        projeto.setData_inicio("11/10/2016");
        lista.add(projeto);

        lista_projetos.setAdapter(new Adapter_Projetos(lista,this));


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
}
