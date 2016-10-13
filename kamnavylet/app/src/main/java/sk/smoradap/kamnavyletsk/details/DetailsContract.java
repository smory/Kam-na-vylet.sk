package sk.smoradap.kamnavyletsk.details;

import java.util.List;
import java.util.Map;

import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.NearbyAttraction;

/**
 * Created by psmorada on 13.10.2016.
 */
public interface DetailsContract {

    interface Presenter {
        void start(String url);
        void nearByAttractionPicked(AttractionDetails attraction);
        void previewImagePicked(int imageIndex);
    }

    interface View {
        void setImagePreviews(List<String> urls);
        void setDetailsDescription(String text);
        void setTitle(String mainTitle, String subTitle, String iconUrl);
        void setCategory(String category);
        void setDetails(Map<String, String> details);
        void setNearByAttractions(List<AttractionDetails> nearbyAttractions);
        void showNearbyAttractionDetails(AttractionDetails nearbyAttraction);
        void showFullImagePreviews(List<String> urls, int startIndex);

        void setBusy(boolean busy);

        void showCannotLoadData();

    }

}
