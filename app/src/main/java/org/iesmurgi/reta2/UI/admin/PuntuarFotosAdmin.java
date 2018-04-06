package org.iesmurgi.reta2.UI.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.iesmurgi.reta2.R;

public class PuntuarFotosAdmin extends AppCompatActivity {

    private StorageReference mStorageRef;
    private String nombreequipo,nombrepartida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuar_fotos_admin);
        nombreequipo = "asd";
        nombrepartida = "pepe";
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Imagenes").child(nombrepartida).child(nombreequipo);



    }
}
