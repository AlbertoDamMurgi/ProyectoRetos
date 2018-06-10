package org.iesmurgi.reta2.UI.usuario;

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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import org.iesmurgi.reta2.R;

import java.util.HashMap;
import java.util.Map;
/**
 * Actividad que permite al usuario registrarse
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = "Registro_Equipo";
    @BindView(R.id.ed_nombre_equipo)
    EditText nombreequipo;

    @BindView(R.id.ed_pass_equipo)
    EditText passequipo;

    @BindView(R.id.ed_correo_equipo)
    EditText correoequipo;

    @BindView(R.id.ed_participantes_equipo)
    EditText participantesEquipo;

    @BindView(R.id.ed_pass_equipo_confirmar)
    EditText confirmarpass;

    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        mAuth = FirebaseAuth.getInstance();

    }

    /**
     * Método que comprueba si todos los campos estan cumplimentados correctamente
     * @return
     */
    private boolean comprobarRegistro() {
        boolean ok = false;
        if (
                !nombreequipo.getText().toString().trim().isEmpty() && !correoequipo.getText().toString().trim().isEmpty() && !passequipo.getText().toString().trim().isEmpty() && !participantesEquipo.getText().toString().trim().isEmpty()
                ) {
            if (passequipo.getText().toString().trim().length() >= 6) {
                if(confirmarpass.getText().toString().trim().equals(passequipo.getText().toString().trim())) {
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
     * Método que registra al equipo en la base de datos
     */
    @OnClick(R.id.btn_registro_equipo)
    void registrarEquipo() {

        if (comprobarRegistro()) {
            mAuth.createUserWithEmailAndPassword(correoequipo.getText().toString().trim(), passequipo.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");

                                mLoginModel.getConection().setValue(mAuth);
                                mLoginModel.getUsuario().setValue(mAuth.getInstance().getCurrentUser());


                                String name = nombreequipo.getText().toString().trim();
                                UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();

                                mLoginModel.getUsuario().getValue().updateProfile(update)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Insertar a la base de datos


                                                    final String URL = "http://geogame.ml/api/insertar_equipo.php";
                                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.e("ON RESPONDE", response.toString());

                                                            if (response.contains("success")) {
                                                                Toast.makeText(RegistroActivity.this, "Registrado con exito ",
                                                                        Toast.LENGTH_SHORT).show();
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
                                                            params.put("username", nombreequipo.getText().toString().trim());
                                                            params.put("correo", correoequipo.getText().toString().trim());
                                                            params.put("passwd", "" + passequipo.getText().toString().trim());
                                                            params.put("participantes", "" + participantesEquipo.getText().toString().trim());
                                                            return params;
                                                        }
                                                    };

                                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                                    requestQueue.add(stringRequest);


                                                    // fin insertar a db


                                                } else {
                                                    Toast.makeText(RegistroActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistroActivity.this, "Correo no valido ",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


        }
    }//Onclick
}
