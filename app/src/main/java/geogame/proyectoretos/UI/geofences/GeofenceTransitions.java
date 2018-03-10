package geogame.proyectoretos.UI.geofences;

import android.app.IntentService;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import geogame.proyectoretos.R;
import geogame.proyectoretos.UI.LocationModel;

/**
 * Created by Farra on 10/03/2018.
 */

public class GeofenceTransitions extends IntentService {

    protected static final String TAG = "geofence-transitions";

    private LocationModel model;
    public GeofenceTransitions() {
        super(TAG);

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private boolean getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        boolean geofenceTransitionboleean = getTransitionResult(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }


        return geofenceTransitionboleean;
    }


    private boolean getTransitionResult(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Log.e("he entrado","he entrado");
                return true;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Log.e("he salido","he salido");
                return false;
            default:
                return false;

        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {

            Log.e(TAG, "Error en geofence");
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
          boolean resultado = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            // Send notification and log the transition details.


        } else {
            // Log the error.
            Log.e(TAG, "ERROR geofence");
        }
    }
}
