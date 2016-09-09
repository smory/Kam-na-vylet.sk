package sk.smoradap.kamnavyletsk;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.gui.ImageRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity implements KamNaVyletApi.OnDetailsListener {

    public static final String tag = "sk.smoradap.kamnavylet";

    private ImageRecyclerAdapter mImageAdapter;

    @ViewById(R.id.rv_images_preview)
    RecyclerView mImageRecyclerView;

    @ViewById(R.id.tv_details_description)
    TextView mDetailsTextView;

    @ViewById(R.id.tv_name)
    TextView mTitle;

    @ViewById(R.id.tv_place)
    TextView mTownTextView;

    @ViewById(R.id.iv_base_icon)
    ImageView mBaseIcon;

    @ViewById(R.id.tv_category)
    TextView mCategoryTextView;

    private String mUrl;
    private AttractionDetails mAttractionDetails;

    @Bean
    KamNaVyletApi api;


    @AfterViews
    public void testUrls(){
        mUrl = "http://www.kamnavylet.sk/atrakcia/hrad-lubovna-stara-lubovna";
    }

    @Override
    public void onStart(){
        super.onStart();

        if(mAttractionDetails != null){
            setUpViews(mAttractionDetails);
        } else {
            Log.i(tag, "Loading details.");
            api.loadDetails(mUrl, this);
        }
    }

    @UiThread
    void setUpViews(AttractionDetails details){
        mImageAdapter = new ImageRecyclerAdapter(this, details.getImageUrls());
        mImageRecyclerView.setAdapter(mImageAdapter);
        mDetailsTextView.setText(Html.fromHtml(details.getDescription()));
        mTitle.setText(Html.fromHtml("<b>" + details.getName() + "</b>"));
        mTownTextView.setText(details.getTown());
        mCategoryTextView.setText(details.getCategory());

        Glide.with(this).load(details.getImageUrls().get(0))
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mBaseIcon);

    }


    @Override
    public void onDetailsLoaded(AttractionDetails details) {
        mAttractionDetails = details;
        setUpViews(mAttractionDetails);
    }

    @Override
    public void onDetailsFailure(IOException e) {
        e.printStackTrace();
    }
}
