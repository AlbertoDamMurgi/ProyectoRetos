package geogame.proyectoretos.UI;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import geogame.proyectoretos.Chat.ChatActivity;
import geogame.proyectoretos.R;
import geogame.proyectoretos.UI.LoginActivity;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getLifecycle().addObserver(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void comprobarLogin(){

        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        if(currentUser==null){

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }




    }


}
