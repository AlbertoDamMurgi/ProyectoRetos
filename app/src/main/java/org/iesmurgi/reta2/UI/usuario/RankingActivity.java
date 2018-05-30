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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.Seguridad.Cifrar;
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
        idPartida = getIntent().getIntExtra("idPartida", 0);

        cargarDatos();
    }


    public void cargarDatos() {
        progressDialog.setMessage("Cargando ranking...");
        progressDialog.show();
        final String URL = "http://geogame.ml/api/obtener_ranking_partida.php?idPartida=" + idPartida;

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                try {
                    JSONArray response = new JSONArray(Cifrar.decrypt(response2));

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject o = response.getJSONObject(i);
                        puntuacionEquipos.add(new RankingEquipos(o.getString("username"), o.getInt("puntuaciontotal")));
                    }

                    Log.e("LISTA puntuacion", response.toString());

                    recyclerRanking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerRanking.setAdapter(new RankingAdapter(puntuacionEquipos, getApplicationContext()));
                    progressDialog.dismiss();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }

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
