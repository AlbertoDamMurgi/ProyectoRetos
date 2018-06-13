/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

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
/**
 * Actividad que muestra el ranking de los equipos y su puntuacion
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
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

    /**
     * Método que carga los datos de los equipos y sus puntuaciones y los muestra en una lista
     */
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
                    if (puntuacionEquipos.isEmpty()){
                        Toast.makeText(getApplicationContext(), "No hay jugadores en el ranking", Toast.LENGTH_LONG).show();
                    }
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
