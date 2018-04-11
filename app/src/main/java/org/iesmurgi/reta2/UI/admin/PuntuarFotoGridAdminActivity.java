package org.iesmurgi.reta2.UI.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

    StorageReference storageReference;
    @BindView(R.id.gridview_fotos)
    GridView grid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_foto_grid_admin);
        ButterKnife.bind(this);
         usuario = getIntent().getExtras().getString("USUARIO");
         partida = getIntent().getExtras().getString("PARTIDA");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        if(!nombres.isEmpty()) nombres.clear();
        if(!urisreal.isEmpty()) urisreal.clear();

        myRef.child("Imagenes").child(partida).child(usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                    nombres.add(uniqueKeySnapshot.getKey());

                    //Chat aux = uniqueKeySnapshot.child(uniqueKeySnapshot.getValue().toString()).getValue(Chat.class);
                   // uris.add(aux.getMensaje());
                    Log.e("key",""+uniqueKeySnapshot.getKey());
                }
                String [] nombresretos = nombres.toArray(new String[0]);
                startActivity(new Intent(getApplicationContext(),AdminListaRetosPuntuarFotos.class).putExtra("NOMBRESRETOS",nombresretos).putExtra("PARTIDA",partida).putExtra("USUARIO",usuario));
             /*   for (int i = 0; i < nombres.size(); i++) {


                    int finalI = i;
                    myRef.child("Imagenes").child(partida).child(usuario).child(nombres.get(i)).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            // comprobamos que dataSnapshot sea distinto de null es obligatorio esta comprobacion
                            if (dataSnapshot != null ) {
                                try {

                                    Chat msg = dataSnapshot.getValue(Chat.class);
                                    uris.add(msg.getMensaje().substring(msg.getMensaje().lastIndexOf("/")+1,msg.getMensaje().length()));
                                    if(){
                                        descargarUris();
                                    }

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

                    if(finalI == nombres.size()){
                        descargarUris();
                    }

                }


                */

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }


    void descargarUris(){

        for (int i = 0; i <uris.size() ; i++) {
            storageReference.child("Imagenes").child(partida).child(usuario).child(nombres.get(0)).child(uris.get(i)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    urisreal.add(uri);
                }
            });
        }

        grid.setAdapter(new ImageListAdapter(getApplicationContext(),urisreal));


    }

}
