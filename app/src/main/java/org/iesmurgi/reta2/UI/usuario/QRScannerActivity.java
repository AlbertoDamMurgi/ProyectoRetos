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
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.Data.entidades.Respuestas;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.Seguridad.Cifrar;
import org.iesmurgi.reta2.UI.retos.MapPrincActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Actividad que permite al usuario escanear un codigo qr con los datos de la partida
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class QRScannerActivity extends AppCompatActivity {

    @BindView(R.id.sw_scanner_codePreview)
    SurfaceView sw_previewCodigoQR;
    @BindView(R.id.txt_scanner_result)
    TextView txt_result;
    BasedeDatosApp db;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    private String cadenaCodigo;
    static final int PEDIR_PERMISOS_COD = 1001;
    boolean comprobar=true;
    private int ID_PARTIDA;
    private String NOMBREPARTIDA;
    private int ULTIMORETO;
    int idUsuario;
    ProgressDialog progressDialog;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PEDIR_PERMISOS_COD:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }

                    try {
                        cameraSource.start(sw_previewCodigoQR.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        db = BasedeDatosApp.getAppDatabase(this);
        progressDialog = new ProgressDialog(QRScannerActivity.this);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        idUsuario=getIntent().getIntExtra("idUsuario",0);
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();

        sw_previewCodigoQR.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(QRScannerActivity.this,
                            new String[]{Manifest.permission.CAMERA}, PEDIR_PERMISOS_COD);
                    return;
                }


                //////
                try {
                    cameraSource.start(sw_previewCodigoQR.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    if (comprobar) {
                        cadenaCodigo = qrcodes.valueAt(0).displayValue;
                        Log.e("SANTI", "cadenaCodigo" + cadenaCodigo);
                        comprobar=false;

                        new cargarPartida().execute();
                    }
                }

            }
        });
    }


    /**
     * Tarea asincrona que obtiene la partida de la base de datos
     */
    class cargarRespuesta extends AsyncTask<Respuestas, Void, Integer> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected Integer doInBackground(Respuestas... r) {

            final String URL3 = "http://geogame.ml/api/obtener_respuestas.php?nombre=" + NOMBREPARTIDA;


            StringRequest request3 = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
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

                    Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
                }
            });


            requestQueue.add(request3);


            return 0;
        }
    }

    /**
     * Método que inserta al equipo en la partida y obtiene el reto por el que va el equipo
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

        final String URL2 = "http://geogame.ml/api/Lista_Retos_Clave.php?nombre=" + NOMBREPARTIDA;


        @Override
        protected Integer doInBackground(Void... r) {
            StringRequest request2 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
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
                        Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_LONG).show();
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
     * Tarea asincrona que carga la partida actual
     */
    class cargarPartida extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... r) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            final String URL = "http://geogame.ml/api/obtener_partida_codigoqr.php?codeqr="+cadenaCodigo;

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
                        progressDialog.setMessage("Cargando partida ...");
                        // progressDialog.setCancelable(false);
                        progressDialog.show();
                        InsertarEnPartidaYObtenerUltima();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Datos erroneos", Toast.LENGTH_LONG).show();
                        comprobar=true;
                    }
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
}
