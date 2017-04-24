package sk.smoradap.kamnavyletsk.base;

/**
 * Created by smorada on 24.4.2017.
 */

public interface BaseContract {

    interface Presenter {

        void onStart();
        void onStop();
        void onResume();
        void onPause();

    }

    interface View <T extends Presenter> {
        void showBusy(boolean show, CharSequence message);
        T getPresenter();
    }

}
