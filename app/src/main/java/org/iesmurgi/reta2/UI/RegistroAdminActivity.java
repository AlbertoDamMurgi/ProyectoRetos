package org.iesmurgi.reta2.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.R;

public class RegistroAdminActivity extends AppCompatActivity {

    private static final String TAG = "REGISTROADMIN";
    @BindView(R.id.ed_correo_admin_registrar)
    EditText correoadmin;
    @BindView(R.id.ed_pass_equipo_admin_registrar)
    EditText passadmin;


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

    @OnClick(R.id.btn_registro_admin_registrar)
    void registrarAdmin() {


        mAuth.createUserWithEmailAndPassword(correoadmin.getText().toString(), passadmin.getText().toString())
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

                                                Toast.makeText(RegistroAdminActivity.this, "user updated", Toast.LENGTH_SHORT).show();
                                                Log.e("asdasd", "cambiado");
                                                Log.e("qweqwe", mLoginModel.getUsuario().getValue().getDisplayName());

                                            } else {
                                                Toast.makeText(RegistroAdminActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            // ...

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroAdminActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }

}
