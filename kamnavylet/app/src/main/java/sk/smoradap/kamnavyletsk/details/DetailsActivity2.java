package sk.smoradap.kamnavyletsk.details;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import sk.smoradap.kamnavyletsk.base.BaseActivity;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.base.BaseContract;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;

@EActivity(R.layout.activity_details2)
public class DetailsActivity2 extends BaseActivity {

    @ViewById
    Toolbar toolbar;

    @Extra
    String url;

    @Extra
    AttractionDetails attractionDetails;

    private String testUrl = "http://www.kamnavylet.sk/atrakcia/hrad-lubovna-stara-lubovna";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            Fragment fragment = DetailsFragment_.builder()
                    .url(url == null?  testUrl: url)
                    .attractionDetails(attractionDetails)
                    .build();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

    }

    @AfterViews
    void setupToolbar(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }
}
