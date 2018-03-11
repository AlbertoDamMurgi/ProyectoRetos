package geogame.proyectoretos.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import geogame.proyectoretos.Data.BasedeDatosApp;
import geogame.proyectoretos.Data.DAOS.RespuestasDao;
import geogame.proyectoretos.Data.DAOS.RetosDao;
import geogame.proyectoretos.Data.entidades.Respuestas;
import geogame.proyectoretos.Data.entidades.Retos;
import geogame.proyectoretos.R;

public class RetoActivity extends AppCompatActivity {
    private ArrayList<Respuestas> misRespuestas = new ArrayList<>();
    private ArrayList<Respuestas> misResBorrar = new ArrayList<>();
    private Retos miReto;
    private String resElegida;

    @BindView(R.id.txt_reto_nombre)
    TextView nombre;

    @BindView(R.id.txt_reto_descripcion)
    TextView descr;

    @BindView(R.id.btn_reto_responder)
    Button btnResponder;

    @BindView(R.id.btn_reto_cancelar)
    Button btnCancel;

    @BindView(R.id.rg_reto_rgrupo)
    RadioGroup btnGrupo;

    @BindView(R.id.rb_reto_opcion1)
    RadioButton radio1;

    @BindView(R.id.rb_reto_opcion2)
    RadioButton radio2;

    @BindView(R.id.rb_reto_opcion3)
    RadioButton radio3;

    @BindView(R.id.txt_reto_crono)
    TextView crono;

    private  LoginModel cronoViewModel;
    private long nTiempo;

    private RetosDao retosDao;
    private RespuestasDao respuestasDao;
    private int idpartida;
    private int idreto;
    private List<Respuestas> respuestas = new ArrayList<>();
    private boolean salida=false;

    class recRes extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... p) {

            respuestas = respuestasDao.getRespuestas(p[0]);
            return null;
        }


    }

   class RecReto extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... p) {

            miReto = retosDao.getReto_Partida(p[0],p[1]);
            return  null;
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);




        int [] aux = getIntent().getExtras().getIntArray("PARTIDAYRETO");
        Log.e("idpartida",""+aux[0]);
        Log.e("idreto",""+aux[1]);

        retosDao = BasedeDatosApp.getAppDatabase(this).retosDao();
        new RecReto().execute(aux[0],aux[1]);


        respuestasDao = BasedeDatosApp.getAppDatabase(this).respuestasDao();
        new recRes().execute(aux[1]);



        //rellenado de los radioBtn aleatoriamente
        Log.e("respuestas",""+respuestas.size());
        Log.e("retos",""+miReto.getNombre());

        if(misRespuestas.isEmpty()||misResBorrar.isEmpty()){

            misResBorrar.clear();
            misRespuestas.clear();
            rellenarArray();

        }

        int selec =  elegirRandom(0, misResBorrar.size()-1);
        radio1.setText(String.valueOf(misResBorrar.get(selec).getDescripcion()));
        misResBorrar.remove(selec);
        int selec2 =  elegirRandom(0, misResBorrar.size()-1);
        radio2.setText(String.valueOf(misResBorrar.get(selec2).getDescripcion()));
        misResBorrar.remove(selec2);
        int selec3 =  0;
        radio3.setText(String.valueOf(misResBorrar.get(selec3).getDescripcion()));
        misResBorrar.remove(selec3);


        nombre.setText(String.valueOf(miReto.getNombre()));
        descr.setText(String.valueOf(miReto.getDescripcion()));



        //este bloque es el control de la cuenta atras
        cronoViewModel = ViewModelProviders.of(this).get(LoginModel.class);

        Log.v("mitiempo", String.valueOf(cronoViewModel.getMiTiempo()));

        long timeRec = miReto.getMaxDuracion() * 1000;
        if (cronoViewModel.getMiTiempo()==null){
            cronoViewModel.setMiTiempo(timeRec);
            new CountDownTimer(timeRec, 1000) {

                public void onTick(long tiempo) {

                    crono.setText("Tiempo: " + tiempo / 1000);
                    cronoViewModel.setMiTiempo(tiempo);
                }

                public void onFinish() {
                    crono.setText("No puntuas!");
                    cronoViewModel.setMiTiempo((long) 0);
                    if(!salida) {
                        salida=true;
                        setResult(MapPrincActivity.RESULT_OK, new Intent(getApplicationContext(), MapPrincActivity.class));
                    }
                }
            }.start();

        }else{

            final long ntiempo = cronoViewModel.getMiTiempo();
            new CountDownTimer(ntiempo, 1000) {

                public void onTick(long ntiempo) {
                    crono.setText("Tiempo: " + ntiempo / 1000);
                    cronoViewModel.setMiTiempo(ntiempo);
                }

                public void onFinish() {
                    crono.setText("No puntuas!");
                    cronoViewModel.setMiTiempo((long) 0);
                    setResult(MapPrincActivity.RESULT_OK,new Intent(getApplicationContext(),MapPrincActivity.class));
                    if(!salida) {
                        salida = true;
                        setResult(MapPrincActivity.RESULT_OK, new Intent(getApplicationContext(), MapPrincActivity.class));
                    }
                }
            }.start();

        }

        Log.v("mitiempo", String.valueOf(cronoViewModel.getMiTiempo()));


        //cuando cambia la seleccion setea la resElegida con el string del radiobuton
        btnGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int idBoton) {
                if (idBoton==R.id.rb_reto_opcion1){

                    resElegida=String.valueOf(radio1.getText());

                } else if (idBoton==R.id.rb_reto_opcion2){

                    resElegida=String.valueOf(radio2.getText());

                }else if (idBoton==R.id.rb_reto_opcion3){

                    resElegida=String.valueOf(radio3.getText());
                }
            }
        });

        //fin aqui
        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <respuestas.size() ; i++) {
                    if (respuestas.get(i).getDescripcion().equals(resElegida)){

                        if (respuestas.get(i).getVerdadero()==1){

                            Toast.makeText(getApplicationContext(), "Has acertado!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Has fallado!! no puntuas!!", Toast.LENGTH_SHORT).show();
                        }

                        setResult(MapPrincActivity.RESULT_OK, new Intent(getApplicationContext(), MapPrincActivity.class));
                    }
                }
            }
        });

        //fin aqui
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "No puntas este reto...", Toast.LENGTH_SHORT).show();
                setResult(MapPrincActivity.RESULT_OK, new Intent(getApplicationContext(), MapPrincActivity.class));
            }
        });


    }

    public void rellenarArray(){

        for (int i = 0; i <respuestas.size() ; i++) {
            misRespuestas.add(respuestas.get(i));

            misResBorrar.add(respuestas.get(i));

        }


    }


    public int elegirRandom(int min, int max) {

        int rango = (max - min) + 1;
        return (int)(Math.random() * rango) + min;
    }
}
