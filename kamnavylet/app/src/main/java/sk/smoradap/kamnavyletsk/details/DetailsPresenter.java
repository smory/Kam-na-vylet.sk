package sk.smoradap.kamnavyletsk.details;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import sk.smoradap.kamnavyletsk.api.DetailsProvider;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.NearbyAttraction;

/**
 * Created by psmorada on 13.10.2016.
 */
public class DetailsPresenter implements  DetailsContract.Presenter, KamNaVyletApi.OnDetailsListener {

    private DetailsContract.View mView;
    private AttractionDetails mAttractionDetails;
    KamNaVyletApi api = new KamNaVyletApi();
    List<AttractionDetails> mNearbyAttractions = new LinkedList<>();

    public DetailsPresenter(DetailsContract.View view){
        mView = view;
    }

    @Override
    public void start(String url) {
        if(mAttractionDetails == null){
            mView.setBusy(true);
            api.loadDetails(url, this);
        } else {
            setUpView(mAttractionDetails);
        }

    }

    @Override
    public void nearByAttractionPicked(AttractionDetails attraction) {
        mView.showNearbyAttractionDetails(attraction);
    }

    @Override
    public void previewImagePicked(int imageIndex) {
        mView.showFullImagePreviews(mAttractionDetails.getImageUrls(), imageIndex);
    }

    @Override
    public void onDetailsLoaded(AttractionDetails details) {
        mAttractionDetails = details;
        setUpView(details);
    }

    void setUpView(AttractionDetails details){
        mView.setBusy(true);
        mView.setTitle(details.getName(), details.getTown(), details.getImageUrls().get(0));
        mView.setImagePreviews(details.getImageUrls());
        mView.setCategory(details.getCategory());
        mView.setDetailsDescription(details.getDescription());
        mView.setDetails(details.getDetailsMap());

        if(mNearbyAttractions.isEmpty()){
            loadNearbyAttractionDetails(details.getNearbyAttractions());
        } else {
            mView.setNearByAttractions(mNearbyAttractions);
        }

        mView.setBusy(false);
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
                        e.printStackTrace();
                    }
                }
                mView.setNearByAttractions(mNearbyAttractions);
            }
        };
        Thread t = new Thread(r);
        t.start();

    }


    @Override
    public void onDetailsFailure(IOException e) {
        mView.showCannotLoadData();
    }
}
