package sk.smoradap.kamnavyletsk.main;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import io.nlopez.smartlocation.OnLocationUpdatedListener;

/**
 * Created by psmorada on 17.10.2016.
 */
public class MainPresenter implements MainContract.Presenter, OnLocationUpdatedListener {

    private MainContract.View mView;
    public static final String TAG = "sk.smoradap.kamnavylet";
    private SharedPreferences prefs;

    public MainPresenter(MainContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "Starting presenter.");
        mView.registerForSingleLocationUpdate(this);
    }

    @Override
    public void attactionPicked() {

    }


    @Override
    public void onLocationUpdated(Location location) {
        Log.d(TAG, "Received location update: " + location);
        System.out.println("Received location update: " + location);
        System.out.println("Loc:" + location.getProvider());
        System.out.println("Loc:" + location.getLatitude());
        System.out.println("Loc:" + location.getLongitude());
    }
}
