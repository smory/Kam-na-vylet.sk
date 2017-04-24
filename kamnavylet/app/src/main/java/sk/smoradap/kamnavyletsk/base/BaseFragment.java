package sk.smoradap.kamnavyletsk.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

/**
 * Created by Peter Smorada on 19.4.2017.
 */
@EFragment
public abstract class BaseFragment extends Fragment implements BaseContract.View {

    protected ProgressDialog progressDialog;

    @Override
    @UiThread
    public void showBusy(boolean show, CharSequence message) {
        if(show){
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();

        } else {
            if(progressDialog != null){
                progressDialog.dismiss();
                progressDialog = null;
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(getPresenter() != null){
            getPresenter().onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(getPresenter() != null){
            getPresenter().onStop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getPresenter() != null){
            getPresenter().onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getPresenter() != null){
            getPresenter().onPause();
        }
    }
}
