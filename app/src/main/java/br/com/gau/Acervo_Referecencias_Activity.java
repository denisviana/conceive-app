package br.com.gau;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;

import java.util.ArrayList;

import br.com.gau.POJO.Item_Acervo;
import br.com.gau.adapter.Adapter_Acervo_Referencias;

/**
 * Created by Denis Viana on 02/12/2016.
 */

public class Acervo_Referecencias_Activity extends AppCompatActivity {

    private GridView grid_acervo;
    private ArrayList<Item_Acervo> lista;
    private Adapter_Acervo_Referencias adapter;
    private Item_Acervo item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AcervoTheme);
        setContentView(R.layout.activity_acervo_de_referencias);

        initViews();

        lista = new ArrayList<>();

        item = new Item_Acervo();
        item.setNome("Quarto Criança");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Varandas");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Madeira");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Concreto");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Metal");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Salas de Jantar");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Abajures");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Escadas");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Vidro");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Cadeiras");
        lista.add(item);

        item = new Item_Acervo();
        item.setNome("Piscinas");
        lista.add(item);

        adapter = new Adapter_Acervo_Referencias(this,lista);

        grid_acervo.setAdapter(adapter);

    }

    public void initViews(){
        grid_acervo = (GridView) findViewById(R.id.grid_acervo_referencias);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_acervo_referencias,menu);

        return true;
    }
}
