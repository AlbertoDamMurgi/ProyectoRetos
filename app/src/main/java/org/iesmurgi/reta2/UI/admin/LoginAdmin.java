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

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.Seguridad.Cifrar;
import org.iesmurgi.reta2.Seguridad.Permisos;
import org.iesmurgi.reta2.UI.usuario.AcercaDeActivity;
import org.iesmurgi.reta2.UI.usuario.LoginModel;
import org.iesmurgi.reta2.UI.usuario.RegistroActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase que permite al usuario logearse en la aplicación
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class LoginAdmin extends AppCompatActivity implements LifecycleObserver {
 int idAdmin;
    private static final String TAG = "LOGIN_ADMIN";
    @BindView(R.id.et_email_admin)
    EditText email;
    @BindView(R.id.et_pass_admin)
    EditText pass;
    @BindView(R.id.toolbar_login_admin)
    Toolbar toolbar;

    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        //libreria butterknife para los bindeos
        ButterKnife.bind(this);
        //obeservador del ciclo de vida
        this.getLifecycle().addObserver(this);
        //enlace con el viewmodel
        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        setSupportActionBar(toolbar);

        //conexion con la base de datos
        mAuth = FirebaseAuth.getInstance();

        observador();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_admin_menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_admin:
                startActivity(new Intent(getApplicationContext(),RegistroAdminActivity.class));
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    /**
     * Método que comprueba que se ha logueado un administrador
     */
    private void observador() {

        mLoginModel.getConection().observe(this, new Observer<FirebaseAuth>() {
            @Override
            public void onChanged(@Nullable FirebaseAuth firebaseAuth) {
                if (firebaseAuth != null) {
                    try {
                        if (firebaseAuth.getCurrentUser().getDisplayName().equalsIgnoreCase("administrador")) {

                            startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));


                        } else {
                            Toast.makeText(LoginAdmin.this, "Credenciales erroneas.", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }catch (NullPointerException ex){
                        Log.e("KiRil","kiril el maestro");
                    }
                }
            }
        });

    }


    /**
     * Método que conecta con Firebase y se loguea
     */
    @OnClick(R.id.btn_conectarse_admin)
    void conectarUsuario() {
        if (Permisos.comprobarPermisos(this,this)) {
            if (!email.getText().toString().trim().isEmpty() && !pass.getText().toString().trim().isEmpty()) {
                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    mLoginModel.getUsuario().setValue(mAuth.getInstance().getCurrentUser());
                                    mLoginModel.getConection().setValue(mAuth);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginAdmin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            } else {
                Toast.makeText(getApplicationContext(), "Porfavor rellena todos los campos.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Necesitas todos los permisos para poder entrar", Toast.LENGTH_LONG).show();

        }
    }
/*
    void obtenerIDyLanzar(){


        //Sacamos la id de usaario
        final String URL = "http://geogame.ml/api/obtener_admin.php?correo="+email.getText().toString().trim()+"&passwd="+email.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                try {
                JSONArray response = new JSONArray(Cifrar.decrypt(response2));
                for (int i = 0; i < response.length(); i++) {
                        JSONObject o = response.getJSONObject(i);
                        idAdmin=o.getInt("idAdmin");
                }
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class).putExtra("idAdmin",idAdmin));

                finish();
                //endgfor
                Log.e("ADMIN RESPONDE ", response.toString());
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }*/


}



