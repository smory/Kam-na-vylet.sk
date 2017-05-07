package sk.smoradap.kamnavyletsk.details;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.DetailsProvider;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.NearbyAttraction;

/**
 * Created by psmorada on 13.10.2016.
 */
@EBean(scope = EBean.Scope.Default)
public class DetailsPresenter implements  DetailsContract.Presenter, KamNaVyletApi.OnDetailsListener {

    @RootContext
    Context context;

    private DetailsContract.View view;
    private AttractionDetails mAttractionDetails;
    KamNaVyletApi api = new KamNaVyletApi();
    List<AttractionDetails> mNearbyAttractions = new LinkedList<>();

    @Override
    public void start(String url) {
        if(mAttractionDetails == null){
            view.showBusy(true, context.getString(R.string.loading_data));
            api.loadDetails(url, this);
        } else {
            setUpView(mAttractionDetails);
        }

    }

    @Override
    public void setView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void start(AttractionDetails details){
        mAttractionDetails = details;
        setUpView(details);
    }

    @Override
    public void nearByAttractionPicked(AttractionDetails attraction) {
        view.showNearbyAttractionDetails(attraction);
    }

    @Override
    public void previewImagePicked(int imageIndex) {
        view.showFullImagePreviews(mAttractionDetails.getImageUrls(), imageIndex);
    }

    @Override
    public void onDetailsLoaded(AttractionDetails details) {
        mAttractionDetails = details;
        view.setAttactionDetails(details);
        setUpView(details);
    }

    void setUpView(AttractionDetails details){
        view.showBusy(true, context.getString(R.string.loading_data));
        view.setTitle(details.getName(), details.getTown(), details.getImageUrls().get(0));
        view.setImagePreviews(details.getImageUrls());
        view.setCategory(details.getCategory());
        view.setDetailsDescription(details.getDescription());
        view.setDetails(details.getDetailsMap());

        if(mNearbyAttractions.isEmpty()){
            loadNearbyAttractionDetails(details.getNearbyAttractions());
        } else {
            view.setNearByAttractions(mNearbyAttractions);
        }

        view.showBusy(false, null);
    }

    private void loadNearbyAttractionDetails(final List<NearbyAttraction> list)  {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(NearbyAttraction na : list){
                    try {
                        AttractionDetails ad = DetailsProvider.details(na.getUrl());
                        mNearbyAttractions.add(ad);
                    } catch (IOException e) {
                        Log.w(this.getClass().getSimpleName(), e);
                    }
                }
                view.setNearByAttractions(mNearbyAttractions);
            }
        };
        Thread t = new Thread(r);
        t.start();

    }


    @Override
    public void onDetailsFailure(IOException e) {
        view.showCannotLoadData();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
