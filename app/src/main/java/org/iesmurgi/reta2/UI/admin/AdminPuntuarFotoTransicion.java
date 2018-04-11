package org.iesmurgi.reta2.UI.admin;

import android.content.Context;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.iesmurgi.reta2.Chat.Chat;
import org.iesmurgi.reta2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminPuntuarFotoTransicion extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private String partida,usuario,nombrereto;
    private ArrayList<String> urisString = new ArrayList<>();
    StorageReference storageReference;
    @BindView(R.id.imagen_descargada_admin)
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_puntuar_foto_transicion);
        ButterKnife.bind(this);
        usuario = getIntent().getExtras().getString("USUARIO");
        partida = getIntent().getExtras().getString("PARTIDA");
        nombrereto = getIntent().getExtras().getString("NOMBRERETO");
        storageReference = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        myRef.child("Imagenes").child(partida).child(usuario).child(nombrereto).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Chat aux = data.getValue(Chat.class);
                    urisString.add(aux.getMensaje().substring(aux.getMensaje().lastIndexOf("/")+1,aux.getMensaje().length()));

                }
                StorageReference reference = storageReference.child("Imagenes").child(partida).child(usuario).child(nombrereto).child(urisString.get(0));
                Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(reference).into(imagen);

            }


          //StorageReference reference = storageReference.child("Imagenes").child(partida).child(usuario).child(nombrereto).child(urisString.get(0));



            //Glide.with(this).using(new FirebaseImageLoader).load(reference).into(imagen);

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

   public void ponerImagen(){




    }
}
