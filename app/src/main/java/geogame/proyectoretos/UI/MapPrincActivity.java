package geogame.proyectoretos.UI;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import geogame.proyectoretos.R;

public class MapPrincActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 1;


    public GoogleMap mapa;
    private Location myLocation;
    private LocationListener mGpsListener;
    private LocationModel locationModel;
    private LocationManager gestorLoc;
    private GoogleApiClient myClient;
    private final LatLng Murgi = new LatLng(36.7822801, -2.815255);

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

        locationModel = ViewModelProviders.of(this).get(LocationModel.class);
        mGpsListener = new MyLocationListener(locationModel);


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