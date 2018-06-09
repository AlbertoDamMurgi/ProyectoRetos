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
import org.iesmurgi.reta2.UI.admin.Objetos.Partida;
import org.iesmurgi.reta2.UI.admin.PuntuarFotoGridAdminActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



 /**
 * Adapter que se usa para el chat y algunas listas.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see ChatAdapter
 * @see ChatAdminActivity
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {





    private ArrayList<Partida> partidas;
 

    private ArrayList<String> salas,nombreretos;
  

    private Context context;
    private int tipo,size;
    private String sala ="",partida,usuario;
    private int idpartida;

     /**
      * Constructor del chat
      * @param salas  salas del chat
      * @param context contexto de la aplicación
      * @param tipo tipo del adapter
      */
    public ChatAdapter(ArrayList<String> salas, Context context,int tipo) {
        this.salas = salas;
        this.context=context;
        this.tipo = tipo;
        size = salas.size();
    }

     /**
      * Constructor que recibe tambien el id de partida
      * @param salas salas del chat
      * @param context contexto de la app
      * @param tipo tipo del adapter
      * @param sala sala actual
      * @param idpartida id de la partida
      */
    public ChatAdapter(ArrayList<String> salas, Context context,int tipo,String sala,int idpartida) {
        this.salas = salas;
        this.context=context;
        this.tipo = tipo;
        this.sala =sala;
        this.idpartida=idpartida;
        size= salas.size();
    }

     /**
      * Constructor que recibe nombres e IDs de las partidas
      * @param nombresAndIDs nombres e ids de las partidas
      * @param context contexto de la aplicación
      * @param tipo tipo del adapter
      * @param aux auxiliar para diferenciar el constructor
      */
    public ChatAdapter(ArrayList<Partida> nombresAndIDs, Context context, int tipo, int aux) {
        this.partidas = nombresAndIDs;
        this.context=context;
        this.tipo = tipo;
        size = nombresAndIDs.size();
    }

     /**
      * Constructor que recibe el nombre de los retos, la partida , el usuario que la esta jugando y el id de la partida
      * @param context contexto de la app
      * @param tipo tipo del adapter
      * @param nombreretos lista con los nombres de los retos
      * @param partida partida a la que pertenecen los retos
      * @param usuario usuario que está jugando la partida
      * @param idpartida id de la partida que se está jugando
      */
    public ChatAdapter(Context context, int tipo,ArrayList<String> nombreretos,String partida,String usuario,int idpartida) {
        this.nombreretos = nombreretos;
        this.context = context;
        this.tipo = tipo;
        this.partida = partida;
        this.usuario=usuario;
        this.idpartida=idpartida;
        size = nombreretos.size();
    }

     /**
      * Constructor que recibe los nombres de los participantes y la partida
      * @param nombres nombres de los participantes
      * @param applicationContext contexto de la app
      * @param tipo tipo del adapter
      * @param partida partida que se está jugando
      */
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
                    context.startActivity(new Intent(context, ChatActivity.class).putExtra("USUARIO", salas.get(position)).putExtra("SALA",partida).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                });
                break;
            case 4:
                holder.nombreChat.setText(salas.get(position));
                break;
            case 5:
                holder.nombreChat.setText(partidas.get(position).getNombre());
                holder.nombreChat.setOnClickListener(v -> {
                    context.startActivity(new Intent(context,AdministrarPartidaAdminActivity.class).putExtra("PARTIDA", partidas.get(position).getNombre()).putExtra("ID", partidas.get(position).getId()).putExtra("codigoqr",partidas.get(position).getCodeqr()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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

     /**
      * Método que retorna el número de items del adapter
      * @return tamaño del adapter
      */
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
