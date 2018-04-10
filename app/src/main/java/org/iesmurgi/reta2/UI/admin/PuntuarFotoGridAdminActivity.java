package org.iesmurgi.reta2.UI.admin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.iesmurgi.reta2.Chat.Chat;
import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.R;

import java.util.ArrayList;

public class PuntuarFotoGridAdminActivity extends AppCompatActivity {

    private String nombrereto;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private ArrayList<String> nombres = new ArrayList<>();
    private ArrayList<String> uris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_foto_grid_admin);

        String usuario = getIntent().getExtras().getString("USUARIO");
        String partida = getIntent().getExtras().getString("PARTIDA");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child("Imagenes").child(partida).child(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                    nombres.add(uniqueKeySnapshot.getKey());
                    Chat aux = uniqueKeySnapshot.getValue(Chat.class);
                    uris.add(aux.getMensaje());
                    Log.e("key",""+uniqueKeySnapshot.getKey());
                }


                //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

               // recyclerView.setAdapter(new ChatAdapter(nombres,getApplicationContext(),6,partida));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
