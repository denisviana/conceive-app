package br.com.conceive.adapter;

import android.content.Context;

import android.content.Intent;

import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import br.com.conceive.activitys.Criar_Projeto_Activity;
import br.com.conceive.POJO.Projeto;
import br.com.conceive.R;
import br.com.conceive.activitys.Visualizacao_Projeto_Activity;
import br.com.conceive.interfaces.RecyclerViewClickListener;

/**
 * Created by Denis Viana on 29/11/2016.
 */

public class Adapter_Projetos extends RecyclerView.Adapter<Adapter_Projetos.MyViewHolder> {

    private ArrayList<Projeto> lista_projetos;
    private Intent intent;
    private Projeto projeto;
    private Context context;
    private LayoutInflater layoutInflater;
    private static RecyclerViewClickListener mListener;

    public Adapter_Projetos(ArrayList<Projeto> lista_projetos, Context context){
        this.lista_projetos = lista_projetos;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.mListener = itemClickListener;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_item_projeto,parent,false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Projeto projeto = lista_projetos.get(position);
        holder.nome_projeto.setText(projeto.getNome_projeto());
        holder.nome_cliente_projeto.setText(projeto.getNome_cliente());
        holder.data_inicio_projeto.setText(projeto.getData_inicio());
        holder.etapa_atual_projeto.setText(projeto.getEtapa_atual());
    }

    @Override
    public int getItemCount() {
        return lista_projetos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome_projeto;
        TextView nome_cliente_projeto;
        TextView data_inicio_projeto;
        TextView etapa_atual_projeto;
        ImageView ic_edit_projeto;
        CardView card_projeto;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome_projeto = (TextView) itemView.findViewById(R.id.nome_projeto);
            nome_cliente_projeto = (TextView) itemView.findViewById(R.id.nome_cliente_projeto);
            data_inicio_projeto = (TextView) itemView.findViewById(R.id.data_inicio_projeto);
            etapa_atual_projeto = (TextView) itemView.findViewById(R.id.etapa_atual_projeto);
            ic_edit_projeto = (ImageView) itemView.findViewById(R.id.ic_edit_projeto);
            card_projeto = (CardView) itemView.findViewById(R.id.card_projeto);

            card_projeto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Visualizacao_Projeto_Activity.class);
                    Projeto projeto = lista_projetos.get(getLayoutPosition());
                    intent.putExtra("projeto",projeto);
                    context.startActivity(intent);
                }
            });

            ic_edit_projeto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showPopup(view,getLayoutPosition());
                    /*
                    Intent intent = new Intent(context, Criar_Projeto_Activity.class);
                    Projeto projeto = lista_projetos.get(getLayoutPosition());
                    intent.putExtra("projeto",projeto);
                    context.startActivity(intent); */
                }
            });

        }


    }

    public void setFilter(List<Projeto> projetos){
        lista_projetos = new ArrayList<>();
        lista_projetos.addAll(projetos);
        notifyDataSetChanged();
    }


    private void showPopup(View view, final int position) {
        // pass the imageview id
        View menuItemView = view.findViewById(R.id.ic_edit_projeto);
        PopupMenu popup = new PopupMenu(context, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.menu_card_projeto, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        Intent intent = new Intent(context, Criar_Projeto_Activity.class);
                        Projeto projeto = lista_projetos.get(position);
                        intent.putExtra("projeto",projeto);
                        context.startActivity(intent);
                        break;
                    case R.id.delete:
                        // do what you need .
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });
        popup.show();
    }

}
