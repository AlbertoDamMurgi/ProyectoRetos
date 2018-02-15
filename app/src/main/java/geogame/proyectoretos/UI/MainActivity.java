package geogame.proyectoretos.UI;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import geogame.proyectoretos.R;
import geogame.proyectoretos.UI.LoginActivity;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();



    }




    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void comprobarUsuarioLogeado(){






    }

}
