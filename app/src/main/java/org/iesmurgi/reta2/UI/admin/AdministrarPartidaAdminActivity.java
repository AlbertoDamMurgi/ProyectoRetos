package org.iesmurgi.reta2.UI.admin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.Chat.ChatAdminActivity;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.RankingActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdministrarPartidaAdminActivity extends AppCompatActivity {


    private String sala;
    private int idpartida;
    private String codigoqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_partida_admin);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        sala = getIntent().getExtras().getString("PARTIDA");
        idpartida = getIntent().getExtras().getInt("ID");
        codigoqr=getIntent().getStringExtra("codigoqr");
        Log.e("partida",""+sala);

    }


    @OnClick(R.id.fab_chat_admin)
        void abrirChat(){
            startActivity(new Intent(getApplicationContext(),ChatAdminActivity.class).putExtra("SALA",sala));
        }

    @OnClick(R.id.btn_admin_mapa)
        void abrirMapa(){
            startActivity(new Intent(getApplicationContext(),AdminMapaActivity.class).putExtra("PARTIDA",sala));
        }

    @OnClick(R.id.btn_admin_verjugadores3)
        void verJugadores(){
            startActivity(new Intent(getApplicationContext(),AdminVerJugadoresActivity.class).putExtra("PARTIDA",sala));
        }
    @OnClick(R.id.btn_admin_PuntuarFotos)
        void verFotos(){
            startActivity(new Intent(getApplicationContext(),PuntuarFotosAdmin.class).putExtra("PARTIDA",sala).putExtra("IDPARTIDA",idpartida));
        }

    @OnClick(R.id.btn_admin_ranking)
        void verRanking(){
            startActivity(new Intent(getApplicationContext(),RankingActivity.class).putExtra("PARTIDA",sala).putExtra("idPartida",idpartida));
        }
    @OnClick(R.id.btn_admin_codigoQR)
        void verCodigoQR(){

            startActivity(new Intent(getApplicationContext(),QRGeneratorActivity.class).putExtra("codigoqr",codigoqr));


        }


}
