package org.iesmurgi.reta2.UI.retos;

import android.Manifest;

import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import org.iesmurgi.reta2.Chat.ChatActivity;
import org.iesmurgi.reta2.Data.BasedeDatosApp;
import org.iesmurgi.reta2.Data.entidades.Retos;
import org.iesmurgi.reta2.R;
import org.iesmurgi.reta2.UI.geofences.GeofenceTransiciones;
import org.iesmurgi.reta2.UI.usuario.FinPartidaActivity;

import butterknife.OnClick;

public class MapPrincActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    private static final String TAG = "GEOFENCE";



    public GoogleMap mapa;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private String usuario;
    static final int RETO_FINALIZADO = 1;
    private LocationListener mGpsListener;
    private LocationModel locationModel;
    private LocationManager gestorLoc;
    private GoogleApiClient myClient;
    float[] results = {100};
    private GeofencingRequest geofencingRequest;
    private boolean puedespinchar = false;
    private boolean isMonitoring = false;
    protected ArrayList<Geofence> mGeofenceList;
    private MarkerOptions markerOptions;
    private PendingIntent pendingIntent;
    private GeofencingClient mGeofencingClient;
    private CircleOptions co = new CircleOptions();
    private Marker destinoactual;
    private int idpartida;
    private final LatLng Murgi = new LatLng(36.7822801, -2.815255);
    private FirebaseAuth mAuth;

    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 1;

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bindLocationListener();
        } else {
            Toast.makeText(this, "This sample requires Location access", Toast.LENGTH_LONG).show();
        }
    }


    private void bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, mGpsListener, getApplicationContext());
    }


    class RecuperarRetos extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... p) {
            retos =  BasedeDatosApp.getAppDatabase(getApplicationContext()).retosDao().getRetosPartida(p[0]);
            locationModel.setRetos(retos);
            locationModel.setCargados(true);
            return 0;
        }
    }

    class NombrePartida extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... p) {
            nombrepartida =  BasedeDatosApp.getAppDatabase(getApplicationContext()).partidasDao().getPartidaActual(p[0]);

            return 0;
        }
    }




    private List<Retos> retos = new ArrayList<>();



    private String nombrepartida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_princ);
        idpartida= getIntent().getExtras().getInt("IDPARTIDA");
        locationModel = ViewModelProviders.of(this).get(LocationModel.class);


        new NombrePartida().execute(idpartida);
        if (!locationModel.isCargados()) {
            new RecuperarRetos().execute(idpartida);
        }else{
            retos=locationModel.getRetos();
        }


       Button mChat = findViewById(R.id.btn_mapa_chat);
        mChat.setOnClickListener(v -> {
            Log.e("chat","deberia abrir el chat"+nombrepartida);
            startActivity(new Intent(getApplicationContext(),ChatActivity.class).putExtra("SALA",nombrepartida));
        });



        locationModel = ViewModelProviders.of(this).get(LocationModel.class);


        mGpsListener = new MyLocationListener(locationModel);
        mGeofenceList = new ArrayList<Geofence>();






        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
        } else {
            bindLocationListener();
        }


        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        mGeofencingClient = LocationServices.getGeofencingClient(this);

        buildGoogleApiClient();
        if(myClient==null) {

            myClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, this)

                    .build();
        }

        crearmarcadores();


        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser().getDisplayName();
        if(usuario == null){
            usuario = locationModel.getUsuario();
        }else{
            locationModel.setUsuario(usuario);
        }

        if(nombrepartida==null){
            nombrepartida=locationModel.getPartida();

        }else{
            locationModel.setPartida(nombrepartida);
        }

        myRef = database.getReference("Localizaciones").child(nombrepartida).child(usuario);


        locationModel.getmLocation().observe(this, location -> {
            if (location != null && mapa != null) {

                puedespinchar=false;
                Log.e("no puedes pinchar","no puedes pinchar"+puedespinchar);
                Log.e("he entrado", "he entrado" + location.getLatitude() + " " + location.getLongitude());

                LatLng lat = new LatLng(location.getLatitude(),location.getLongitude());

                myRef.push().setValue(lat);



                for(int i=0;i<retos.size(); i++){
                    Location.distanceBetween(location.getLatitude(),location.getLongitude(),retos.get(i).getLocalizacionLatitud(),retos.get(i).getLocalizacionLongitud(),results);
                    if(results[0]<20){
                        if(i==locationModel.getNumReto()) {
                            puedespinchar = true;

                            Log.e("puedes pinchar", "puedes pinchar" + puedespinchar);

                        }
                    }
                }

            }

        });

    }





    private void buildGoogleApiClient() {
    }

    //---------------------------------------------------geofences-----------------------------------------------------------------------------
    @Override
    protected void onStart() {
        myClient.connect();
        //startGeofences();
        super.onStart();




    }

    @Override
    protected void onStop() {
        myClient.disconnect();
        super.onStop();

    }

    private static final String NOTIFICATION_MSG = "NOTIFICATION MSG";
    // Create a Intent send by the notification

    public static Intent makeNotificationIntent(Context context, String msg) {
        Intent intent = new Intent( context, MapPrincActivity.class );
        intent.putExtra( NOTIFICATION_MSG, msg );
        return intent;
    }


    // Start Geofence creation process
    private void startGeofences() {
        Log.i(TAG, "startGeofence()");
        for (int i = 0; i < retos.size(); i++) {
            Geofence geofence = createGeofence(new LatLng(retos.get(i).getLocalizacionLatitud(),retos.get(i).getLocalizacionLongitud()), 150, retos.get(i).getNombre());
            GeofencingRequest geofenceRequest = createGeofenceRequest(geofence);
            addGeofence(geofenceRequest);
        }

    }

    private static final long GEO_DURATION = 60 * 60 * 1000;

    // Create a Geofence
    private Geofence createGeofence(LatLng latLng, float radius, String id) {
        Log.d(TAG, "createGeofence");
        return new Geofence.Builder()
                .setRequestId(id)
                .setCircularRegion(latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }


    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest(Geofence geofence) {
        Log.d(TAG, "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;

    private PendingIntent createGeofencePendingIntent() {
        Log.d(TAG, "createGeofencePendingIntent");
        if (geoFencePendingIntent != null)
            return geoFencePendingIntent;

        Intent intent = new Intent(this, GeofenceTransiciones.class);
        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        Log.d(TAG, "addGeofence");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.GeofencingApi.addGeofences(
                myClient,
                request,
                createGeofencePendingIntent()
        ).setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull Status status) {

        Log.e(TAG, "onResult: " + status);
        if ( status.isSuccess() ) {
            Toast.makeText(this, "yeeah", Toast.LENGTH_SHORT).show();

        } else {
            // inform about fail
        }


    }

    // Clear Geofence
    private void clearGeofence() {
        Log.d(TAG, "clearGeofence()");
        LocationServices.GeofencingApi.removeGeofences(
                myClient,
                createGeofencePendingIntent()
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if ( status.isSuccess() ) {
                    // remove drawing
                   Log.e("removidas","removidasGeofences");
                }
            }
        });
    }




//-----------------------------------------------------------------------------------------------------------------------------------------------




    //-----------------------------------------------------------mapa----------------------------------------------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("Numero reto",locationModel.getNumReto()+"");
        if (mapa == null) {
            mapa = googleMap;
            mapa.addMarker(marcadores.get(locationModel.getNumReto()));
        }else{
            mapa.addMarker(marcadores.get(locationModel.getNumReto()));
        }








        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        mapa.setMyLocationEnabled(true);
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mapa.addMarker(new MarkerOptions()
                .position(Murgi)
                .title("IES Murgi")
                .snippet("Instituto de Educación Secundaria Murgi")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.hojapequenia))

                .anchor(0.5f, 0.5f));


        float zoom = 14f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Murgi, zoom);
        mapa.animateCamera(cameraUpdate);


            mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if(puedespinchar) {
                    for (int i = 0; i < marcadores.size() ; i++) {

                        if(marcadores.get(i).getTitle().equalsIgnoreCase(marker.getTitle())){
                                int [] aux;
                                aux = new int[]{idpartida,retos.get(i).getIdReto()};
                              startActivityForResult(new Intent(getApplicationContext(),RetoActivity.class).putExtra("PARTIDAYRETO",aux),RETO_FINALIZADO);
                        }
                        }
                    }
                    return false;
                }
            });



    }

    private ArrayList<MarkerOptions> marcadores = new ArrayList<>();

    private void crearmarcadores() {

        for (int i = 0; i < retos.size() ; i++) {
            marcadores.add(new MarkerOptions().position(new LatLng(retos.get(i).getLocalizacionLatitud(),retos.get(i).getLocalizacionLongitud()))
                    .title(retos.get(i).getNombre())

                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.hojapequenia))
                    .anchor(0.5f, 0.5f));

        }



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RETO_FINALIZADO) {

            if (resultCode == RESULT_OK) {
                locationModel.setNumReto(locationModel.getNumReto()+1);
                if (locationModel.getNumReto() < retos.size()) {

                    mapa.clear();
                    onMapReady(mapa);

                }else{

                    // finalizar juego
                    startActivity(new Intent(getApplicationContext(),FinPartidaActivity.class));
                    Log.e("FINISH","FINISH");
                    finish();

                }

            }


        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

     class MyLocationListener implements LocationListener {

       private LocationModel model;

         public MyLocationListener(LocationModel locationModel) {
             model = locationModel;
         }

         @Override
        public void onLocationChanged(Location location) {
            Log.d("onLocationChanged","Changed 1");
                if(location!=null&&model.getmLocation()!=null) {model.setmLocation(location);}
            Log.d("onLocationChanged","Changed 2");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }



/*








    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}

     class MyLocationListener implements LocationListener {

       private LocationModel model;

         public MyLocationListener(LocationModel locationModel) {
             model = locationModel;
         }

         @Override
        public void onLocationChanged(Location location) {
            Log.d("onLocationChanged","Changed 1");
                if(location!=null&&model.getmLocation()!=null) {model.setmLocation(location);}
            Log.d("onLocationChanged","Changed 2");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }



/*








    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mapa == null) {
            mapa = googleMap;

            // probar que el marker cargar la foto como icono
            mapa.setMyLocationEnabled(true);
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mapa.addMarker(new MarkerOptions()
                    .position(Murgi)
                    .title("IES Murgi")
                    .snippet("Instituto de Educación Secundaria Murgi")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.logohoja))
                    .anchor(0.5f, 0.5f));
        }


        if(myLocation==null) {

            gestorLoc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,1,this);
            myLocation=gestorLoc.getLastKnownLocation(proveedor);

        }
        gestorLoc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,1,this);

        myLocation=gestorLoc.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        myPos=new LatLng(myLocation.getLatitude(),myLocation.getLongitude());




        CircleOptions co = new CircleOptions().center(myPos).radius(50).strokeColor(Color.RED).fillColor(Color.TRANSPARENT);

        mapa.addCircle(co);

        // recorrer array que contendrá retos para ver cercano


        for(int i=0;i<posiciones.size(); i++){
            Location.distanceBetween(myLocation.getLatitude(),myLocation.getLongitude(),posiciones.get(i).getCoordenadas().latitude,posiciones.get(i).getCoordenadas().longitude,results);
            if(results[0]<150){
                mapa.addMarker(new MarkerOptions().position(posiciones.get(i).getCoordenadas()).title(posiciones.get(i).getNombre()));
            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
                mapa.setMyLocationEnabled(true);
                mapa.getUiSettings().setZoomControlsEnabled(false);
                mapa.getUiSettings().setCompassEnabled(true);
                }
                }*/