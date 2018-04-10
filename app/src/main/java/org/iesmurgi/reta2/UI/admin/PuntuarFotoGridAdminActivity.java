package org.iesmurgi.reta2.UI.admin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.ScrollView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ArrayList<String> nombres = new ArrayList<>();
    private ArrayList<String> uris = new ArrayList<>();
    private ArrayList<String> nombreparamostrar = new ArrayList<>();

    @BindView(R.id.gridview_fotos)
    GridView grid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_foto_grid_admin);
        ButterKnife.bind(this);
        String usuario = getIntent().getExtras().getString("USUARIO");
        String partida = getIntent().getExtras().getString("PARTIDA");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        if(!nombres.isEmpty()) nombres.clear();

        myRef.child("Imagenes").child(partida).child(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                    nombres.add(uniqueKeySnapshot.getKey());

                    //Chat aux = uniqueKeySnapshot.child(uniqueKeySnapshot.getValue().toString()).getValue(Chat.class);
                   // uris.add(aux.getMensaje());
                    Log.e("key",""+uniqueKeySnapshot.getKey());
                }

                for (int i = 0; i < nombres.size(); i++) {



                    myRef.child("Imagenes").child(partida).child(usuario).child(nombres.get(i)).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            // comprobamos que dataSnapshot sea distinto de null es obligatorio esta comprobacion
                            if (dataSnapshot != null ) {
                                try {

                                    Chat msg = dataSnapshot.getValue(Chat.class);
                                    uris.add(msg.getMensaje());


                                } catch (Exception ex) {
                                    Log.e("Error", ex.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }




}
