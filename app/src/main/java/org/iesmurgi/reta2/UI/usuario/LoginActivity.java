package org.iesmurgi.reta2.UI.usuario;

import android.Manifest;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.Seguridad.Cifrar;
import org.iesmurgi.reta2.Seguridad.Permisos;
import org.iesmurgi.reta2.UI.admin.LoginAdmin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LifecycleObserver {

    int idUsuario;
    private static final String TAG = "LOGIN";
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_pass)
    EditText pass;
    @BindView(R.id.toolbar_login)
    Toolbar toolbar;

    SharedPreferences prefs;


    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //libreria butterknife para los bindeos
        ButterKnife.bind(this);
        //obeservador del ciclo de vida
        this.getLifecycle().addObserver(this);
        //enlace con el viewmodel
        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);
        //conexion con la base de datos
        mAuth = FirebaseAuth.getInstance();

        setSupportActionBar(toolbar);


        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String userGuardado = prefs.getString("usuario", "");
        String passGuardado = prefs.getString("pass", "");
        email.setText(userGuardado);
        pass.setText(passGuardado);
        if (!email.getText().toString().isEmpty() && !pass.getText().toString().trim().isEmpty()) {
            conectarUsuario();
        }
    }

    /*
    private void observador() {

        mLoginModel.getConection().observe(this, new Observer<FirebaseAuth>() {
            @Override
            public void onChanged(@Nullable FirebaseAuth firebaseAuth) {
                if(firebaseAuth!=null&&!firebaseAuth.getCurrentUser().getDisplayName().equalsIgnoreCase("administrador")){



                }else{
                    //startActivity(new Intent(getApplicationContext(),LoginAdmin.class));
                    Toast.makeText(LoginActivity.this, "Credenciales erroneas.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_usuario_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_user:
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
                return true;

            case R.id.action_about:
                Intent i = new Intent(getApplicationContext(), AcercaDeActivity.class);
                startActivity(i);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

/*

    @OnClick(R.id.bt_register)
    void stratRetistro(){

    }

*/
/*
    @OnClick(R.id.btn_registrarse)
    void registrarAdmin() {



        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            mLoginModel.getConection().setValue(mAuth);
                            mLoginModel.getUsuario().setValue(mAuth.getInstance().getCurrentUser());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });


    }
    */

    @OnLongClick(R.id.loginsecreto_admin)
    boolean loginAdminSecreto() {

        startActivity(new Intent(getApplicationContext(), LoginAdmin.class));

        return true;
    }


    @OnClick(R.id.btn_conectarse)
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

                                    if (!mLoginModel.getConection().getValue().getCurrentUser().getDisplayName().equalsIgnoreCase("administrador")) {


                                        obtenerid();

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Credenciales erroneas.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
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

    void obtenerid() {

        //Sacamos la id de usaario
        final String URL = "http://geogame.ml/api/obtener_usuario.php?correo=" + email.getText().toString().trim() + "&passwd=" + pass.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                try {
                    JSONArray response = new JSONArray(Cifrar.decrypt(response2));
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject o = response.getJSONObject(i);
                        idUsuario = o.getInt("idUsuario");
                    }
                    guardarylanzar();
                    Log.e("LISTA SACAR RETO", response.toString());
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

    }//End

/*
    @OnClick(R.id.fab_acercade)
    void acercaDe(){

        Intent i = new Intent(getApplicationContext(), AcercaDeActivity.class);
        startActivity(i);
    }*/

    void guardarylanzar() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario", email.getText().toString().trim());
        editor.putString("pass", pass.getText().toString().trim());
        editor.apply();
        startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("idUsuario", idUsuario));
        finish();
    }





}


