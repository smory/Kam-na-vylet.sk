package sk.smoradap.kamnavyletsk.details;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.ImageBrowseActivity_;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.SearchActivity_;
import sk.smoradap.kamnavyletsk.gui.AnimationListenerAdapter;
import sk.smoradap.kamnavyletsk.gui.DetailsTable;
import sk.smoradap.kamnavyletsk.gui.ImageRecyclerAdapter;
import sk.smoradap.kamnavyletsk.gui.ItemView;
import sk.smoradap.kamnavyletsk.gui.ItemView_;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity implements DetailsContract.View,
        ImageRecyclerAdapter.OnImageClickedInterface {

    public static final String tag = "sk.smoradap.kamnavylet";
    public static final String URL = "URL";
    public static final String DETAILS = "details";

    DetailsContract.Presenter mPresenter;

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

    @ViewById(R.id.progressLayout)
    RelativeLayout progressLayout;

    @AnimationRes(R.anim.rotate_180)
    Animation rotate180Animation;

    @AnimationRes(R.anim.rotate_180_back)
    Animation rotate180backAnimation;

    @AnimationRes(R.anim.slide_down)
    Animation slideDownAnimation;

    @AnimationRes(R.anim.slide_up)
    Animation slideUpAnimation;

    private String mUrl;

    @AfterViews
    void createPresenter(){
        if(mPresenter == null){
            mPresenter = new DetailsPresenter(this);
        }
    }

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
        mUrl =  getIntent().getStringExtra(URL) == null ? mUrl :  getIntent().getStringExtra(URL);
        AttractionDetails details = (AttractionDetails) getIntent().getSerializableExtra(DETAILS);
        if(details != null){
            mPresenter.start(details);
        } else {
            mPresenter.start(mUrl);
        }

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
    @UiThread
    public void setImagePreviews(List<String> urls) {
        mImageAdapter = new ImageRecyclerAdapter(this, urls, this);
        mImageRecyclerView.setAdapter(mImageAdapter);
    }

    @Override
    @UiThread
    public void setDetailsDescription(String text) {
        mDetailsTextView.setText(Html.fromHtml(text));
    }

    @Override
    @UiThread
    public void setTitle(String mainTitle, String subTitle, String iconUrl) {
        mTitle.setText(Html.fromHtml("<b>" + mainTitle + "</b>"));
        mTownTextView.setText(subTitle);

        Glide.with(this).load(iconUrl)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mBaseIcon);

        collapsingToolbarLayout.setTitle("");
        toolbar.setTitle("");
    }

    @Override
    @UiThread
    public void setCategory(String category) {
        mCategoryTextView.setText(category);
    }

    @Override
    @UiThread
    public void setDetails(Map<String, String> details) {
        detailsTable.removeAllViews();
        for (Map.Entry<String, String> entry : details.entrySet()) {
            detailsTable.addRow(entry.getKey(), entry.getValue());
        }
    }

    @Override
    @UiThread
    public void setNearByAttractions(List<AttractionDetails> nearbyAttractions) {
        nearbyAttrationsLayout.removeAllViews();

        for(final AttractionDetails nearbyAttraction : nearbyAttractions){
            final ItemView item = ItemView_.build(getApplicationContext());
            item.setName(nearbyAttraction.getName());
            item.setPlace(nearbyAttraction.getTown());

            if(!nearbyAttraction.getImageUrls().isEmpty()){
                item.setIcon(nearbyAttraction.getImageUrls().get(0));
            }

            nearbyAttrationsLayout.addView(item );
            System.out.println("adding nearby item");
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.nearByAttractionPicked(nearbyAttraction);
                }
            });
            View v = new View(getApplicationContext());
            v.setBackgroundResource(R.drawable.line_separator);

        }

        nearbyAttrationsLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNearbyAttractionDetails(AttractionDetails nearbyAttraction) {
        Intent i = new Intent(this, DetailsActivity_.class);
        i.putExtra(DetailsActivity.DETAILS, nearbyAttraction);
        startActivity(i);
    }

    @Override
    public void showFullImagePreviews(List<String> urls, int startIndex) {
        Intent i = new Intent(this, ImageBrowseActivity_.class);
        i.putExtra(ImageBrowseActivity_.IMAGE_URLS, (Serializable) urls);
        i.putExtra(ImageBrowseActivity_.PAGER_POSITION, startIndex);
        startActivity(i);
    }

    @Override
    @UiThread
    public void setBusy(boolean busy) {
        if(busy){
            progressLayout.setVisibility(View.VISIBLE);
        } else {
            progressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCannotLoadData() {

    }

    @Override
    public void imageClicked(int position) {
        mPresenter.previewImagePicked(position);
    }
}
