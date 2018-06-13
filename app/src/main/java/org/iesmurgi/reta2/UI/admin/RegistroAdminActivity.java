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

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.LoginModel;

import java.util.HashMap;
import java.util.Map;
/**
 * Actividad que permite al administrador registrarse en la aplicación
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class RegistroAdminActivity extends AppCompatActivity {

    private static final String TAG = "REGISTROADMIN";
    @BindView(R.id.ed_correo_admin_registrar)
    EditText correoadmin;
    @BindView(R.id.ed_pass_equipo_admin_registrar)
    EditText passadmin;
    @BindView(R.id.ed_pass_admin_confirmar)
    EditText passadminconfirmar;

    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_admin);

        ButterKnife.bind(this);

        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        mAuth = FirebaseAuth.getInstance();


    }

    /**
     * Método que comprueba si todos los campos estan rellenos y la pass tiene la longitud adecuada.
     * @return true o false
     */
    private boolean comprobarRegistro() {
        boolean ok = false;
        if (
                !correoadmin.getText().toString().trim().isEmpty() && !passadmin.getText().toString().trim().isEmpty()
                ) {
            if (passadmin.getText().toString().trim().toCharArray().length >= 6) {
                if(passadminconfirmar.getText().toString().trim().equals(passadmin.getText().toString().trim())) {
                    ok = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Debes confirmar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"La contraseña debe tener 6 caracteres o mas ",Toast.LENGTH_LONG ).show();
            }


        }else{
            Toast.makeText(getApplicationContext(),"Rellena todos los campos",Toast.LENGTH_LONG ).show();

        }
        return ok;
    }

    /**
     * Método que inserta los datos del administrador en la base de datos
     */
    @OnClick(R.id.btn_registro_admin_registrar)
    void registrarAdmin() {

if (comprobarRegistro()){
        mAuth.createUserWithEmailAndPassword(correoadmin.getText().toString().trim(), passadmin.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            mLoginModel.getConection().setValue(mAuth);
                            mLoginModel.getUsuario().setValue(mAuth.getInstance().getCurrentUser());


                            String name = "Administrador";
                            UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            mLoginModel.getUsuario().getValue().updateProfile(update)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                //Registrar admin en db


                                                final String URL = "http://geogame.ml/api/insertar_admin.php";
                                                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {


                                                        if (response.contains("success")) {
                                                            finish();

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
                                                        params.put("username", correoadmin.getText().toString().trim());
                                                        params.put("passwd", "" + passadmin.getText().toString().trim());

                                                        return params;
                                                    }
                                                };

                                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                requestQueue.add(stringRequest);


                                                // fin insertar a db


                                            } else {
                                                Toast.makeText(RegistroAdminActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            // ...

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroAdminActivity.this, "Correo no valido",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }}

}
