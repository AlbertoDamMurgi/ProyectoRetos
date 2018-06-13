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

package org.iesmurgi.reta2.UI.admin;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import org.iesmurgi.reta2.Seguridad.Cifrar;
import org.iesmurgi.reta2.papelera.Admin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import org.iesmurgi.reta2.R;
/**
 * Actividad que crea el reto que se va a agregar a la partida
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class CrearRetoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private String latitud = "";
    private String longitud = "";
    private int idPartida;
    private int idReto;
    private String nombrePartida;
    private String clavePartida;
    private int tipoReto = 1;

    private int partidaNumerosRetos;
    private int partidaNumerosRetosActual=1;

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

    @BindView(R.id.txt_crearReto_respuestaFalse1)
    EditText txt_rfalse1;

    @BindView(R.id.txt_crearReto_respuestaFalse2)
    EditText txt_rfalse2;

    @BindView(R.id.txt_crearReto_respuestaTrue)
    EditText txt_rtrue;
    @BindView(R.id.txt_crearreto_mensaje)
    TextView txt_mensaje;
    @BindView(R.id.spinner_crearreto_tipos)
    Spinner spinnerTipos;
    @BindView(R.id.txt_crearReto_urlVideo)
    TextView txt_video;

    private AdminModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reto);
        ButterKnife.bind(this);
        model = ViewModelProviders.of(this).get(AdminModel.class);
        if(model.getRetoactual()!=0){
            partidaNumerosRetosActual = model.getRetoactual();
        }
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        progressDialog = new ProgressDialog(CrearRetoActivity.this);



        mClient = new GoogleApiClient.Builder(this)

                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //añadimos las apis que nos interesan
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                //donde se va a pintar la actividad
                .enableAutoManage(this, this)
                .build();

        nombrePartida = getIntent().getStringExtra("partidaNombre");
        clavePartida = getIntent().getStringExtra("partidaContra");
        partidaNumerosRetos = Integer.parseInt(getIntent().getStringExtra("partidaNumerosRetos"));

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getApplicationContext(), R.array.tipos, R.layout.spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipos.setAdapter(adapterSpinner);

        spinnerTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 0:
                        txt_rtrue.setVisibility(View.VISIBLE);
                        txt_rfalse1.setVisibility(View.VISIBLE);
                        txt_rfalse2.setVisibility(View.VISIBLE);
                        tipoReto = 1;
                        break;

                    case 1:
                        txt_rtrue.setVisibility(View.VISIBLE);
                        txt_rfalse1.setVisibility(View.GONE);
                        txt_rfalse2.setVisibility(View.GONE);
                        tipoReto = 2;
                        break;

                    case 2:
                        txt_rtrue.setVisibility(View.GONE);
                        txt_rfalse1.setVisibility(View.GONE);
                        txt_rfalse2.setVisibility(View.GONE);
                        tipoReto = 3;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ImageButton btnMapa = findViewById(R.id.btn_crearReto_mapaLugares);
        btnMapa.setOnClickListener(view -> runApiPlaces(view));

        txt_mensaje.setText("Introduce los datos del reto " + partidaNumerosRetosActual + "/" + partidaNumerosRetos);
    }


    /**
     * Método que muestra un dialogo mientras el reto se crea
     */
    @OnClick(R.id.btn_crearReto_next)
    void ClickBotonReto() {

        if (comprobarcampos()) {
            progressDialog.setMessage("Creando reto...");
            progressDialog.show();
            CargarIDPartida();
        } else {
            Toast.makeText(getApplicationContext(), "Debes de rellenar, todos los campos ", Toast.LENGTH_LONG).show();
        }


    }//End OnClick

    /**
     * Método que comprueba que no hay ningún campo vacío
     * @return true si no hay ningun campo vacío, false de lo contrario
     */
    boolean comprobarcampos() {
        boolean ok = false;

        //Campos obligatorios siempre

        if (
                !txt_puntos.getText().toString().trim().isEmpty() &&
                        !txt_duracion.getText().toString().trim().isEmpty() &&
                        !txt_pregunta.getText().toString().trim().isEmpty() &&
                        !txt_nombre.getText().toString().trim().isEmpty() &&
                        !latitud.isEmpty() && !longitud.isEmpty()
                ) {
            if (tipoReto == 1) {
                if (
                        !txt_rfalse1.getText().toString().trim().isEmpty() &&
                                !txt_rfalse2.getText().toString().trim().isEmpty() &&
                                !txt_rtrue.getText().toString().trim().isEmpty()) {
                    ok = true;
                }
            } else if (tipoReto == 2) {
                if (
                        !txt_rtrue.getText().toString().trim().isEmpty()) {

                    ok = true;
                }
            } else {
                ok = true;
            }


        }

        return ok;
    }

    /**
     * Método que vacía todos lo campos
     */
    void limpiarcampos() {
        txt_puntos.setText("");
        txt_duracion.setText("");
        txt_pregunta.setText("");
        txt_video.setText("");
        txt_nombre.setText("");
        txt_rfalse1.setText("");
        txt_rfalse2.setText("");
        txt_rtrue.setText("");
        latitud = "";
        longitud = "";
    }

    /**
     * Método que inserta las respuestas de un reto de seleccion multiple en la base de datos
     */
    void insertarRespuestas() {
        final String URL = "http://geogame.ml/api/insertar_respuestas.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {

                    cambiarpregunta();


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Ups! ha habido algun error", Toast.LENGTH_SHORT).show();
                }

                Log.e("ON RESPONDE", response.toString());

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
                params.put("idReto", "" + idReto);

                params.put("descripcion1", txt_rtrue.getText().toString().trim());
                params.put("verdadero1", "1");

                params.put("descripcion2", txt_rfalse1.getText().toString().trim());
                params.put("verdadero2", "0");

                params.put("descripcion3", txt_rfalse2.getText().toString().trim());
                params.put("verdadero3", "0");

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }//fin insertarPreguntas

    /**
     * Método que se lanza cuando un reto se rellena por completo, si es el ultimo se vuelve a la actividad principal, si no se rellena el siguiente reto.
     */
    void cambiarpregunta() {
        progressDialog.dismiss();
        partidaNumerosRetosActual++;
        model.setRetoactual(partidaNumerosRetosActual);
        if (partidaNumerosRetosActual <= partidaNumerosRetos) {
            txt_mensaje.setText("Introduce los datos del reto " + partidaNumerosRetosActual + "/" + partidaNumerosRetos);
            limpiarcampos();
            Toast.makeText(getApplicationContext(), "Reto creado, rellena el siguiente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Todos los retos creados, Ya puede jugar la partida!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
            finish();
        }

    }

    /**
     * Método que inserta la respuesta de un reto de respuesta única en la base de datos.
     */
    void insertarRespuesta() {
        final String URL = "http://geogame.ml/api/insertar_respuesta.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {

                    cambiarpregunta();


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Ups! ha habido algun error", Toast.LENGTH_SHORT).show();
                }

                Log.e("ON RESPONDE", response.toString());

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
                params.put("idReto", "" + idReto);

                params.put("descripcion1", txt_rtrue.getText().toString().trim());
                params.put("verdadero1", "1");

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }//fin insertarPregunta

    /**
     * Método que obtiene el id del reto ya que no sabemos cual va a ser hasta que no se inserta en la base de datos.
     */
    void CargarIDReto() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

