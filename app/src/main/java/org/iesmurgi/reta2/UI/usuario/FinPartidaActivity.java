package org.iesmurgi.reta2.UI.usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.iesmurgi.reta2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinPartidaActivity extends AppCompatActivity {
    int idPartida;
    String nombreEquipo;
    int puntosEquipo;


    @BindView(R.id.txt_finpartida_nombreEquip)
    TextView txt_finpartida_nombreEquip;

    @BindView(R.id.txt_finpartida_puntos)
    TextView txt_finpartida_puntos;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_partida);

        ButterKnife.bind(this);

        Button btnVolver = findViewById(R.id.btn_finpartida_volverLogin);
        Button btnRanking = findViewById(R.id.btn_finpartida_verranking);

        idPartida=getIntent().getIntExtra("idPartida",0);
        nombreEquipo=getIntent().getStringExtra("nombreEquipo");
        puntosEquipo=23;

        txt_finpartida_nombreEquip.setText(nombreEquipo);
        txt_finpartida_puntos.setText(""+puntosEquipo);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),RankingActivity.class).putExtra("idPartida",idPartida));
            }
        });

    }
}
