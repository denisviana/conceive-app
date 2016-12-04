package br.com.gau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.gau.POJO.Etapa_Projeto;
import br.com.gau.R;

/**
 * Created by Denis Viana on 30/11/2016.
 */

public class Adapter_Etapas_Projeto extends BaseAdapter {

    private ArrayList<Etapa_Projeto> etapas;
    private Context contexto;

    public Adapter_Etapas_Projeto(Context contexto, ArrayList<Etapa_Projeto> etapas){
        this.contexto = contexto;
        this.etapas = etapas;
    }
    @Override
    public int getCount() {
        return etapas.size();
    }

    @Override
    public Object getItem(int i) {
        return etapas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Etapa_Projeto etapa = etapas.get(i);

        View layout;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.layout_item_etapas_projeto,null);
        }else
            layout = view;

        TextView nome_etapa = (TextView) layout.findViewById(R.id.nome_etapa_projeto);

        nome_etapa.setText(etapa.getNome_etapa());

        return layout;
    }

}
