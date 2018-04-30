package org.iesmurgi.reta2.UI.usuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinPartidaActivity extends AppCompatActivity {
    int idPartida;
    int idUsuario;
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
        idUsuario=getIntent().getIntExtra("idUsuario",0);

  Log.e("Fin usuario y partida",idUsuario+""+idPartida);


        cargarDatos();




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



    public void cargarDatos(){

        final String URL = "http://geogame.ml/api/obtener_puntos_usuario.php?idPartida="+idPartida+"&idUsuario="+idUsuario;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject o = response.getJSONObject(i);
                        txt_finpartida_nombreEquip.setText( o.getString("username"));
                        txt_finpartida_puntos.setText(""+ o.getInt("puntuaciontotal"));


                    } catch (JSONException e) {
                        Log.e("Log Json error Partida", e.getMessage());
                    }
                }//endgfor

                Log.e("LISTA puntuacion", response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Partida", error.getMessage());
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }


}
