package org.iesmurgi.reta2.UI.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminListaRetosPuntuarFotos extends AppCompatActivity {
    private String [] nombresretos;
    private String partida,usuario;
    @BindView(R.id.recicler_chat_admin)
    RecyclerView recicler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas_admin);
        ButterKnife.bind(this);
        nombresretos = getIntent().getExtras().getStringArray("NOMBRESRETOS");
        partida = getIntent().getExtras().getString("PARTIDA");
        usuario = getIntent().getExtras().getString("USUARIO");
        recicler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recicler.setAdapter(new ChatAdapter(getApplicationContext(),7,nombresretos,partida,usuario));

    }
}
