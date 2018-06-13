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

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.Chat.ChatAdminActivity;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.RankingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Actividad que proporciona la interfaz necesaria para administrar la partida desde el perfil de administrador
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class AdministrarPartidaAdminActivity extends AppCompatActivity {


    private String sala;
    private int idpartida;
    private String codigoqr;

    @BindView(R.id.toolbar_administrar_partida)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_partida_admin);
        ButterKnife.bind(this);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);

        sala = getIntent().getExtras().getString("PARTIDA");
        idpartida = getIntent().getExtras().getInt("ID");
        codigoqr=getIntent().getStringExtra("codigoqr");
        Log.e("partida",""+sala);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.administrar_partida_menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mapa_administrar_partida:
                startActivity(new Intent(getApplicationContext(),AdminMapaActivity.class).putExtra("PARTIDA",sala).putExtra("idPartida",idpartida));
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Método que lanza la actividad de chat del administrador
     * @see ChatAdminActivity
     */
    @OnClick(R.id.fab_chat_admin)
        void abrirChat(){
            startActivity(new Intent(getApplicationContext(),ChatAdminActivity.class).putExtra("SALA",sala));
        }
/*
    @OnClick(R.id.btn_admin_mapa)
        void abrirMapa(){
            startActivity(new Intent(getApplicationContext(),AdminMapaActivity.class).putExtra("PARTIDA",sala));
        }
*/

    /**
     * Método que lanza la actividad de ver jugadores y sus equipos
     * @see AdminVerJugadoresActivity
     */
    @OnClick(R.id.btn_admin_verjugadores3)
        void verJugadores(){
            startActivity(new Intent(getApplicationContext(),AdminVerJugadoresActivity.class).putExtra("idPartida",idpartida));
        }

    /**
     * Método que lanza la actividad de puntuar las fotos
     * @see PuntuarFotosAdmin
     */
    @OnClick(R.id.btn_admin_PuntuarFotos)
        void verFotos(){
            startActivity(new Intent(getApplicationContext(),PuntuarFotosAdmin.class).putExtra("PARTIDA",sala).putExtra("IDPARTIDA",idpartida).putExtra("codigoqr",codigoqr));
        }

    /**
     * Método que lanza la actividad de ver el ranking de los equipos
     * @see RankingActivity
     */
    @OnClick(R.id.btn_admin_ranking)
        void verRanking(){
            startActivity(new Intent(getApplicationContext(),RankingActivity.class).putExtra("PARTIDA",sala).putExtra("idPartida",idpartida));
        }

    /**
     * Método que lanza la actividad de crear un código QR con los datos de la partida
     * @see QRGeneratorActivity
     */
    @OnClick(R.id.btn_admin_codigoQR)
        void verCodigoQR(){

            startActivity(new Intent(getApplicationContext(),QRGeneratorActivity.class).putExtra("codigoqr",codigoqr));


        }


}
