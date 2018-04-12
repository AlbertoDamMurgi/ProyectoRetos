package org.iesmurgi.reta2.UI.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.iesmurgi.reta2.Chat.Chat;
import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PuntuarFotoGridAdminActivity extends AppCompatActivity {

    private String nombrereto;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private String partida,usuario;
    private ArrayList<String> nombres = new ArrayList<>();
    private ArrayList<String> uris = new ArrayList<>();
    private ArrayList<Uri> urisreal = new ArrayList<>();
    private int idpartida;
    StorageReference storageReference;

    @BindView(R.id.recicler_chat_admin)
     RecyclerView recicler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas_admin);
        ButterKnife.bind(this);
         usuario = getIntent().getExtras().getString("USUARIO");
         partida = getIntent().getExtras().getString("PARTIDA");
         idpartida = getIntent().getExtras().getInt("IDPARTIDA");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        if(!nombres.isEmpty()) nombres.clear();
        if(!urisreal.isEmpty()) urisreal.clear();

        myRef.child("Imagenes").child(partida).child(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                    nombres.add(uniqueKeySnapshot.getKey());
                    Log.e("key",""+uniqueKeySnapshot.getKey());
                }

                recicler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recicler.setAdapter(new ChatAdapter(getApplicationContext(),7,nombres,partida,usuario,idpartida));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }



}
