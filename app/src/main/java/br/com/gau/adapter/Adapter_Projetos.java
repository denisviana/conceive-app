package br.com.gau.adapter;

import android.content.Context;

import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import br.com.gau.POJO.Projeto;
import br.com.gau.R;

/**
 * Created by Denis Viana on 29/11/2016.
 */

public class Adapter_Projetos extends BaseAdapter {

    private ArrayList<Projeto> lista_projetos;
    private Context contexto;

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
        Projeto projeto = lista_projetos.get(i);

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
        ImageButton bt_editar_projeto = (ImageButton) layout.findViewById(R.id.bt_edit_projeto);
        LinearLayout bg_item_projeto = (LinearLayout) layout.findViewById(R.id.backgroud_item_projeto);


        Color color = new Color();

        int cor = color.parseColor(projeto.getCor());

        bg_item_projeto.setBackgroundColor(cor);

        nome_projeto.setText(projeto.getNome_projeto());
        nome_cliente.setText(projeto.getNome_cliente());
        data_inicio.setText(projeto.getData_inicio());
        etapa_atual.setText(projeto.getEtapa_atual());

        return layout;
    }
}
