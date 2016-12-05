package br.com.conseive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.conseive.POJO.Item_DashBoard;
import br.com.conseive.R;

/**
 * Created by denis on 04/12/2016.
 */

public class Adapter_Dashboard extends BaseAdapter {

    private Context contexto;
    private ArrayList<Item_DashBoard> lista;

    public Adapter_Dashboard(Context contexto, ArrayList<Item_DashBoard> lista){
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
        Item_DashBoard item = lista.get(i);

        View layout;

        int altura = viewGroup.getHeight(); /** Passa a altura da tela pra variável "altura" do tipo inteiro*/

        if(view == null) {
            /** Infla o layout XML personalizado atribuído para cada ítem da lista  */
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.item_dashboard, null);
        } else
            layout = view;

        /** Cria um objeto do TextView e ImageView já passando o ID atribuído no arquivo XML*/
        ImageView imgList1 = (ImageView) layout.findViewById(R.id.image_item_dashboard);
        TextView txtList = (TextView) layout.findViewById(R.id.text_item_dashboard);
        LinearLayout backgroud_item_dashboard = (LinearLayout) layout.findViewById(R.id.backgroud_item_dashboard);


        /** Recupera o título e o ícone e a cor do ítem da lista passando para cada objeto de cada ítem no ListView */
        txtList.setText(item.getItemnome());
        imgList1.setImageResource(item.getIcone(i));
        backgroud_item_dashboard.setBackgroundColor(layout.getResources().getColor(item.getCor()));

        /** De forma que os ítens e a ListView preencham completamente a tela
         * é utilizado o método setMinimumHeight passando como parâmetro
         * a altura da tela principal dividida pelo número de ítens da lista, assim,
         * a ListView ajustará seu tamanho de acordo com a tela do dispositivo*/
        layout.setMinimumHeight(altura/lista.size());

        return layout;
    }

}
