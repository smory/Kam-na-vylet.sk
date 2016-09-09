package sk.smoradap.kamnavyletsk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.gui.ImageRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;

public class DetailsActivity extends AppCompatActivity implements KamNaVyletApi.OnDetailsListener {

    public static final String tag = "sk.smoradap.kamnavylet";

    private ImageRecyclerAdapter mImageAdapter;
    private RecyclerView mImageRecyclerView;
    private TextView mDetailsTextView;
    private TextView mTitle;
    private TextView mTownTextView;
    private ImageView mBaseIcon;
    private TextView mCategoryTextView;

    private String mUrl;
    private AttractionDetails mAttractionDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mUrl = "http://www.kamnavylet.sk/atrakcia/hrad-lubovna-stara-lubovna";
        //mUrl = "http://www.kamnavylet.sk/atrakcia/aquapark-tatralandia-liptovsky-mikulas";

        mImageRecyclerView = (RecyclerView) findViewById(R.id.rv_images_preview);
        mDetailsTextView = (TextView) findViewById(R.id.tv_details_description);
        mTitle = (TextView) findViewById(R.id.tv_name);
        mBaseIcon = (ImageView) findViewById(R.id.iv_base_icon);
        mTownTextView = (TextView) findViewById(R.id.tv_place);
        mCategoryTextView = (TextView) findViewById(R.id.tv_category);
    }

    @Override
    public void onStart(){
        super.onStart();

        if(mAttractionDetails != null){
            setUpViews(mAttractionDetails);
        } else {
            Log.i(tag, "Loading details.");
            new KamNaVyletApi().loadDetails(mUrl, this);
        }
    }

    private void setUpViews(AttractionDetails details){
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
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpViews(mAttractionDetails);
            }
        });
    }

    @Override
    public void onDetailsFailure(IOException e) {
        e.printStackTrace();
    }
}