////////////////////////////////////////////// CargarIDReto

        //  final String URL = "http://geogame.ml/api/obtener_reto_con_datos_de_reto.php?nombre=" + txt_nombre.getText().toString().trim() + "&descripcion=" + txt_pregunta.getText().toString() + "&maxDuracion=" + txt_duracion.getText().toString() + "&tipo="+tipoReto+"&puntuacion=" + txt_puntos.getText().toString() + "&localizacionLatitud=" + latitud + "&localizacionLongitud=" + longitud + "&idPartida=" + idPartida;
        final String URL = "http://geogame.ml/api/obtener_reto_con_datos_de_reto.php?nombre=" + txt_nombre.getText().toString().trim() + "&maxDuracion=" + txt_duracion.getText().toString().trim() + "&tipo=" + tipoReto + "&puntuacion=" + txt_puntos.getText().toString().trim() + "&localizacionLatitud=" + latitud + "&localizacionLongitud=" + longitud + "&idPartida=" + idPartida;

        Log.d("URL SANTI", URL);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                try {
                    JSONArray response = new JSONArray(Cifrar.decrypt(response2));
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject o = response.getJSONObject(i);
                        idReto = o.getInt("idReto");
                        if (tipoReto == 1) {
                            insertarRespuestas();
                        } else if (tipoReto == 2) {
                            insertarRespuesta();
                        } else if (tipoReto == 3) {
                            cambiarpregunta();
                        }
                        Log.e("Obteniendo id reto", "" + idReto);
                    }
                    Log.e("LISTA SACAR RETO", response.toString());

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

        requestQueue.add(request);

    }//End CargarIDReto

    /**
     * Método que obtiene el id de la partida ya que no sabemos cual va a ser hasta que no se inserta en la base de datos.
     */
    void CargarIDPartida() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

