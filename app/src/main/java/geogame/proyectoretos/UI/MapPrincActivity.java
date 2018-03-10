package geogame.proyectoretos.UI;

import android.Manifest;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import geogame.proyectoretos.R;
import geogame.proyectoretos.UI.geofences.GeofenceTransitions;
import geogame.proyectoretos.UI.geofences.Posiciones;

public class MapPrincActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    private static final String TAG = "GEOFENCE" ;


    public GoogleMap mapa;
    private Location myLocation;
    private LocationListener mGpsListener;
    private LocationModel locationModel;
    private LocationManager gestorLoc;
    private GoogleApiClient myClient;
    private GeofencingRequest geofencingRequest;
    private boolean isMonitoring = false;
    protected ArrayList<Geofence> mGeofenceList;
    private MarkerOptions markerOptions;
    private PendingIntent pendingIntent;
    private Marker destinoactual;
    private ArrayList<Posiciones> posiciones = new ArrayList<>();
    private final LatLng Murgi = new LatLng(36.7822801, -2.815255);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_princ);

        if(posiciones.isEmpty()) {
            posiciones.add(new Posiciones(new LatLng(36.775132, -2.812932), "Ayuntamiento de El Ejido"));
            posiciones.add(new Posiciones(new LatLng(36.764014, -2.800453), "Estadio Municipal de Santo Domingo"));
            posiciones.add(new Posiciones(new LatLng(36.773189, -2.805506), "El Corte Inglés"));
        }

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

        myClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
            
        locationModel.getmLocation().observe(this, location -> {
            if (location != null && mapa != null) {

                CircleOptions co = new CircleOptions().center(new LatLng(location.getAltitude(), location.getLongitude())).radius(50).strokeColor(Color.RED).fillColor(Color.TRANSPARENT).strokeWidth(2F);
                Log.e("he entrado","he entrado"+location.getAltitude()+" "+location.getLatitude());


                mapa.addCircle(co);

            }

        });
        movida();
        populateGeofenceList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!myClient.isConnecting() || !myClient.isConnected()) {
            myClient.connect();
        }
    }



        @Override
        protected void onStop(){
            super.onStop();
            if (myClient.isConnecting() || myClient.isConnected()) {
                myClient.disconnect();
            }
        }


    private void populateGeofenceList() {

        for (int i = 0; i < posiciones.size(); i++) {



            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(posiciones.get(i).getNombre())

                    // Set the circular region of this geofence.
                    .setCircularRegion(
                            posiciones.get(i).getCoordenadas().latitude,
                            posiciones.get(i).getCoordenadas().longitude,
                            150
                    )

                    // Set the expiration duration of the geofence. This geofence gets automatically
                    // removed after this period of time.
                    .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)

                    // Create the geofence.
                    .build());
        }

    }


    public void onResult(Status status) {
        if (status.isSuccess()) {
            Toast.makeText(
                    this,
                    "Geofences Added",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.

        }
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitions.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }



    private void movida(){

        if (!myClient.isConnected()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    myClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mapa == null) {
            mapa = googleMap;
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
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

            //TODO probar que el marker cargar la foto como icono
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

        //TODO recorrer array que contendrá retos para ver cercano


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