package sk.smoradap.kamnavyletsk;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.gui.DetailsTable;
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

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @ViewById(R.id.toolbar_name)
    TextView toolbarName;

    @ViewById(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @ViewById(R.id.details_table)
    DetailsTable detailsTable;

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
    void setUpViews(final AttractionDetails details){
        mImageAdapter = new ImageRecyclerAdapter(this, details.getImageUrls());
        mImageRecyclerView.setAdapter(mImageAdapter);
        mImageRecyclerView.setNestedScrollingEnabled(false);
        mDetailsTextView.setText(Html.fromHtml(details.getDescription()));
        mTitle.setText(Html.fromHtml("<b>" + details.getName() + "</b>"));
        mTownTextView.setText(details.getTown());
        mCategoryTextView.setText(details.getCategory());

        Glide.with(this).load(details.getImageUrls().get(0))
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mBaseIcon);

        setSupportActionBar(toolbar);
        //collapsingToolbarLayout.setTitle(details.getName());
        collapsingToolbarLayout.setTitle(Html.fromHtml("<b>" + details.getName() + "</b>"));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));


        //toolbarName.setText(details.getName());
        /*appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                System.out.println(verticalOffset);
                if(verticalOffset < -40){
                    toolbarName.setText(details.getName());
                    System.out.println("change   offset: " + verticalOffset);
                } else if(verticalOffset > -40 || toolbarName.getText().equals(details.getName())) {
                    toolbarName.setText("");
                    System.out.println("change 1");
                }

            }
        });*/

        for(Map.Entry<String,String> entry : details.getDetailsMap().entrySet()){
            detailsTable.addRow(entry.getKey(), entry.getValue());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
