/*

Reta2  Copyright (C) 2018  Alberto Fernández Fernández, Santiago Álvarez Fernández, Joaquín Pérez Rodríguez

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see http://www.gnu.org/licenses/.


Contact us:

fernandez.fernandez.angel@gmail.com
santiago.alvarez.dam@gmail.com
perezrodriguezjoaquin0@gmail.com

*/

package org.iesmurgi.reta2.UI.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.iesmurgi.reta2.Chat.ChatAdapter;
import org.iesmurgi.reta2.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Actividad que muestra una lista con los retos de los equipos que tienen alguna foto por puntuar.
 * @author Alberto Fernández
 * @author Santiago Álvarez
 * @author Joaquín Pérez
 */
public class PuntuarFotosAdmin extends AppCompatActivity {

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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        String partida = getIntent().getExtras().getString("PARTIDA");
        int idpartida = getIntent().getExtras().getInt("IDPARTIDA");


        myRef.child("Imagenes").child(partida).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                    nombres.add(uniqueKeySnapshot.getKey());
                    Log.e("key",""+uniqueKeySnapshot.getKey());
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                recyclerView.setAdapter(new ChatAdapter(nombres,getApplicationContext(),6,partida,idpartida,getIntent().getStringExtra("codigoqr")));

                if (nombres.isEmpty()){
                    Toast.makeText(getApplicationContext(), "No hay fotos para puntuar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    }

