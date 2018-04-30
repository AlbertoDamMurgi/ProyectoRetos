package org.iesmurgi.reta2.UI.usuario;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingActivity extends AppCompatActivity {
    int idPartida;
    @BindView(R.id.recycler_ranking)
    RecyclerView recyclerRanking;

    ProgressDialog progressDialog;
    ArrayList<RankingEquipos> puntuacionEquipos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(RankingActivity.this);
        idPartida=getIntent().getIntExtra("idPartida",0);

        cargarDatos();
    }


    public void cargarDatos(){
        progressDialog.setMessage("Cargando ranking...");
        progressDialog.show();
        final String URL = "http://geogame.ml/api/obtener_ranking_partida.php?idPartida="+idPartida;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject o = response.getJSONObject(i);
                        puntuacionEquipos.add(new RankingEquipos(o.getString("username"),o.getInt("puntuaciontotal")));

                    } catch (JSONException e) {
                        Log.e("Log Json error Partida", e.getMessage());
                    }
                }//endgfor

                Log.e("LISTA puntuacion", response.toString());

                recyclerRanking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerRanking.setAdapter(new RankingAdapter(puntuacionEquipos, getApplicationContext()));
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }


}
