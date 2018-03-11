package geogame.proyectoretos.UI;

import android.arch.lifecycle.ViewModelProviders;
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

import butterknife.BindView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);



        if (misRespuestas.isEmpty()){

            rellenarArray();
        }

        //rellenado de los radioBtn aleatoriamente

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

        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <misRespuestas.size() ; i++) {
                    if (misRespuestas.get(i).getDescripcion().equals(resElegida)){

                        if (misRespuestas.get(i).getVerdadero()==1){

                            Toast.makeText(getApplicationContext(), "Has acertado!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Has fallado!! no puntuas!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "No puntas este reto...", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void rellenarArray(){
        miReto = new Retos(1,"La fuente","cual es el numero total de cabezas de leon, blablalblablabllablbablblablalab", 180,1,10, 2.3451,23.234,1 );

        Respuestas res1 = new Respuestas(1, 1, "cuatro",1);
        Respuestas res2 = new Respuestas(2, 1, "siete",0);
        Respuestas res3 = new Respuestas(3, 1, "ninguno",0);

        misRespuestas.add(res1);
        misRespuestas.add(res2);
        misRespuestas.add(res3);
        misResBorrar.add(res1);
        misResBorrar.add(res2);
        misResBorrar.add(res3);
    }


    public int elegirRandom(int min, int max) {

        int rango = (max - min) + 1;
        return (int)(Math.random() * rango) + min;
    }
}
