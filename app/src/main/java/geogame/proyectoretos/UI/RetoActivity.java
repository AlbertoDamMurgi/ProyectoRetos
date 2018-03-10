package geogame.proyectoretos.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import geogame.proyectoretos.R;

public class RetoActivity extends AppCompatActivity {
    long tiempo = 120000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto);


        LoginModel cronoViewModel = ViewModelProviders.of(this).get(LoginModel.class);
        TextView crono = findViewById(R.id.txt_reto_crono);

        Log.v("mitiempo", String.valueOf(cronoViewModel.getMiTiempo()));
        if (cronoViewModel.getMiTiempo()==null){
            cronoViewModel.setMiTiempo(tiempo);
            new CountDownTimer(tiempo, 1000) {

                public void onTick(long tiempo) {

                    crono.setText("Tiempo: " + tiempo / 1000);
                    cronoViewModel.setMiTiempo(tiempo);
                }

                public void onFinish() {
                    crono.setText("No puntuas!");
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
                }
            }.start();

        }

        Log.v("mitiempo", String.valueOf(cronoViewModel.getMiTiempo()));


    }
}
