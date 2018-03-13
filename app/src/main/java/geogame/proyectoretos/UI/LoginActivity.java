package geogame.proyectoretos.UI;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import geogame.proyectoretos.R;

public class LoginActivity extends AppCompatActivity implements LifecycleObserver {


    private static final String TAG = "LOGIN";
    @BindView(R.id.et_email)
    EditText email;
    @BindView(R.id.et_pass)
    EditText pass;




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

       observador();

    }

    private void observador() {

        mLoginModel.getConection().observe(this, new Observer<FirebaseAuth>() {
            @Override
            public void onChanged(@Nullable FirebaseAuth firebaseAuth) {
                if(firebaseAuth!=null&&!firebaseAuth.getCurrentUser().getDisplayName().equalsIgnoreCase("administrador")){

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else{
                    //startActivity(new Intent(getApplicationContext(),LoginAdmin.class));
                    Toast.makeText(LoginActivity.this, "Credenciales erroneas.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





    @OnClick(R.id.bt_register)
    void stratRetistro(){
        startActivity(new Intent(getApplicationContext(),RegistroActivity.class));
    }


/*
    @OnClick(R.id.btn_registrarse)
    void registrarAdmin() {



        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
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
    boolean loginAdminSecreto(){

        startActivity(new Intent(getApplicationContext(),LoginAdmin.class));

        return true;
    }


    @OnClick(R.id.btn_conectarse)
    void conectarUsuario(){


        if (!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()) {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
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
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        }else {
            Toast.makeText(getApplicationContext(), "Porfavor rellena todos los campos.", Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick(R.id.btn_login_acercaDe)
    void acercaDe(){

        Intent i = new Intent(getApplicationContext(), AcercaDeActivity.class);
        startActivity(i);
    }



}


