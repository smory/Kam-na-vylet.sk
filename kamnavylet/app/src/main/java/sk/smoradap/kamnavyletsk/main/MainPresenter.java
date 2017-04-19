package sk.smoradap.kamnavyletsk.main;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import sk.smoradap.kamnavyletsk.api.LocallyStoredAttrationProvider;
import sk.smoradap.kamnavyletsk.model.Attraction;
import sk.smoradap.kamnavyletsk.model.Item;
import sk.smoradap.kamnavyletsk.utils.GpsUtils;

/**
 * Created by psmorada on 17.10.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MainPresenter implements MainContract.Presenter, OnLocationUpdatedListener {

    private MainContract.View view;
    public static final String TAG = MainPresenter.class.getSimpleName();

    @RootContext
    Context context;

    @Override
    public void start() {
        Log.d(TAG, "Starting presenter.");
        registerForSingleLocationUpdate();
        view.showBusy(true);
    }

    @Override
    public void attactionPicked(Item item) {
        view.showAttractionDetails(item.getSourceUrl());
    }


    @Override
    public void onLocationUpdated(Location location) {
        Log.d(TAG, "Received location update: " + location);
        System.out.println("Received location update: " + location);
        LocallyStoredAttrationProvider p = LocallyStoredAttrationProvider.getInstance(context);
        List<Attraction> attractions = GpsUtils.getAtractionsInRadius(location, p.getAttractions(), 10d);
        System.out.println(attractions);
        view.showNearbyAttractions(attractions);
        view.showBusy(false);
    }


    public void registerForSingleLocationUpdate() {
        Log.d(TAG, "Registering for single location update");
        SmartLocation.with(context)
                .location()
                .oneFix()
                .start(this);
    }

    @Override
    public void searchReqested(String query) {
        view.performSearch(query);
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }
}
