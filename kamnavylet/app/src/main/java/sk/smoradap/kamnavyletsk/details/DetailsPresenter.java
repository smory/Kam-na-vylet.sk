package sk.smoradap.kamnavyletsk.details;

import java.io.IOException;

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

    public DetailsPresenter(DetailsContract.View view){
        mView = view;
    }

    @Override
    public void start(String url) {
        if(mAttractionDetails == null){
            mView.setBusy(true);
            api.loadDetails(url, this);
        } else {
            onDetailsLoaded(mAttractionDetails);
        }

    }

    @Override
    public void nearByAttractionPicked(NearbyAttraction attraction) {
        mView.showNearbyAttractionDetails(attraction);
    }

    @Override
    public void previewImagePicked(int imageIndex) {
        mView.showFullImagePreviews(mAttractionDetails.getImageUrls(), imageIndex);
    }

    @Override
    public void onDetailsLoaded(AttractionDetails details) {
        mAttractionDetails = details;
        mView.setBusy(true);
        mView.setDescription(details.getName(), details.getTown(), details.getImageUrls().get(0));
        mView.setImagePreviews(details.getImageUrls());
        mView.setCategory(details.getCategory());
        mView.setDetailsDescription(details.getDescription());
        mView.setDetails(details.getDetailsMap());
        mView.setNearByAttractions(details.getNearbyAttractions());
        mView.setBusy(false);
    }


    @Override
    public void onDetailsFailure(IOException e) {
        mView.showCannotLoadData();
    }
}
