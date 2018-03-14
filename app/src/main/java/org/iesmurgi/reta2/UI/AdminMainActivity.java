package org.iesmurgi.reta2.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;
import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.R;

public class AdminMainActivity extends AppCompatActivity {


    private LoginModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        ButterKnife.bind(this);
        mLoginModel = ViewModelProviders.of(this).get(LoginModel.class);

        escuchador();

    }

    @OnClick({R.id.btn_chat_main_registro, R.id.btn_main_admin_crear_partida, R.id.btn_main_admin_desconectarse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chat_main_registro:
                startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                break;
            case R.id.btn_main_admin_crear_partida:
                startActivity(new Intent(getApplicationContext(),CrearPartida.class));
                break;
            case R.id.btn_main_admin_desconectarse:
                desconectarAdmin();
                break;
        }
    }

    private void desconectarAdmin() {

        mLoginModel.getConection().getValue().getInstance().signOut();
        mLoginModel.getConection().postValue(null);
        mLoginModel.getUsuario().postValue(null);



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
}
