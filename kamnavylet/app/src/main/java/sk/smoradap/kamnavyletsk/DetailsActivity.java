package sk.smoradap.kamnavyletsk;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.gui.AnimationListenerAdapter;
import sk.smoradap.kamnavyletsk.gui.DetailsTable;
import sk.smoradap.kamnavyletsk.gui.ImageRecyclerAdapter;
import sk.smoradap.kamnavyletsk.gui.SearchItem;
import sk.smoradap.kamnavyletsk.gui.SearchItem_;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.NearbyAttraction;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity implements KamNaVyletApi.OnDetailsListener {

    public static final String tag = "sk.smoradap.kamnavylet";

    private ImageRecyclerAdapter mImageAdapter;

    @ViewById(R.id.rv_images_preview)
    RecyclerView mImageRecyclerView;

    @ViewById(R.id.tv_details_description)
    TextView mDetailsTextView;

    @ViewById(R.id.tv_details_more_less)
    TextView detailsMoreLessTextVew;

    @ViewById(R.id.details_description_layout)
    LinearLayout mDescrptionLayout;

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

    @ViewById(R.id.details_card)
    CardView detailsCardView;

    @ViewById(R.id.details_table_drop_icon)
    ImageView detailsDropIcon;

    @ViewById(R.id.nearby_item_layout)
    LinearLayout nearbyAttrationsLayout;

    @ViewById(R.id.nearby_card)
    CardView nearbyCard;

    @ViewById(R.id.nearby_drop_icon)
    ImageView nearByDropIcon;

    @AnimationRes(R.anim.rotate_180)
    Animation rotate180Animation;

    @AnimationRes(R.anim.rotate_180_back)
    Animation rotate180backAnimation;

    @AnimationRes(R.anim.slide_down)
    Animation slideDownAnimation;

    @AnimationRes(R.anim.slide_up)
    Animation slideUpAnimation;

    private String mUrl;
    private AttractionDetails mAttractionDetails;


    @Bean
    KamNaVyletApi api;

    @AfterViews
    void configureViews(){
        mImageRecyclerView.setNestedScrollingEnabled(false);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

    }

    @AfterViews
    public void testUrls(){
        mUrl = "http://www.kamnavylet.sk/atrakcia/hrad-lubovna-stara-lubovna";
        mUrl = "http://www.kamnavylet.sk/atrakcia/hrad-branc-podbranc";
    }

    @Override
    public void onStart(){
        super.onStart();

        if(mAttractionDetails != null){
            setUpViews(mAttractionDetails);
        } else {
            Log.i(tag, "Loading details.");
            Intent i = getIntent();
            System.out.println(i.getStringExtra("URL"));
            api.loadDetails(i.getStringExtra("URL") == null ? mUrl : i.getStringExtra("URL"), this);
        }
    }

    @UiThread
    void setUpViews(final AttractionDetails details){
        mImageAdapter = new ImageRecyclerAdapter(this, details.getImageUrls());
        mImageRecyclerView.setAdapter(mImageAdapter);
        mDetailsTextView.setText(Html.fromHtml(details.getDescription()));
        mTitle.setText(Html.fromHtml("<b>" + details.getName() + "</b>"));
        mTownTextView.setText(details.getTown());
        mCategoryTextView.setText(details.getCategory());

        Glide.with(this).load(details.getImageUrls().get(0))
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mBaseIcon);

        collapsingToolbarLayout.setTitle(Html.fromHtml("<b>" + details.getName() + "</b>"));

        for(Map.Entry<String,String> entry : details.getDetailsMap().entrySet()){
            detailsTable.addRow(entry.getKey(), entry.getValue());
        }

        if(!details.getNearbyAttractions().isEmpty()){
            setUpNearbyAttractions(details.getNearbyAttractions());
        }

        nearbyAttrationsLayout.setVisibility(View.GONE);

    }

    @Click(R.id.details_description_layout)
    void descriptionToggle(){

        ObjectAnimator animator;
        if(mDetailsTextView.getMaxLines() == 5){
            animator = ObjectAnimator.ofInt(mDetailsTextView, "maxLines", 5, mDetailsTextView.getLineCount());
            detailsMoreLessTextVew.setText(getString(R.string.less));
        } else {
            animator = ObjectAnimator.ofInt(mDetailsTextView, "maxLines", mDetailsTextView.getLineCount(), 5);
            detailsMoreLessTextVew.setText(getString(R.string.more));
        }

        animator.setDuration(200);
        animator.start();

    }

    @UiThread
    public void setUpNearByDetails(AttractionDetails details, SearchItem item){
        item.setName(details.getName());
        item.setPlace(details.getTown());
        if(!details.getImageUrls().isEmpty()){
            item.setIcon(details.getImageUrls().get(0));
        }
    }

    @Click(R.id.details_card)
    public void toggleDetailsTable(){

        System.out.println("details card clicked");

        if(detailsTable.getVisibility() == View.VISIBLE){
            //detailsTable.setVisibility(View.GONE);
            detailsDropIcon.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_180_back));
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            animation.setAnimationListener(new AnimationListenerAdapter() {

                @Override
                public void onAnimationEnd(Animation animation) {
                    detailsTable.setVisibility(View.GONE);
                }

            });
            detailsTable.startAnimation(animation);
        } else {
            detailsTable.setVisibility(View.VISIBLE);
            detailsDropIcon.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_180));
            detailsTable.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
        }

    }

    @Click(R.id.nearby_card)
    public void toggleNearByItems(){
        if(nearbyAttrationsLayout.getVisibility() == View.VISIBLE){

            slideUpAnimation.setAnimationListener(new AnimationListenerAdapter() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    nearbyAttrationsLayout.setVisibility(View.GONE);
                }

            });
            slideUpAnimation.cancel();
            nearbyAttrationsLayout.startAnimation(slideUpAnimation);
            nearByDropIcon.startAnimation(rotate180backAnimation);

        } else {
            nearByDropIcon.startAnimation(rotate180Animation);
            nearbyAttrationsLayout.setVisibility(View.VISIBLE);
            nearbyAttrationsLayout.startAnimation(slideDownAnimation);

        }

    }

    public void setUpNearbyAttractions(List<NearbyAttraction> attractions){
        for(final NearbyAttraction nearbyAttraction : attractions){
            final SearchItem item = SearchItem_.build(getApplicationContext());
            item.setName(nearbyAttraction.getName());
            item.setPlace(nearbyAttraction.getUrl());
            nearbyAttrationsLayout.addView(item );
            System.out.println("adding nearby item");
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), DetailsActivity_.class);
                    i.putExtra("URL", nearbyAttraction.getUrl());
                    startActivity(i);
                }
            });

            KamNaVyletApi.OnDetailsListener listener = new KamNaVyletApi.OnDetailsListener() {
                @Override
                public void onDetailsLoaded(AttractionDetails details) {
                    setUpNearByDetails(details, item);
                }

                @Override
                public void onDetailsFailure(IOException e) {

                }
            };

            api.loadDetails(nearbyAttraction.getUrl(), listener);

            View v = new View(getApplicationContext());
            //v.setBackgroundResource(R.drawable.line_separator);
            nearbyAttrationsLayout.addView(v);
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
