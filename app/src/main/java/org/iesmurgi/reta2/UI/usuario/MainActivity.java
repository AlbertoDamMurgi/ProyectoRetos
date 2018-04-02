package org.iesmurgi.reta2.UI.usuario;

import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.iesmurgi.reta2.UI.retos.MapPrincActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.Data.entidades.Respuestas;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.R;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        escuchador();

        String email = mLoginModel.getConection().getValue().getInstance().getCurrentUser().getEmail();
        progressDialog = new ProgressDialog(this);
        db = BasedeDatosApp.getAppDatabase(this);


    }



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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void comprobarLogin() {


    }


    @OnClick(R.id.btn_logout)
    public void desconectarse() {

        mLoginModel.getConection().getValue().getInstance().signOut();
        mLoginModel.getConection().postValue(null);
        mLoginModel.getUsuario().postValue(null);
    }



    @OnClick(R.id.bt_iniciarPartida)
    void iniciarPartida() {


        progressDialog.setMessage("Cargando partida...");
        progressDialog.show();
        new cargarPartida().execute();


        new comprobarDescargado().execute();
    }//fin onclick




    class cargarRespuesta extends AsyncTask<Respuestas, Void, Integer> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected Integer doInBackground(Respuestas... r) {

            final String URL3 = "http://geogame.ml/api/obtener_respuestas.php?nombre="+txt_nombrepartida.getText().toString();




            JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.POST, URL3, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject o = response.getJSONObject(i);
                            Log.e("LISTA AA AAA Respuestas", "una vuelta");

                            new InsertarRespuesta().execute(new Respuestas(
                                    o.getInt("idRespuesta"),
                                    o.getInt("idReto"),
                                    o.getString("descripcion"),
                                    o.getInt("verdadero")
                            ));

                        } catch (JSONException e) {
                            Log.e("Json error Respuestas", e.getMessage());
                        }
                        if (response.length()-1 == i) {
                            startActivity(new Intent(getApplicationContext(),MapPrincActivity.class).putExtra("IDPARTIDA",ID_PARTIDA));
                        }
                    }//endgfor
                    progressDialog.dismiss();

                    Log.e("LISTA Respuestas", response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Respuestas", error.getMessage());
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            });


            requestQueue.add(request3);


            return 0;
        }
    }


    class cargarReto extends AsyncTask<Void, Void, Integer> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final String URL2 = "http://geogame.ml/api/Lista_Retos_Clave.php?nombre="+txt_nombrepartida.getText().toString();



        @Override
        protected Integer doInBackground(Void... r) {
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.POST, URL2, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject o = response.getJSONObject(i);
                            Log.e("LISTA Retos ", "una vuelta");

                            new InsertarReto().execute(new Retos(

                                    o.getInt("idReto"),
                                    o.getString("nombre"),
                                    o.getString("descripcion"),
                                    o.getInt("maxDuracion"),
                                    o.getInt("tipo"),
                                    o.getInt("puntuacion"),
                                    o.getDouble("localizacionLatitud"),
                                    o.getDouble("localizacionLongitud"),
                                    o.getInt("idPartida")
                            ));

                        } catch (JSONException e) {
                            Log.e("Log Json error Retos", e.getMessage());
                        }
                    }//endgfor
                    new cargarRespuesta().execute();

                    Log.e("LISTA Retos", response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Retos", error.getMessage());
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request2);


            return 0;
        }
    }



    class cargarPartida extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... r) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

////////////////////////////////////////////// Partida


            final String URL = "http://geogame.ml/api/obtener_partida.php?nombre="+txt_nombrepartida.getText().toString()+"&passwd="+txt_contraPartida.getText().toString();



            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    boolean partidadescargada=false;
                    for (int i=0;i<response.length();i++){


                        try {
                            JSONObject o = response.getJSONObject(i);
                            Log.e("LISTA Partida", "una vuelta");
                            ID_PARTIDA = o.getInt("idPartida");
                            new InsertarPartida().execute(new Partidas(
                                    ID_PARTIDA,
                                    o.getString("nombre"),
                                    o.getString("passwd"),
                                    o.getInt("maxDuracion")
                            ));

                        } catch (JSONException e) {
                            Log.e("Log Json error Partida", e.getMessage());
                        }
                        partidadescargada=true;
                    }//endgfor
                    if (partidadescargada){
                        new cargarReto().execute();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Datos erroneos",Toast.LENGTH_LONG).show();
                    }



                    Log.e("LISTA Partida", response.toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Partida", error.getMessage());
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(request);

            return 0;
        }
    }


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

    class InsertarReto extends AsyncTask<Retos, Void, Integer> {
        @Override
        protected Integer doInBackground(Retos... r) {
            db.retosDao().retosInsert(r);
            return 0;
        }
    }

    class InsertarPartida extends AsyncTask<Partidas, Void, Integer> {
        @Override
        protected Integer doInBackground(Partidas... p) {
            db.partidasDao().partidasInsert(p);
            return 0;
        }
    }

    class InsertarRespuesta extends AsyncTask<Respuestas, Void, Integer> {
        @Override
        protected Integer doInBackground(Respuestas... r) {
            db.respuestasDao().insertarRespuestas(r);
            return db.retosDao().getRetos().size();
        }
    }


}