////////////////////////////////////////////// Partida

        final String URL = "http://geogame.ml/api/obtener_partida.php?nombre=" + nombrePartida + "&passwd=" + clavePartida;

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                try {
                    JSONArray response = new JSONArray(Cifrar.decrypt(response2));

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject o = response.getJSONObject(i);
                        Log.e("LISTA Partida", "una vuelta");

                        idPartida = o.getInt("idPartida");
                        o.getString("nombre");
                        o.getString("passwd");
                        o.getInt("maxDuracion");
                        InsertarReto();

                    }//endgfor
                    Log.e("LISTA Partida", response.toString());

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

        requestQueue.add(request);

    }//End CargarIDPartida


    /**
     * Método que inserta el reto en la base de datos
     */
    void InsertarReto() {
        //INSERTART
        final String URL = "http://geogame.ml/api/insertar_reto.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) {
                    CargarIDReto();
                } else {
                    Toast.makeText(getApplicationContext(), "Ups! ha habido algun error", Toast.LENGTH_SHORT).show();
                }
                Log.e("ON RESPONDE2", response.toString());

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
                params.put("nombre", txt_nombre.getText().toString().trim());
                params.put("descripcion", txt_pregunta.getText().toString().trim());
                params.put("video", txt_video.getText().toString().trim());
                params.put("maxDuracion", txt_duracion.getText().toString().trim());
                params.put("tipo", "" + tipoReto);
                params.put("puntuacion", txt_puntos.getText().toString().trim());
                params.put("localizacionLatitud", latitud);
                params.put("localizacionLongitud", longitud);
                params.put("idPartida", "" + idPartida);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }//InsertarReto


    /**
     * Método que lanza la API de Google Places para seleccionar la ubicación del reto.
     * @param view
     */
    public void runApiPlaces(View view) {
        try {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            Place place = PlacePicker.getPlace(this, data);

            if (place == null) {
                Log.i(TAG, "ningun lugar seleccionado");
                return;
            }


            latLng = place.getLatLng();

            String lat = String.valueOf(latLng.latitude) + "000000000000000000000000";
            latitud = lat.substring(0, lat.indexOf(".") + 9);

            String lon = String.valueOf(latLng.longitude) + "000000000000000000000000";
            longitud = lon.substring(0, lon.indexOf(".") + 9);



            Toast.makeText(getApplicationContext(), "Ubicacion del reto seleccionada!!", Toast.LENGTH_LONG).show();
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
