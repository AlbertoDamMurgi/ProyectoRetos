package org.iesmurgi.reta2.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.iesmurgi.reta2.R;

public class FinPartidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_partida);


        Button btnVolver = findViewById(R.id.btn_finpartida_volverLogin);
        Button btnRanking = findViewById(R.id.btn_finpartida_verranking);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Implementacion en versiones futuras...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
