package org.iesmurgi.reta2.Chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.iesmurgi.reta2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Clase que muestra la lista de equipos con las que el administrador de la partida puede hablar.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 * @see ChatAdapter
 * @see Chat
 * @see ChatActivity
 */
public class ChatAdminActivity extends AppCompatActivity {

    @BindView(R.id.recicler_chat_admin)
    RecyclerView recyclerView;


    private ArrayList<String> nombres = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_admin);
        ButterKnife.bind(this);
        String sala = getIntent().getExtras().getString("SALA");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child("chat").child(sala).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                   nombres.add(uniqueKeySnapshot.getKey());
                   Log.e("key",""+uniqueKeySnapshot.getKey());
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(new ChatAdapter(nombres,getApplicationContext(),3,sala));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
}
