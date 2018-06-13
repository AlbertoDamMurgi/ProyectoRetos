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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

import org.iesmurgi.reta2.Chat.ChatAdminActivity;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.LoginActivity;
import org.iesmurgi.reta2.UI.usuario.LoginModel;

/**
 * Actividad principal del administrador
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class AdminMainActivity extends AppCompatActivity {


    private LoginModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        ButterKnife.bind(this);
        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        escuchador();

    }

    @OnClick({R.id.btn_administrarPartida_main_registro, R.id.btn_main_admin_crear_partida, R.id.btn_main_admin_desconectarse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_administrarPartida_main_registro:
                startActivity(new Intent(getApplicationContext(), ListarPartidasAdminActivity.class));
                break;
            case R.id.btn_main_admin_crear_partida:
                startActivity(new Intent(getApplicationContext(),CrearPartida.class));
                break;
            case R.id.btn_main_admin_desconectarse:
                desconectarAdmin();
                break;
        }
    }

    /**
     * Método que desloguea al administrador
     */
    private void desconectarAdmin() {

        mLoginModel.getConection().getValue().getInstance().signOut();
        mLoginModel.getConection().postValue(null);
        mLoginModel.getUsuario().postValue(null);



    }

    /**
     * Método que lanza un escuchador para que si no hay ningun usuario logueado se cargue la actividad de login
     */
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
}
