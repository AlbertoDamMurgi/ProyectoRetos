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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.iesmurgi.reta2.Data.Objetos.RankingEquipos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.Seguridad.Cifrar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Actividad que se muestra cuando el usuario termina la partida.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
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

        idPartida = getIntent().getIntExtra("idPartida", 0);
        idUsuario = getIntent().getIntExtra("idUsuario", 0);

        Log.e("Fin usuario y partida", idUsuario + "" + idPartida);


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
                startActivity(new Intent(getApplicationContext(), RankingActivity.class).putExtra("idPartida", idPartida));
            }
        });

    }

    /**
     * Método que carga los datos del equipo y su puntuacion.
     */
    public void cargarDatos() {

        final String URL = "http://geogame.ml/api/obtener_puntos_usuario.php?idPartida=" + idPartida + "&idUsuario=" + idUsuario;

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                try {
                    JSONArray response = new JSONArray(Cifrar.decrypt(response2));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject o = response.getJSONObject(i);
                        txt_finpartida_nombreEquip.setText(o.getString("username"));
                        txt_finpartida_puntos.setText("" + o.getInt("puntuaciontotal"));
                    }//endgfor
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
