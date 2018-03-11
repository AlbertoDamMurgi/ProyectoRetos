package geogame.proyectoretos.UI;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geogame.proyectoretos.Data.entidades.Partidas;
import geogame.proyectoretos.R;

public class CrearRetoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private double latitud;
    private double longitud;
    private int idPartida;
    private int idReto;
    private String nombrePartida;
    private String clavePartida;
    private int partidaNumerosRetos;
    private LatLng latLng;
    GoogleApiClient mClient;
    public static final String TAG = CrearRetoActivity.class.getSimpleName();
    private static final int PLACE_PICKER_REQUEST = 1;
    ProgressDialog progressDialog;

    @BindView(R.id.txt_crearReto_nombre)
    EditText txt_nombre;

    @BindView(R.id.txt_crearReto_duracion)
    EditText txt_duracion;

    @BindView(R.id.txt_crearReto_puntuacion)
    EditText txt_puntos;

    @BindView(R.id.txt_crearReto_pregunta)
    EditText txt_pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reto);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        mClient = new GoogleApiClient.Builder(this)
                //lo que nos devuelve google
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //añadimos las apis que nos interesan
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                //donde se va a pintar la actividad
                .enableAutoManage(this, this)
                .build();

        nombrePartida=getIntent().getStringExtra("partidaNombre");
        clavePartida=getIntent().getStringExtra("partidaContra");
        partidaNumerosRetos=Integer.parseInt(getIntent().getStringExtra("partidaNumerosRetos"));

        ImageButton btnMapa = findViewById(R.id.btn_crearReto_mapaLugares);
        btnMapa.setOnClickListener(view -> runApiPlaces(view));


    }





    @OnClick(R.id.btn_crearReto_next )
    void ClickBotonReto() {
        Log.i("LOOOOOOOOOOOG","ENTRE EN CLIK");
        progressDialog.setMessage("Creando reto...");
        progressDialog.show();

        CargarIDPartida();



    }//End OnClick



    void CargarIDReto(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

////////////////////////////////////////////// CargarIDReto

        final String URL = "http://geogame.ml/api/obtener_reto_con_datos_de_reto.php?nombre="+txt_nombre.getText().toString()+"&descripcion="+txt_pregunta.getText().toString()+"&maxDuracion="+txt_duracion.getText().toString()+"&tipo=1&puntuacion="+txt_puntos.getText().toString()+"&localizacionLatitud="+latitud+"&localizacionLongitud="+longitud+"&idPartida="+idPartida;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){

                    try {
                        JSONObject o =response.getJSONObject(i);



                        idReto= o.getInt("idReto");

                        Log.e("Obteniendo id reto",""+idReto);

                    } catch (JSONException e) {
                        Log.e("Log Json error Partida",e.getMessage());
                    }
                }

                //endgfor
                Log.e("LISTA Partida",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Partida",error.getMessage());
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

    }//End CargarIDReto




    void CargarIDPartida(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

////////////////////////////////////////////// Partida

        final String URL = "http://geogame.ml/api/obtener_partida.php?nombre="+nombrePartida+"&passwd="+clavePartida;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){

                    try {
                        JSONObject o =response.getJSONObject(i);
                        Log.e("LISTA Partida","una vuelta");


                             idPartida= o.getInt("idPartida");
                                o.getString("nombre");
                                o.getString("passwd");
                                o.getInt("maxDuracion");

                        InsertarReto();
                    } catch (JSONException e) {
                        Log.e("Log Json error Partida",e.getMessage());
                    }
                }//endgfor
                Log.e("LISTA Partida",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Partida",error.getMessage());
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

    }//End CargarIDPartida


//////////////////////////////////////
    void InsertarReto(){
        //INSERTART
        final String URL = "http://geogame.ml/api/insertar_reto.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    CargarIDReto();
                }else{
                    Toast.makeText(getApplicationContext(), "Ups! ha habido algun error", Toast.LENGTH_SHORT).show();

                }
                Log.e("ON RESPONDE",response.toString());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", txt_nombre.getText().toString());
                params.put("descripcion", txt_pregunta.getText().toString());
                params.put("maxDuracion", txt_duracion.getText().toString());
                params.put("tipo", "1");
                params.put("puntuacion", txt_puntos.getText().toString());
                params.put("localizacionLatitud", ""+latitud);
                params.put("localizacionLongitud", ""+longitud);
                params.put("idPartida", ""+idPartida);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }//InsertarReto
























    public void runApiPlaces(View view){
        try {
            // Start a new Activity for the Place Picker API, this will trigger {@code #onActivityResult}
            // when a place is selected or with the user cancels.
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }

    }
    //este metodo se lanza cuando el metodo RunApiplaces termina
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            Place place = PlacePicker.getPlace(this, data);

            if (place == null) {
                Log.i(TAG, "ningun lugar seleccionado");
                return;
            }
            Log.v("lat1", String.valueOf(latitud));
            Log.v("long1", String.valueOf(longitud));
            latLng = place.getLatLng();
            latitud = latLng.latitude;
            longitud = latLng.longitude;
            Log.v("lat2", String.valueOf(latitud));
            Log.v("long2", String.valueOf(longitud));

            Toast.makeText(getApplicationContext(),"Ubicacion del reto seleccionada!!",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


/////////////////////////////////////////////

}
