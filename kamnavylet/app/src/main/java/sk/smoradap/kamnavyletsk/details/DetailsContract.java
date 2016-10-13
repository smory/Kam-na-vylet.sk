package sk.smoradap.kamnavyletsk.details;

import java.util.List;
import java.util.Map;

import sk.smoradap.kamnavyletsk.model.NearbyAttraction;

/**
 * Created by psmorada on 13.10.2016.
 */
public interface DetailsContract {

    interface Presenter {
        void start(String url);
        void nearByAttractionPicked(NearbyAttraction attraction);
        void previewImagePicked(int imageIndex);
    }

    interface View {
        void setImagePreviews(List<String> urls);
        void setDetailsDescription(String text);
        void setDescription(String mainTitle, String subTitle, String iconUrl);
        void setCategory(String category);
        void setDetails(Map<String, String> details);
        void setNearByAttractions(List<NearbyAttraction> nearbyAttractions);
        void showNearbyAttractionDetails(NearbyAttraction nearbyAttraction);
        void showFullImagePreviews(List<String> urls, int startIndex);

        void setBusy(boolean busy);

        void showCannotLoadData();

    }

}
