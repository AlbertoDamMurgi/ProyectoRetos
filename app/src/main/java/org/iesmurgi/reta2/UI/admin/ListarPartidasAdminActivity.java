package org.iesmurgi.reta2.UI.admin;

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
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarPartidasAdminActivity extends AppCompatActivity {

    @BindView(R.id.recicler_chat_admin)
    RecyclerView recyclerView;


    private ArrayList<NombreAndID> nombreseIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas_admin);
        ButterKnife.bind(this);

        nombreseIDs.clear();

        final String URL = "http://geogame.ml/api/obtener_partida_admin.php?idAdmin=1";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject o = response.getJSONObject(i);
                             partidas.add( o.getString("nombre"));

                    } catch (JSONException e) {
                        Log.e("Log Json error Partida", e.getMessage());
                    }
                }//endgfor

                Log.e("LISTA Partida", response.toString());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(new ChatAdapter(nombreseIDs,getApplicationContext(),5,0));

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
