package geogame.proyectoretos.UI;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

/**
 * Created by Farra on 10/03/2018.
 */

public class LocationModel extends ViewModel{

    private MutableLiveData<Location> mLocation = new MutableLiveData<>();



    public MutableLiveData<Location> getmLocation() {
        return mLocation;
    }

    public void setmLocation(Location location) {
        this.mLocation.setValue(location);
    }
}
