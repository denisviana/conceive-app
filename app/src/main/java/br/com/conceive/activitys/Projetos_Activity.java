package br.com.conceive.activitys;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

import br.com.conceive.POJO.Projeto;
import br.com.conceive.R;
import br.com.conceive.adapter.Adapter_Projetos;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Projetos_Activity extends AppCompatActivity {

    private RecyclerView lista_projetos;
    private Adapter_Projetos adapter;
    private ArrayList<Projeto> lista;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projetos_activity);

        Realm.init(getApplicationContext());
        realmConfig = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfig);

        lista = new ArrayList<>();

        RealmResults<Projeto> realmResults = realm.where(Projeto.class).findAll();

        for(Projeto projeto: realmResults){
            Projeto pj = projeto;
            Log.i("Objeto Realm",projeto.getNome_projeto());
            lista.add(pj);
        }

        /*
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
        }  */

        lista_projetos = (RecyclerView) findViewById(R.id.list_projetos);

        adapter = new Adapter_Projetos(lista,Projetos_Activity.this);

        lista_projetos.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lista_projetos.setLayoutManager(layoutManager);

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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.menu_busca_projetos);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Pesquisar Projetos");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Projeto> filteredProjects = filter(lista,newText);
                adapter.setFilter(filteredProjects);
                return true;
            }
        });


        return true;
    }

    private List<Projeto> filter(List<Projeto> projetos, String query){
        query = query.toLowerCase();
        final List<Projeto> filteredProjects = new ArrayList<>();

        for(Projeto projeto : projetos){
            final String text = projeto.getNome_projeto().toLowerCase();
            if(text.contains(query)){
                filteredProjects.add(projeto);
            }
        }
        return filteredProjects;
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(Projetos_Activity.this, Visualizacao_Projeto_Activity.class);
        Projeto projeto = (Projeto)adapter.getItem(i);
        intent.putExtra("projeto",projeto);
        startActivity(intent);
    } */
}
