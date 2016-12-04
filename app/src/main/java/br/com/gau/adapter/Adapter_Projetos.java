package br.com.gau.adapter;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


import br.com.gau.POJO.Projeto;
import br.com.gau.R;
import br.com.gau.Visualizacao_Projeto_Activity;

/**
 * Created by Denis Viana on 29/11/2016.
 */

public class Adapter_Projetos extends BaseAdapter {

    private ArrayList<Projeto> lista_projetos;
    private Context contexto;
    private Intent intent;
    private Projeto projeto;

    public Adapter_Projetos(ArrayList<Projeto> lista_projetos, Context context){
        this.lista_projetos = lista_projetos;
        this.contexto = context;
    }

    @Override
    public int getCount() {
        return lista_projetos.size();
    }

    @Override
    public Object getItem(int i) {
        return lista_projetos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        projeto = lista_projetos.get(i);
        intent = new Intent(contexto, Visualizacao_Projeto_Activity.class);


        View layout;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.layout_item_projeto,null);
        } else
            layout = view;

        TextView nome_projeto = (TextView) layout.findViewById(R.id.text_item_nome_projeto);
        TextView nome_cliente = (TextView) layout.findViewById(R.id.text_item_cliente_projeto);
        TextView data_inicio = (TextView) layout.findViewById(R.id.text_item_data_inicio_projeto);
        TextView etapa_atual = (TextView) layout.findViewById(R.id.text_item_etapa_atual_projeto);
        LinearLayout bg_item_projeto = (LinearLayout) layout.findViewById(R.id.backgroud_item_projeto);


        nome_projeto.setText("Nome: "+projeto.getNome_projeto());
        nome_cliente.setText("Cliente: "+projeto.getNome_cliente());
        data_inicio.setText("Data de início: "+projeto.getData_inicio());
        etapa_atual.setText("Etapa atual: "+projeto.getEtapa_atual());

        Color color = new Color();
        int cor = color.parseColor(projeto.getCor());

        bg_item_projeto.setBackgroundColor(cor);

        return layout;
    }
}
