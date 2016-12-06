package br.com.conseive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import br.com.conseive.POJO.Anotacao;
import br.com.conseive.R;

/**
 * Created by Denis Viana on 06/12/2016.
 */

public class Adapter_Anotacoes extends RecyclerView.Adapter<Adapter_Anotacoes.MyViewHolder>{

    private ArrayList<Anotacao> anotacoes;
    private LayoutInflater layoutInflater;

    public Adapter_Anotacoes(ArrayList<Anotacao> anotacoes, Context context){
        this.anotacoes = anotacoes;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_card_anotacao,parent,false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            Anotacao anotacao = anotacoes.get(position);

        holder.nome_cliente_anotacao.setText(anotacao.getNome());
        holder.data_anotacao.setText(anotacao.getData());
        holder.hora_anotacao.setText(anotacao.getHora());
        holder.anotacao.setText(anotacao.getAnotacao());
        holder.avatar_cliente_anotacao.setImageResource(R.drawable.cozinha);

    }



    @Override
    public int getItemCount() {
        return anotacoes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome_cliente_anotacao;
        TextView data_anotacao;
        TextView anotacao;
        TextView hora_anotacao;
        CircularImageView avatar_cliente_anotacao;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome_cliente_anotacao = (TextView) itemView.findViewById(R.id.nome_cliente_anotacao);
            data_anotacao = (TextView) itemView.findViewById(R.id.data_anotacao);
            anotacao = (TextView) itemView.findViewById(R.id.anotacao);
            hora_anotacao = (TextView) itemView.findViewById(R.id.hora_comentario);
            avatar_cliente_anotacao = (CircularImageView) itemView.findViewById(R.id.avatar_cliente_notificao);
        }
    }

}
