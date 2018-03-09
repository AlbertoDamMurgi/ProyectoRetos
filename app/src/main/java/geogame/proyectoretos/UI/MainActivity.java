package geogame.proyectoretos.UI;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geogame.proyectoretos.Chat.ChatActivity;
import geogame.proyectoretos.R;
import geogame.proyectoretos.papelera.LoginModel;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    @BindView(R.id.pruebaemail)
    TextView pruebaemail;

    @BindView(R.id.textView3)
    TextView textView3;

    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        escuchador();


        String email = mLoginModel.getConection().getValue().getInstance().getCurrentUser().getEmail();


        pruebaemail.setText(email);


    }


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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void comprobarLogin() {


    }


    @OnClick(R.id.btn_logout)
    public void desconectarse() {

        mLoginModel.getConection().getValue().getInstance().signOut();
        mLoginModel.getConection().postValue(null);
        mLoginModel.getUsuario().postValue(null);

    }

    @OnClick(R.id.btn_chat)
    public void iralChat() {

        startActivity(new Intent(getApplicationContext(), ChatActivity.class));

    }
}



