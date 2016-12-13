package br.com.conceive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.conceive.POJO.Item_Acervo;
import br.com.conceive.R;

/**
 * Created by Denis Viana on 02/12/2016.
 */

public class Adapter_Acervo_Referencias extends BaseAdapter {

    private Context contexto;
    private ArrayList<Item_Acervo> lista;

    public Adapter_Acervo_Referencias(Context contexto, ArrayList<Item_Acervo> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Item_Acervo item = lista.get(i);

        View layout;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.item_acervo_referencias,null);
        }else
            layout = view;

        TextView nome_acervo = (TextView) layout.findViewById(R.id.nome_categoria_acervo);

        nome_acervo.setText(item.getNome());


        return layout;
    }
}
