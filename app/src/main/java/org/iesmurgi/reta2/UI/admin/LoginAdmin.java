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
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.usuario.AcercaDeActivity;
import org.iesmurgi.reta2.UI.usuario.LoginModel;
import org.iesmurgi.reta2.UI.usuario.RegistroActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



    private void observador() {

        mLoginModel.getConection().observe(this, new Observer<FirebaseAuth>() {
            @Override
            public void onChanged(@Nullable FirebaseAuth firebaseAuth) {
                if (firebaseAuth != null) {
                    if (firebaseAuth.getCurrentUser().getDisplayName().equalsIgnoreCase("administrador")) {

                        startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));


                    } else {
                        Toast.makeText(LoginAdmin.this, "Credenciales erroneas.", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
            }
        });

    }


    @OnClick(R.id.bt_register_admin)
    void stratRetistro() {
        startActivity(new Intent(getApplicationContext(), RegistroAdminActivity.class));
    }


    @OnClick(R.id.btn_conectarse_admin)
    void conectarUsuario() {
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

    }

    void obtenerIDyLanzar(){


        //Sacamos la id de usaario
        final String URL = "http://geogame.ml/api/obtener_admin.php?correo="+email.getText().toString().trim()+"&passwd="+email.getText().toString().trim();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject o = response.getJSONObject(i);
                        idAdmin=o.getInt("idAdmin");

                    } catch (JSONException e) {
                        Log.e("Log Json error Partida", e.getMessage());
                    }
                }
                startActivity(new Intent(getApplicationContext(), AdminMainActivity.class).putExtra("idAdmin",idAdmin));

                finish();
                //endgfor
                Log.e("ADMIN RESPONDE ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Partida", error.getMessage());
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }


}



