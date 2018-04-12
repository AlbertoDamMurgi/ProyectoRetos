package org.iesmurgi.reta2.Chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.admin.AdminPuntuarFotoTransicion;
import org.iesmurgi.reta2.UI.admin.AdministrarPartidaAdminActivity;
import org.iesmurgi.reta2.UI.admin.NombreAndID;
import org.iesmurgi.reta2.UI.admin.PuntuarFotoGridAdminActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Farra on 26/03/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {



    private ArrayList<String> salas,nombreretos;
    private ArrayList<NombreAndID> nombreAndIDS;

    private Context context;
    private int tipo,size;
    private String sala ="",partida,usuario;
    private int idpartida;


    public ChatAdapter(ArrayList<String> salas, Context context,int tipo) {
        this.salas = salas;
        this.context=context;
        this.tipo = tipo;
        size = salas.size();
    }

    public ChatAdapter(ArrayList<String> salas, Context context,int tipo,String sala,int idpartida) {
        this.salas = salas;
        this.context=context;
        this.tipo = tipo;
        this.sala =sala;
        this.idpartida=idpartida;
        size= salas.size();
    }

    public ChatAdapter(ArrayList<NombreAndID> nombresAndIDs, Context context, int tipo,int asd) {
        this.nombreAndIDS = nombresAndIDs;
        this.context=context;
        this.tipo = tipo;
        size = nombresAndIDs.size();
    }

    public ChatAdapter(Context context, int tipo,ArrayList<String> nombreretos,String partida,String usuario,int idpartida) {
        this.nombreretos = nombreretos;
        this.context = context;
        this.tipo = tipo;
        this.partida = partida;
        this.usuario=usuario;
        this.idpartida=idpartida;
        size = nombreretos.size();
    }

    public ChatAdapter(ArrayList<String> nombres, Context applicationContext, int tipo, String partida) {
        this.salas=nombres;
        this.context = applicationContext;
        this.tipo = tipo;
        this.partida=partida;
        size= nombres.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_admin_item, parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (tipo){
            case 0:
                holder.nombreChat.setText(salas.get(position));
                holder.nombreChat.setOnClickListener(v -> {
                    context.startActivity(new Intent(context, ChatActivity.class).putExtra("SALA", salas.get(position)));
                });
                break;
            case 1:
                holder.nombreChat.setText(salas.get(position));
                holder.nombreChat.setOnClickListener(v -> {
                    //onclick
                    context.startActivity(new Intent(context, AdministrarPartidaAdminActivity.class).putExtra("PARTIDA",salas.get(position)));
                });
                break;
            case 2:
                break;
            case 3:
                holder.nombreChat.setText(salas.get(position));
                holder.nombreChat.setOnClickListener(v -> {
                    context.startActivity(new Intent(context, ChatActivity.class).putExtra("USUARIO", salas.get(position)).putExtra("SALA",sala).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                });
                break;
            case 4:
                holder.nombreChat.setText(salas.get(position));
                break;
            case 5:
                holder.nombreChat.setText(nombreAndIDS.get(position).getNombre());
                holder.nombreChat.setOnClickListener(v -> {
                    context.startActivity(new Intent(context,AdministrarPartidaAdminActivity.class).putExtra("PARTIDA",nombreAndIDS.get(position).getNombre()).putExtra("ID",nombreAndIDS.get(position).getId()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                });
                break;
            case 6:
                holder.nombreChat.setText(salas.get(position));
                holder.nombreChat.setOnClickListener(v -> {
                context.startActivity(new Intent(context, PuntuarFotoGridAdminActivity.class).putExtra("USUARIO",salas.get(position)).putExtra("PARTIDA",sala).putExtra("IDPARTIDA",idpartida));
                });
                break;
            case 7:
                holder.nombreChat.setText(nombreretos.get(position));
                holder.nombreChat.setOnClickListener(v -> {
                    context.startActivity(new Intent(context, AdminPuntuarFotoTransicion.class).putExtra("NOMBRERETO",nombreretos.get(position)).putExtra("USUARIO",usuario).putExtra("PARTIDA",partida).putExtra("IDPARTIDA",idpartida));
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return size;
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
