package sk.smoradap.kamnavyletsk.main;

import java.util.List;

import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.Item;

/**
 * Created by psmorada on 17.10.2016.
 */
public interface MainContract {

    interface View {
        void showAttractionDetails(AttractionDetails details);
        void showAttractionDetails(String url);
        void showNearbyAttractions(List<? extends Item> items);
        void showBusy(boolean busy);
        void performSearch(String query);
    }

    interface Presenter {
        void start();
        void attactionPicked(Item item);
        void searchReqested(String query);
        void setView(View view);
    }
}
