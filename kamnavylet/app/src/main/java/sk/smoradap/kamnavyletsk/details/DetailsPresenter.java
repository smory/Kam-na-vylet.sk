package sk.smoradap.kamnavyletsk.details;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.AttractionProvider;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.api.model.Attraction;
import sk.smoradap.kamnavyletsk.api.model.BaseAttractionInfo;
import sk.smoradap.kamnavyletsk.api.model.DetailType;
import sk.smoradap.kamnavyletsk.api.model.NearbyAttraction;
import sk.smoradap.kamnavyletsk.base.KamNaVyletApiBean;

/**
 * Created by psmorada on 13.10.2016.
 */
@EBean(scope = EBean.Scope.Default)
public class DetailsPresenter implements  DetailsContract.Presenter, KamNaVyletApi.OnDetailsListener {

    public static final String TAG = DetailsPresenter.class.getSimpleName();

    @RootContext
    Context context;

    @Bean
    KamNaVyletApiBean api;

    private DetailsContract.View view;
    private Attraction attraction;

    private List<BaseAttractionInfo> nearByAttractions = new LinkedList<>();

    @Override
    public void start(String url) {
        if(attraction == null){
            view.showBusy(true, context.getString(R.string.loading_data));
            api.loadDetails(url, this);
        } else {
            setUpView(attraction);
        }
    }

    @Override
    public void setView(DetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void start(Attraction attraction){
        this.attraction = attraction;
        setUpView(attraction);
    }

    @Override
    public void nearByAttractionPicked(BaseAttractionInfo attraction) {
        view.showNearbyAttractionDetails(attraction);
    }

    @Override
    public void previewImagePicked(int imageIndex) {
        view.showFullImagePreviews(attraction.getLargeSizePhotos(), imageIndex);
    }

    @Override
    public void onDetailsLoaded(Attraction attraction) {
        this.attraction = attraction;
        view.setAttactionDetails(attraction);
        setUpView(attraction);
    }

    @Override
    @Subscribe
    public void detailClicked(DetailType type) {
        Log.v(TAG, "Detail clicked: " + type);
    }

    void setUpView(Attraction attraction){
        view.showBusy(true, context.getString(R.string.loading_data));
        view.setTitle(attraction.getName(), attraction.getTown(), attraction.getPreviewImageUrl());
        view.setImagePreviews(attraction.getNormalSizedPhotos());
        view.setCategory(attraction.getCategory().getDisplayName());
        view.setDetailsDescription(attraction.getDescription());
        setDetails(attraction);

        if(nearByAttractions.isEmpty()){
            loadNearbyAttractionDetails(attraction.getNearbyAttractions());
        } else {
            view.setNearByAttractions(nearByAttractions);
        }

        view.showBusy(false, null);
    }

    private void setDetails(Attraction attraction) {

        if(attraction.getCategory() != null) {
            view.setDetail(DetailType.CATEGORY, attraction.getCategory().getDisplayName());
        }

        if(attraction.getName() != null) {
            view.setDetail(DetailType.NAME, attraction.getName());
        }

        if(attraction.getRegion() != null) {
            view.setDetail(DetailType.REGION, attraction.getRegion().getName());
        }

        if(attraction.getArea() != null) {
            view.setDetail(DetailType.AREA, attraction.getArea());
        }

        if(attraction.getDistrict() != null) {
            view.setDetail(DetailType.DISTRICT, attraction.getDistrict());
        }

        if(attraction.getTown() != null) {
            view.setDetail(DetailType.TOWN, attraction.getTown());
        }

        if(attraction.getGps() != null) {
            view.setDetail(DetailType.GPS, attraction.getGps());
        }

        if(attraction.getPhone() != null) {
            view.setDetail(DetailType.TELEPHONE, attraction.getPhone());
        }

        if(attraction.getEmail() != null) {
            view.setDetail(DetailType.EMAIL, attraction.getEmail());
        }

        if(attraction.getWebSite() != null) {
            view.setDetail(DetailType.WEB_SITE, attraction.getWebSite());
        }
    }

    private void loadNearbyAttractionDetails(final List<NearbyAttraction> list)  {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for(NearbyAttraction na : list){
                    try {
                        Attraction ad = AttractionProvider.getAttraction(na.getUrl());
                        nearByAttractions.add(ad);
                    } catch (IOException e) {
                        Log.w(this.getClass().getSimpleName(), e);
                    }
                }
                view.setNearByAttractions(nearByAttractions);
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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
