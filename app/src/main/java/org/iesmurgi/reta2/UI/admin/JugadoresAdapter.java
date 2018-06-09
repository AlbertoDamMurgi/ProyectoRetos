package org.iesmurgi.reta2.UI.admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.admin.Objetos.EquipoParticipantes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter para ver los equipos y sus participantes.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class JugadoresAdapter extends RecyclerView.Adapter<JugadoresAdapter.ViewHolder> {

    private ArrayList<EquipoParticipantes> jugadores;
    private Context context;

    /**
     * Constructor
     * @param j lista de los jugadores y sus equipos
     * @param context contexto de la app
     */
    public JugadoresAdapter(ArrayList<EquipoParticipantes> j, Context context) {
        this.jugadores = j;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jugadores_item, parent,false);
        return new JugadoresAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nombreEquipo.setText(jugadores.get(position).getEquipo());
        holder.participantes.setText(jugadores.get(position).getParticipantes().replaceAll("\n",", "));

    }

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_nombre_equipo_item)
        TextView nombreEquipo;
        @BindView(R.id.tv_participantes_item)
        TextView participantes;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
