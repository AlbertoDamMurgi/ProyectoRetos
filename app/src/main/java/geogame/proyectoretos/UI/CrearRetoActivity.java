package geogame.proyectoretos.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import geogame.proyectoretos.R;

public class CrearRetoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private double latitud;
    private double longitud;
    private LatLng latLng;
    GoogleApiClient mClient;
    public static final String TAG = CrearRetoActivity.class.getSimpleName();
    private static final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reto);



        mClient = new GoogleApiClient.Builder(this)
                //lo que nos devuelve google
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //añadimos las apis que nos interesan
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                //donde se va a pintar la actividad
                .enableAutoManage(this, this)
                .build();


        ImageButton btnMapa = findViewById(R.id.btn_crearReto_mapaLugares);
        btnMapa.setOnClickListener(view -> runApiPlaces(view));
    }


    public void runApiPlaces(View view){
        try {
            // Start a new Activity for the Place Picker API, this will trigger {@code #onActivityResult}
            // when a place is selected or with the user cancels.
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }

    }
    //este metodo se lanza cuando el metodo RunApiplaces termina
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {

            Place place = PlacePicker.getPlace(this, data);

            if (place == null) {
                Log.i(TAG, "ningun lugar seleccionado");
                return;
            }
            Log.v("lat1", String.valueOf(latitud));
            Log.v("long1", String.valueOf(longitud));
            latLng = place.getLatLng();
            latitud = latLng.latitude;
            longitud = latLng.longitude;
            Log.v("lat2", String.valueOf(latitud));
            Log.v("long2", String.valueOf(longitud));

            Toast.makeText(getApplicationContext(),"Ubicacion del reto seleccionada!!",Toast.LENGTH_LONG).show();
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
