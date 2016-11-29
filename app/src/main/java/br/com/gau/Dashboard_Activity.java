package br.com.gau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.gau.POJO.Item_DashBoard;
import br.com.gau.adapter.Adapter_dashboard;

public class Dashboard_Activity extends AppCompatActivity {

    private ListView list_dashboard;
    private Adapter_dashboard adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setTitle("Dashboard");

        initViews();

        ArrayList<Item_DashBoard> lista_itens = new ArrayList<>();

        Item_DashBoard item = new Item_DashBoard();
        item.setItemnome("Projetos");
        item.setCor(R.color.colorProjetos);
        lista_itens.add(item);

        item = new Item_DashBoard();
        item.setItemnome("Meu Portfólio");
        item.setCor(R.color.colorPortfolio);
        lista_itens.add(item);

        item = new Item_DashBoard();
        item.setItemnome("Acervo de Referências");
        item.setCor(R.color.colorAcervo);
        lista_itens.add(item);

        item = new Item_DashBoard();
        item.setItemnome("Configurações");
        item.setCor(R.color.colorConfig);
        lista_itens.add(item);

        adapter = new Adapter_dashboard(this,lista_itens);

        list_dashboard.setAdapter(adapter);

        list_dashboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        Intent intent = new Intent(Dashboard_Activity.this,Projetos_Activity.class);
                        startActivity(intent);
                }

            }
        });



    }

    private void initViews(){
        list_dashboard = (ListView) findViewById(R.id.dashboard_list);
    }

}
