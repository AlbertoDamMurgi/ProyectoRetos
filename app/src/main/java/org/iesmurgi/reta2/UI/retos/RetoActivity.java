package org.iesmurgi.reta2.UI.retos;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.Data.DAOS.RespuestasDao;
import org.iesmurgi.reta2.Data.DAOS.RetosDao;
import org.iesmurgi.reta2.Data.entidades.Partidas;
import org.iesmurgi.reta2.Data.entidades.Respuestas;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.LoginModel;

public class RetoActivity extends AppCompatActivity {

    @BindView(R.id.rb_reto_opcion1)
    RadioButton rbRetoOpcion1;
    @BindView(R.id.rb_reto_opcion2)
    RadioButton rbRetoOpcion2;
    @BindView(R.id.rb_reto_opcion3)
    RadioButton rbRetoOpcion3;
    @BindView(R.id.rg_reto_rgrupo)
    RadioGroup rgRetoRgrupo;
    @BindView(R.id.txt_reto_descripcion)
    TextView txtRetoDescripcion;
    @BindView(R.id.btn_reto_responder)
    Button btnRetoResponder;
    @BindView(R.id.btn_reto_cancelar)
    Button btnRetoCancelar;
    @BindView(R.id.txt_reto_crono)
    TextView txtRetoCrono;
    @BindView(R.id.txt_reto_nombre)
    TextView txtRetoNombre;
    @BindView(R.id.btn_reto_verVideo)
    Button btnRetoVerVideo;
    @BindView(R.id.et_reto_respuestaUnica)
    EditText etRetoRespuestaUnica;
    @BindView(R.id.btn_reto_subirImagen)
    Button btnRetoSubirImagen;

    ProgressDialog progressDialog;


    private List<Respuestas> misRespuestas = new ArrayList<>();
    private List<Respuestas> misResBorrar = new ArrayList<>();
    private Retos miReto;
    private String resElegida;


    private LoginModel cronoViewModel;
    private long nTiempo;

    private RetosDao retosDao;
    private RespuestasDao respuestasDao;
    private int idpartida;
    private int idreto;
    private List<Respuestas> respuestas = new ArrayList<>();
    private boolean salida = false;
    private String nombrepartida;
    BasedeDatosApp db;

    int[] aux;



