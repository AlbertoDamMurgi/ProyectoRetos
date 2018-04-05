package org.iesmurgi.reta2.UI.usuario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingActivity extends AppCompatActivity {

    @BindView(R.id.recycler_ranking)
    RecyclerView recyclerRanking;

    ArrayList<RankingEquipos> puntuacionEquipos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);


        if (puntuacionEquipos.isEmpty()){
            rellenarArray();
        }

        recyclerRanking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerRanking.setAdapter(new RankingAdapter(puntuacionEquipos, getApplicationContext()));



    }


    public void rellenarArray(){

        puntuacionEquipos.add(new RankingEquipos("Los tiesos", 100));
        puntuacionEquipos.add(new RankingEquipos("Los tigres", 100));
        puntuacionEquipos.add(new RankingEquipos("Los pepes", 100));
        puntuacionEquipos.add(new RankingEquipos("Los strings", 100));
        puntuacionEquipos.add(new RankingEquipos("Los leones", 100));
        puntuacionEquipos.add(new RankingEquipos("Los saltys", 100));
        puntuacionEquipos.add(new RankingEquipos("Los farrators", 100));
        puntuacionEquipos.add(new RankingEquipos("Los ricos", 100));
        puntuacionEquipos.add(new RankingEquipos("Los secos", 100));

    }


}
