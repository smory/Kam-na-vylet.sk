package sk.smoradap.kamnavyletsk.main;

import java.util.List;

import sk.smoradap.kamnavyletsk.api.model.Attraction;
import sk.smoradap.kamnavyletsk.api.model.BaseAttractionInfo;

/**
 * Created by psmorada on 17.10.2016.
 */
public interface MainContract {

    interface View {
        void showAttractionDetails(Attraction attraction);

        void showAttractionDetails(String url);

        void showNearbyAttractions(List<? extends BaseAttractionInfo> items);

        void showBusy(boolean busy);

        void performSearch(String query);
    }

    interface Presenter {
        void start();

        void attactionPicked(BaseAttractionInfo item);

        void searchReqested(String query);

        void setView(View view);
    }
}
