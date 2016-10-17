package sk.smoradap.kamnavyletsk.main;

/**
 * Created by psmorada on 17.10.2016.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void attactionPicked() {

    }
}