    class recRes extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... p) {


            respuestas = db.respuestasDao().getRespuestas(p[0]);
            Log.e("respuestas", "" + respuestas.size());

            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            init();
        }


    }

    class RecReto extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... p) {


            miReto = db.retosDao().getReto_Partida(p[0], p[1]);
            Log.e("retos", "" + miReto.getNombre());

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new recRes().execute(aux[1]);

            new NomPartida().execute(aux[0]);

        }
    }

    class NomPartida extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... p) {


            nombrepartida = db.partidasDao().getPartidaActual(p[0]);


            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

        db = BasedeDatosApp.getAppDatabase(this);

        aux = getIntent().getExtras().getIntArray("PARTIDAYRETO");
        Log.e("idpartida", "" + aux[0]);
        Log.e("idreto", "" + aux[1]);

        //rellenado de los radioBtn aleatoriamente

        new RecReto().execute(aux[0], aux[1]);


    }


    void init() {


        misResBorrar.clear();
        misRespuestas.clear();
        rellenarArray();
        Log.i("COMPROBAR misResBorrar:", misResBorrar.size() + "");
        Log.i("COMPROBAR misRespuesta:", misRespuestas.size() + "");

        int tipoReto = miReto.getTipo();

        

        switch (tipoReto){
            //segun el tipo de reto mostrará los campos correspondientes

            case 1:     //tipo multirespuesta

                rbRetoOpcion1.setVisibility(View.VISIBLE);
                rbRetoOpcion2.setVisibility(View.VISIBLE);
                rbRetoOpcion3.setVisibility(View.VISIBLE);
                etRetoRespuestaUnica.setVisibility(View.GONE);
                btnRetoSubirImagen.setVisibility(View.GONE);

                int selec = elegirRandom(0, misResBorrar.size() - 1);
                rbRetoOpcion1.setText(misResBorrar.get(selec).getDescripcion());
                misResBorrar.remove(selec);
                int selec2 = elegirRandom(0, misResBorrar.size() - 1);
                rbRetoOpcion2.setText(misResBorrar.get(selec2).getDescripcion());
                misResBorrar.remove(selec2);
                int selec3 = 0;
                rbRetoOpcion3.setText(misResBorrar.get(selec3).getDescripcion());
                misResBorrar.remove(selec3);

                break;

            case 2:     //tipo respuesta unica

                rbRetoOpcion1.setVisibility(View.GONE);
                rbRetoOpcion2.setVisibility(View.GONE);
                rbRetoOpcion3.setVisibility(View.GONE);
                etRetoRespuestaUnica.setVisibility(View.VISIBLE);
                btnRetoSubirImagen.setVisibility(View.GONE);
                break;

            case 3:     //tipo subir imagen
                rbRetoOpcion1.setVisibility(View.GONE);
                rbRetoOpcion2.setVisibility(View.GONE);
                rbRetoOpcion3.setVisibility(View.GONE);
                etRetoRespuestaUnica.setVisibility(View.GONE);
                btnRetoSubirImagen.setVisibility(View.VISIBLE);
                break;

        }





        txtRetoNombre.setText(miReto.getNombre());
        txtRetoDescripcion.setText(miReto.getDescripcion());


        //este bloque es el control de la cuenta atras
        cronoViewModel = ViewModelProviders.of(this).get(LoginModel.class);

        Log.v("mitiempo", "" + cronoViewModel.getMiTiempo());

        long timeRec = miReto.getMaxDuracion() * 1000*60;
        if (cronoViewModel.getMiTiempo() == null) {
            cronoViewModel.setMiTiempo(timeRec);
            new CountDownTimer(timeRec, 1000) {

                public void onTick(long tiempo) {
                    int mins =(int) (tiempo/(1000*60))%60;
                    int seg = (int) (tiempo/1000)%60;
                    txtRetoCrono.setText("Tiempo: " + mins+":"+seg);
                    cronoViewModel.setMiTiempo(tiempo);
                }

                public void onFinish() {
                    txtRetoCrono.setText("No puntuas!");
                    cronoViewModel.setMiTiempo((long) 0);
                    if (!salida) {
                        salida = true;
                        insertarPuntos(0,"Se acabo el tiempo, puntuas 0");

                    }
                }
            }.start();

        } else {

            final long ntiempo = cronoViewModel.getMiTiempo();
            new CountDownTimer(ntiempo, 1000) {

                public void onTick(long ntiempo) {
                    int mins =(int) (ntiempo/(1000*60))%60;
                    int seg = (int) (ntiempo/1000)%60;
                    txtRetoCrono.setText("Tiempo: " +mins+":"+seg);
                    cronoViewModel.setMiTiempo(ntiempo);
                }

                public void onFinish() {
                    txtRetoCrono.setText("No puntuas!");
                    cronoViewModel.setMiTiempo((long) 0);
                    insertarPuntos(0,"Se acabo el tiempo, puntuas 0");

                    if (!salida) {
                        salida = true;
                        insertarPuntos(0,"Se acabo el tiempo, puntuas 0");

                    }
                }
            }.start();

        }

        Log.v("mitiempo", String.valueOf(cronoViewModel.getMiTiempo()));




        //cuando cambia la seleccion setea la resElegida con el string del radiobuton
        rgRetoRgrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int idBoton) {
                if (idBoton == R.id.rb_reto_opcion1) {

                    resElegida = rbRetoOpcion1.getText().toString();

                } else if (idBoton == R.id.rb_reto_opcion2) {

                    resElegida = rbRetoOpcion2.getText().toString();

                } else if (idBoton == R.id.rb_reto_opcion3) {

                    resElegida = rbRetoOpcion3.getText().toString();
                }
            }
        });


        //fin aqui
        btnRetoResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //segun el tipo de reto controla la respuesta de diferentes maneras
                switch (tipoReto){

                    case 1:     //tipo multirespuesta

                        for (int i = 0; i < respuestas.size(); i++) {
                            if (respuestas.get(i).getDescripcion().equals(resElegida)) {

                                if (respuestas.get(i).getVerdadero() == 1) {

                                    insertarPuntos(miReto.getPuntuacion(),"Acertaste!! puntuas:"+ miReto.getPuntuacion());
                                } else {
                                    insertarPuntos(0,"Has fallado el reto, puntuas 0");
                                }
                            }
                        }

                        break;

                    case 2:     //tipo respuesta unica

                        if (!etRetoRespuestaUnica.getText().toString().isEmpty()){
                            if (etRetoRespuestaUnica.getText().toString().equals(respuestas.get(0))){

                                insertarPuntos(miReto.getPuntuacion(),"Acertaste!! puntuas:"+ miReto.getPuntuacion());
                            }else{
                                insertarPuntos(0,"Has fallado el reto, puntuas 0");
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Introduce una respuesta!!", Toast.LENGTH_SHORT).show();
                        }



                        break;

                    case 3:     //tipo subir imagen

                        break;

                }

            }
        });

        btnRetoVerVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RetoVideoActivity.class);
                startActivity(i);
            }
        });

        //fin aqui
        btnRetoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertarPuntos(0,"Saltaste el reto, puntuas 0");
            }
        });


    }


    @OnClick(R.id.btn_reto_subirImagen)
    void abrirRetoFoto(){

        startActivity(new Intent(getApplicationContext(),RetoFotoActivity.class).putExtra("PARTIDA", nombrepartida));

    }

    public void rellenarArray() {

        for (int i = 0; i < respuestas.size(); i++) {
            misRespuestas.add(respuestas.get(i));

            misResBorrar.add(respuestas.get(i));

        }

    }

    void insertarPuntos(int puntos,String mensaje) {
        progressDialog.setMessage("Insertando tu puntuacion ...");
       // progressDialog.setCancelable(false);
        progressDialog.show();
        final String URL = "http://geogame.ml/api/insertar_puntuacion.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ON RESPONDE", response.toString());

                if (response.contains("success")) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                    setResult(MapPrincActivity.RESULT_OK, new Intent(getApplicationContext(), MapPrincActivity.class));
                    finish();

                } else {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fallo del servidor", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idUsuario", "" + 1);
                params.put("idReto", "" + miReto.getIdReto());
                params.put("tiempo", "11" );
                params.put("puntuacion", ""+puntos);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }//fin insertarPuntos

    public int elegirRandom(int min, int max) {

        int rango = (max - min) + 1;
        return (int) (Math.random() * rango) + min;

    }
}
