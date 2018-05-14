package org.iesmurgi.reta2.UI.admin;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.admin.Objetos.EquipoParticipantes;
import org.iesmurgi.reta2.UI.usuario.RankingActivity;
import org.iesmurgi.reta2.UI.usuario.RankingAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminVerJugadoresActivity extends AppCompatActivity {

    @BindView(R.id.recicler_chat_admin)
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    int idPartida;
    ArrayList<EquipoParticipantes> jugadores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_admin);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(AdminVerJugadoresActivity.this);

         idPartida = getIntent().getIntExtra("idPartida",0);

         cargarDatos();

            }




    public void cargarDatos(){
        progressDialog.setMessage("Cargando participantes...");
        progressDialog.show();
        final String URL = "http://geogame.ml/api/obtener_jugadores_partida.php?idPartida="+idPartida;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject o = response.getJSONObject(i);
                        jugadores.add(new EquipoParticipantes(o.getString("username"),o.getString("participantes")));

                    } catch (JSONException e) {
                        Log.e("Log Json error Partida", e.getMessage());
                    }
                }//endgfor

                Log.e("LISTA jugadores ", response.toString());

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new JugadoresAdapter(jugadores, getApplicationContext()));
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

