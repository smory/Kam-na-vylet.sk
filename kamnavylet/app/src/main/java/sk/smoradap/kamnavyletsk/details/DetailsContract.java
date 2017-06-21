package sk.smoradap.kamnavyletsk.details;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import sk.smoradap.kamnavyletsk.api.model.Attraction;
import sk.smoradap.kamnavyletsk.api.model.BaseAttractionInfo;
import sk.smoradap.kamnavyletsk.api.model.DetailType;
import sk.smoradap.kamnavyletsk.api.model.Photo;
import sk.smoradap.kamnavyletsk.base.BaseContract;

/**
 * Created by psmorada on 13.10.2016.
 */
public interface DetailsContract {

    interface Presenter extends BaseContract.Presenter{
        void start(String url);
        void start(Attraction details);
        void nearByAttractionPicked(BaseAttractionInfo attraction);
        void previewImagePicked(int imageIndex);
        void setView(View view);
        void detailClicked(DetailType type);
    }

    interface View extends BaseContract.View {
        void setImagePreviews(Collection<? extends Photo> photos);
        void setDetailsDescription(String text);
        void setTitle(String mainTitle, String subTitle, String iconUrl);
        void setCategory(String category);
        void setDetail(DetailType type, String detailValue);
        void setNearByAttractions(List<? extends BaseAttractionInfo> nearbyAttractions);
        void showNearbyAttractionDetails(BaseAttractionInfo nearbyAttraction);
        void showFullImagePreviews(Collection<? extends Photo> urls, int startIndex);
        void showCannotLoadData();
        void setAttactionDetails(Attraction details);

    }

}
