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

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.iesmurgi.reta2.Seguridad.Cifrar;
import org.iesmurgi.reta2.Seguridad.Permisos;
import org.iesmurgi.reta2.UI.retos.MapPrincActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.Data.entidades.Respuestas;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.R;
/**
 * Actividad principal del usuario.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class MainActivity extends AppCompatActivity implements LifecycleObserver {
    int idUsuario;

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    @BindView(R.id.txt_contraPartida)
    EditText txt_contraPartida;
    @BindView(R.id.txt_main_nombrepartida)
    EditText txt_nombrepartida;
    ProgressDialog progressDialog;
    BasedeDatosApp db;
    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    private List<Retos> retos;

    private int ID_PARTIDA;
    private String NOMBREPARTIDA;
    private int ULTIMORETO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        idUsuario = getIntent().getIntExtra("idUsuario", 0);
        progressDialog = new ProgressDialog(MainActivity.this);
        db = BasedeDatosApp.getAppDatabase(this);


    }


    /*
        private void escuchador() {

            mLoginModel.getUsuario().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(@Nullable FirebaseUser firebaseUser) {
                    if (firebaseUser == null) {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }
                }
            });

        }
    */


    /**
     * Método que desconecta al usuario
     */
    @OnClick(R.id.btn_logout)
    public void desconectarse() {
        try {
            mLoginModel.getConection().getValue().getInstance().signOut();
        }catch (NullPointerException ex){
            Log.w("no conection","no loginmodel conection");
        }
        mLoginModel.getConection().postValue(null);
        mLoginModel.getUsuario().postValue(null);

        SharedPreferences prefs2 = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs2.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();

    }

    /**
     * Método que inicia la partida
     */
    @OnClick(R.id.bt_iniciarPartida)
    void iniciarPartida() {

        if (Permisos.comprobarPermisos(this,this)) {

            progressDialog.setMessage("Cargando partida...");
            if (!txt_contraPartida.getText().toString().isEmpty() && !txt_nombrepartida.getText().toString().isEmpty()) {
                progressDialog.show();
                new cargarPartida().execute();
                new comprobarDescargado().execute();
            }else{
                Toast.makeText(getApplicationContext(), "Rellena los campos de partida", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "Necesitas todos los permisos para poder jugar", Toast.LENGTH_LONG).show();
        }
    }//fin onclick

    /**
     * Clase que hereda de AsyncTask para crear una tarea que cargue la partida
     */
    class cargarRespuesta extends AsyncTask<Respuestas, Void, Integer> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected Integer doInBackground(Respuestas... r) {

            final String URL3 = "http://geogame.ml/api/obtener_respuestas.php?nombre=" + txt_nombrepartida.getText().toString().trim();


            StringRequest request3 = new StringRequest(Request.Method.POST, URL3,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response2) {
                    try {
                        JSONArray response = new JSONArray(Cifrar.decrypt(response2));
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject o = response.getJSONObject(i);
                            Log.e("LISTA AA AAA Respuestas", "una vuelta");
                            new InsertarRespuesta().execute(new Respuestas(
                                    o.getInt("idRespuesta"),
                                    o.getInt("idReto"),
                                    o.getString("descripcion"),
                                    o.getInt("verdadero")
                            ));
                    }//endgfor
                    startActivity(new Intent(getApplicationContext(), MapPrincActivity.class)
                            .putExtra("IDPARTIDA", ID_PARTIDA)
                            .putExtra("NOMBREPARTIDA", NOMBREPARTIDA)
                            .putExtra("idUsuario", idUsuario)
                            .putExtra("ultimoReto",ULTIMORETO)
                    );
                    progressDialog.dismiss();
                    Log.e("LISTA Respuestas", response.toString());

                } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }
            });


            requestQueue.add(request3);


            return 0;
        }
    }

    /**
     * Método que carga el ultimo reto por el que va el usuario
     */
    void InsertarEnPartidaYObtenerUltima(){
        final String URLL = "http://geogame.ml/api/insertar_equipo_partida.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOG Ultimo",response);
                if (response.contains("success")) {
                    Log.e("LOG Ultimo",response);
                    try {
                        JSONObject jObj = new JSONObject(response);
                        ULTIMORETO = jObj.getInt("ultimoReto");
                        new cargarReto().execute();
                        Log.e("ULTIMORETO",""+ULTIMORETO);

                    } catch (JSONException e) {
                        e.getStackTrace();
                    }

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Ups! Algo ha fallado", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", ""+idUsuario);
                params.put("idPartida", ""+ID_PARTIDA);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        // fin insertar a db
    }

    /**
     * Tarea asincrona que carga el reto actual
     */
    class cargarReto extends AsyncTask<Void, Void, Integer> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final String URL2 = "http://geogame.ml/api/Lista_Retos_Clave.php?nombre=" + txt_nombrepartida.getText().toString().trim();


        @Override
        protected Integer doInBackground(Void... r) {
            StringRequest request2 = new StringRequest(Request.Method.POST, URL2,  new Response.Listener<String>() {
                @Override
                public void onResponse(String response2) {
                    try {
                        JSONArray response = new JSONArray(Cifrar.decrypt(response2));

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject o = response.getJSONObject(i);
                            Log.e("LISTA Retos ", "una vuelta");
                            new InsertarReto().execute(new Retos(
                                    o.getInt("idReto"),
                                    o.getString("nombre"),
                                    o.getString("descripcion"),
                                    o.getString("video"),
                                    o.getInt("maxDuracion"),
                                    o.getInt("tipo"),
                                    o.getInt("puntuacion"),
                                    o.getDouble("localizacionLatitud"),
                                    o.getDouble("localizacionLongitud"),
                                    o.getInt("idPartida")
                            ));


                    }//endgfor
                    new cargarRespuesta().execute();

                    Log.e("LISTA Retos", response.toString());
                } catch (Exception e) {
                    Log.e("Log Json error Retos", e.getMessage());
                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(request2);


            return 0;
        }
    }

    /**
     * Tarea asincrona que carga el reto actual
     */
    class cargarPartida extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... r) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

////////////////////////////////////////////// Partida


            final String URL = "http://geogame.ml/api/obtener_partida.php?nombre=" + txt_nombrepartida.getText().toString().trim() + "&passwd=" + txt_contraPartida.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response2) {
                    try {
                        JSONArray response = new JSONArray(Cifrar.decrypt(response2));
                        boolean partidadescargada = false;
                    for (int i = 0; i < response.length(); i++) {
                            JSONObject o = response.getJSONObject(i);
                            Log.e("LISTA Partida", "una vuelta");
                            ID_PARTIDA = o.getInt("idPartida");
                            NOMBREPARTIDA = o.getString("nombre");
                            Log.e("nombrepartida main", NOMBREPARTIDA);
                            new InsertarPartida().execute(new Partidas(
                                    ID_PARTIDA,
                                    NOMBREPARTIDA,
                                    o.getString("passwd"),
                                    o.getInt("maxDuracion")
                            ));
                        partidadescargada = true;
                    }//endgfor
                    if (partidadescargada) {
                        InsertarEnPartidaYObtenerUltima();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Datos erroneos", Toast.LENGTH_LONG).show();
                    }
                    Log.e("LISTA Partida", response.toString());
                } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(request);

            return 0;
        }
    }

    /**
     * Tarea asincrona que comprueba si se ha insertado correctamente la partida en el room
     */
    class comprobarDescargado extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... v) {

            return db.retosDao().getRetos().size();
        }

        @Override
        protected void onPostExecute(Integer cant) {
            super.onPostExecute(cant);
            Log.i("RETOS DESCARGADOS:", cant + "");
        }

    }

    /**
     * Tarea asincrona que inserta un reto en el room
     */
    class InsertarReto extends AsyncTask<Retos, Void, Integer> {
        @Override
        protected Integer doInBackground(Retos... r) {
            db.retosDao().retosInsert(r);
            return 0;
        }
    }
    /**
     * Tarea asincrona que inserta una partida en el room
     */
    class InsertarPartida extends AsyncTask<Partidas, Void, Integer> {
        @Override
        protected Integer doInBackground(Partidas... p) {
            db.partidasDao().partidasInsert(p);
            return 0;
        }
    }
    /**
     * Tarea asincrona que inserta una respuesta en el room
     */
    class InsertarRespuesta extends AsyncTask<Respuestas, Void, Integer> {
        @Override
        protected Integer doInBackground(Respuestas... r) {
            db.respuestasDao().insertarRespuestas(r);
            return db.retosDao().getRetos().size();
        }
    }

    /**
     * Método que lanza la actividad de escanear el codigo qr
     */
    @OnClick(R.id.fab_scanqrcode)
    void goScanQR(){
        Intent i = new Intent(getApplicationContext(), QRScannerActivity.class).putExtra("idUsuario",idUsuario);
        startActivity(i);
    }


}



