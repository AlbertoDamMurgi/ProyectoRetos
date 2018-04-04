package org.iesmurgi.reta2.Chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.admin.AdministrarPartidaAdminActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Farra on 26/03/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {





    private ArrayList<String> salas;
    private Context context;
    private int tipo;
    private String sala ="";

    public ChatAdapter(ArrayList<String> salas, Context context,int tipo) {
        this.salas = salas;
        this.context=context;
        this.tipo = tipo;
    }

    public ChatAdapter(ArrayList<String> salas, Context context,int tipo,String sala) {
        this.salas = salas;
        this.context=context;
        this.tipo = tipo;
        this.sala =sala;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_admin_item, parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(tipo==0) {
            holder.nombreChat.setText(salas.get(position));
            holder.nombreChat.setOnClickListener(v -> {
                context.startActivity(new Intent(context, ChatActivity.class).putExtra("SALA", salas.get(position)));
            });
        }else if(tipo==1){

            holder.nombreChat.setText(salas.get(position));
            holder.nombreChat.setOnClickListener(v -> {
               //onclick
                context.startActivity(new Intent(context, AdministrarPartidaAdminActivity.class).putExtra("PARTIDA",salas.get(position)));
            });
        }else if(tipo==3){
            holder.nombreChat.setText(salas.get(position));
            holder.nombreChat.setOnClickListener(v -> {
                context.startActivity(new Intent(context, ChatActivity.class).putExtra("USUARIO", salas.get(position)).putExtra("SALA",sala));
            });
        }else if(tipo == 4){
            holder.nombreChat.setText(salas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return salas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_nombre_chat_admin)
        TextView nombreChat;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }

}
