package org.iesmurgi.reta2.UI.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdministrarPartidaAdminActivity extends AppCompatActivity {


    private String sala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_partida_admin);
        ButterKnife.bind(this);

        sala = getIntent().getExtras().getString("PARTIDA");

        Log.e("partida",""+sala);

    }


    @OnClick(R.id.btn_admin_chat)
        void abrirChat(){
            startActivity(new Intent(getApplicationContext(), ChatActivity.class).putExtra("SALA",sala));
        }

}
