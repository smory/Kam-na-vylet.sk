package sk.smoradap.kamnavyletsk.details;

import java.util.List;
import java.util.Map;

import sk.smoradap.kamnavyletsk.base.BaseContract;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;

/**
 * Created by psmorada on 13.10.2016.
 */
public interface DetailsContract {

    interface Presenter extends BaseContract.Presenter{
        void start(String url);
        void start(AttractionDetails details);
        void nearByAttractionPicked(AttractionDetails attraction);
        void previewImagePicked(int imageIndex);
        void setView(View view);
    }

    interface View extends BaseContract.View {
        void setImagePreviews(List<String> urls);
        void setDetailsDescription(String text);
        void setTitle(String mainTitle, String subTitle, String iconUrl);
        void setCategory(String category);
        void setDetails(Map<String, String> details);
        void setNearByAttractions(List<AttractionDetails> nearbyAttractions);
        void showNearbyAttractionDetails(AttractionDetails nearbyAttraction);
        void showFullImagePreviews(List<String> urls, int startIndex);
        void showCannotLoadData();
        void setAttactionDetails(AttractionDetails details);

    }

}
