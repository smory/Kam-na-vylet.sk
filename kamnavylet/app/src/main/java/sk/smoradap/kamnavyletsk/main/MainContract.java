package sk.smoradap.kamnavyletsk.main;

import java.util.List;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.Item;

/**
 * Created by psmorada on 17.10.2016.
 */
public interface MainContract {

    interface View {

        void setPresenter(Presenter presenter);
        void registerForSingleLocationUpdate(OnLocationUpdatedListener listener);
        void showAttractionDetails(AttractionDetails details);
        void showAttractionDetails(String url);
        void showNearbyAttractions(List<? extends Item> items);
    }

    interface Presenter {
        void start();
        void attactionPicked(Item item);
    }
}
