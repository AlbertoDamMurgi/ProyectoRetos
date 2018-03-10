package geogame.proyectoretos.UI;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geogame.proyectoretos.Chat.ChatActivity;
import geogame.proyectoretos.Data.BasedeDatosApp;
import geogame.proyectoretos.Data.BasedeDatosApp_Impl;
import geogame.proyectoretos.Data.entidades.Admin;
import geogame.proyectoretos.Data.entidades.Retos;
import geogame.proyectoretos.R;
import geogame.proyectoretos.UI.Adm.ActivityInsertarAdmin;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {


    @BindView(R.id.txt_contraPartida)
    EditText txt_contraPartida;

    ProgressDialog progressDialog;
    BasedeDatosApp db;
    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    private List<Retos> retos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        escuchador();

        String email = mLoginModel.getConection().getValue().getInstance().getCurrentUser().getEmail();
        progressDialog = new ProgressDialog(this);
        db=BasedeDatosApp.getAppDatabase(getApplication());





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

    @OnClick(R.id.btn_chat_prueba)
    public void iralChat() {

        startActivity(new Intent(getApplicationContext(), ChatActivity.class));

    }


    @OnClick(R.id.bt_iniciarPartida)
    void iniciarPartida(){
        retos = new ArrayList<>();
        progressDialog.setMessage("Cargando partida...");
        progressDialog.show();


        final String URL = "http://geogame.ml/api/Lista_Retos_Clave.php?clavereto="+txt_contraPartida.getText().toString();



        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){

                    try {
                        JSONObject o =response.getJSONObject(i);
                        Log.e("LISTA AA AAA","una vuelta");
                       retos.add(
                                new Retos(

                                        o.getInt("idReto"),
                                        o.getString("nombre"),
                                        o.getString("descripcion"),
                                        o.getInt("maxDuracion"),
                                        o.getInt("tipo"),
                                        o.getInt("puntuacion"),
                                        o.getDouble("localizacionLatitud"),
                                        o.getDouble("localizacionLongitud"),
                                        o.getInt("idPartida")
                                )
                        );

                        Log.e("LISTA size",""+retos.size());

                    } catch (JSONException e) {

                    }
                }//endgfor
                progressDialog.dismiss();
                comprobarDescargado();

                Log.e("LISTA AA AAA",response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("adad",error.getMessage());
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });

/*        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("adad","parametros mandar");
                params.put("clavereto", txt_contraPartida.getText().toString());
                return params;
            }}
          */

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }


    class comprobarDescargado extends AsyncTask<Retos,Void,Integer> {



        @Override
        protected Integer doInBackground(Retos... retos) {

            for (int i = 0; i <retos.length ; i++) {
               db.retosDao().retosInsert(retos[i]);
            }

            return db.retosDao().getRetos().size();
        }

        @Override
        protected void onPostExecute(Integer cant) {
            super.onPostExecute(cant);
            Log.i("RETOS DESCARGADOS:", cant+"");


        }

    }

    void comprobarDescargado(){
        Log.e("size retos",""+retos.size());
        new comprobarDescargado().execute(retos.get(0));

    }//end comprobar


}



