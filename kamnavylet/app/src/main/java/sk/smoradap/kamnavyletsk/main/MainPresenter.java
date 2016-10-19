package sk.smoradap.kamnavyletsk.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import sk.smoradap.kamnavyletsk.api.LocallyStoredAttrationProvider;
import sk.smoradap.kamnavyletsk.model.Attraction;
import sk.smoradap.kamnavyletsk.utils.GpsUtils;

/**
 * Created by psmorada on 17.10.2016.
 */
public class MainPresenter implements MainContract.Presenter, OnLocationUpdatedListener {

    private MainContract.View mView;
    public static final String TAG = "sk.smoradap.kamnavylet";
    private SharedPreferences prefs;
    private Context mContext;

    public MainPresenter(MainContract.View view, Context context){
        mView = view;
        mContext = context;
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
        LocallyStoredAttrationProvider p = LocallyStoredAttrationProvider.getInstance(mContext);
        List<Attraction> attractions = GpsUtils.getAtractionsInRadius(location, p.getAttractions(), 10d);
        System.out.println(attractions);

    }
}
