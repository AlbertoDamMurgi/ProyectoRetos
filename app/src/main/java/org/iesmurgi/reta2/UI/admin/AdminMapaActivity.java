package org.iesmurgi.reta2.UI.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.iesmurgi.reta2.Chat.Chat;
import org.iesmurgi.reta2.Chat.ChatAdminActivity;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.geofences.Posiciones;
import org.iesmurgi.reta2.UI.retos.Localizacion;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminMapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1 ;
    private GoogleMap mMap;
    private String partida;
    private final LatLng Murgi = new LatLng(36.7822801, -2.815255);
    FirebaseDatabase database;
    DatabaseReference myRef,mRefAux;
    private ArrayList<MarkerOptions> marcadores = new ArrayList<>();
    private ArrayList<String> participantes = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mapa);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        partida = getIntent().getExtras().getString("PARTIDA");
        Log.e("PArtida",partida);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
        }

      escucharUsuarios();

    }

    @OnClick(R.id.btn_mapa_admin_chat)
    void abrirChat(){
        startActivity(new Intent(getApplicationContext(),ChatAdminActivity.class).putExtra("SALA",partida));
    }

    void pintarMapa(){

        mMap.clear();

        for (int i = 0; i < marcadores.size() ; i++) {
            mMap.addMarker(marcadores.get(i));
        }

    }

    void comprobarMarcador(String participante){

        for (int i = 0; i <marcadores.size() ; i++) {

            if(marcadores.get(i).getTitle().equalsIgnoreCase(participante)){

                marcadores.remove(i);

            }

        }

    }

    void actualizarPosiciones(String participante){


            mRefAux = database.getReference("Localizaciones").child(partida).child(participante);
           // Log.e("participan",participantes.get(i));

            mRefAux.orderByKey().limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot!=null) {
                        try {
                            Log.e("data",dataSnapshot.toString());
                            PruebaLoc loc =  dataSnapshot.getValue(PruebaLoc.class);

                            comprobarMarcador(participante);


                            marcadores.add(new MarkerOptions().icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.hojapequenia)).title(participante).position(new LatLng(Double.parseDouble(loc.getLatitud()),Double.parseDouble(loc.getLongitud()))));

                            pintarMapa();
                        }catch (Exception ex){
                            Log.e("error",ex.getMessage());
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



    void escucharUsuarios(){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Localizaciones").child(partida);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot!=null) {
                    try {

                        String participante = dataSnapshot.getKey();
                        Log.e("participante",participante);
                       // participantes.add(participante);
                        marcadores.add(new MarkerOptions().position(Murgi).title(participante));
                       // Log.e("size",""+participantes.size());
                        actualizarPosiciones(participante);
                    }catch (Exception ex){
                        Log.e("ERROR",ex.getMessage());
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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap == null) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }

        float zoom = 14f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Murgi, zoom);
        mMap.animateCamera(cameraUpdate);


    }




}
