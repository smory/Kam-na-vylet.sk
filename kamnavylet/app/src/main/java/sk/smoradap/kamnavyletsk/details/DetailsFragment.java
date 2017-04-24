package sk.smoradap.kamnavyletsk.details;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.ImageBrowseActivity_;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.base.BaseContract;
import sk.smoradap.kamnavyletsk.base.BaseFragment;
import sk.smoradap.kamnavyletsk.gui.AnimationListenerAdapter;
import sk.smoradap.kamnavyletsk.gui.DetailsTable;
import sk.smoradap.kamnavyletsk.gui.ImageRecyclerAdapter;
import sk.smoradap.kamnavyletsk.gui.ItemView;
import sk.smoradap.kamnavyletsk.gui.ItemView_;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;

/**
 * Created by Peter Smorada on 19.4.2017.
 */

@EFragment(R.layout.fragment_details)
public class DetailsFragment extends BaseFragment implements DetailsContract.View,
        ImageRecyclerAdapter.OnImageClickedInterface {

    @ViewById(R.id.rv_images_preview)
    RecyclerView imageRecyclerView;

    @ViewById(R.id.tv_details_description)
    TextView detailsTextView;

    @ViewById(R.id.tv_details_more_less)
    TextView detailsMoreLessTextVew;

    @ViewById(R.id.tv_name)
    TextView tvTitle;

    @ViewById(R.id.tv_place)
    TextView tvPlace;

    @ViewById(R.id.iv_base_icon)
    ImageView ivBaseIcon;

    @ViewById(R.id.tv_category)
    TextView tvCategory;

    @ViewById(R.id.details_table)
    DetailsTable detailsTable;

    @ViewById(R.id.details_card)
    CardView detailsCardView;

    @ViewById(R.id.details_table_drop_icon)
    ImageView detailsDropIcon;

    @ViewById(R.id.nearby_item_layout)
    LinearLayout llNearbyAttrationsLayout;

    @ViewById(R.id.nearby_card)
    CardView nearbyCard;

    @ViewById(R.id.nearby_drop_icon)
    ImageView ivNearByDropIcon;

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

    @FragmentArg
    String url;

    @FragmentArg
    AttractionDetails attractionDetails;

    @Bean(DetailsPresenter.class)
    DetailsContract.Presenter presenter;

    private ImageRecyclerAdapter imageRecyclerAdapter;

    @AfterViews
    void setupPresenter(){
        presenter.setView(this);
    }

    @AfterViews
    void configureViews(){
        imageRecyclerView.setNestedScrollingEnabled(false);
        detailsTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onStart(){
        super.onStart();

        if(attractionDetails != null){
            presenter.start(attractionDetails);
        } else {
            presenter.start(url);
        }

    }

    @Click(R.id.details_description_layout)
    void descriptionToggle(){

        ObjectAnimator animator;
        if(detailsTextView.getMaxLines() == 5){
            animator = ObjectAnimator.ofInt(detailsTextView, "maxLines", 5, detailsTextView.getLineCount());
            detailsMoreLessTextVew.setText(getString(R.string.less));
        } else {
            animator = ObjectAnimator.ofInt(detailsTextView, "maxLines", detailsTextView.getLineCount(), 5);
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
            detailsDropIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_180_back));
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
            animation.setAnimationListener(new AnimationListenerAdapter() {

                @Override
                public void onAnimationEnd(Animation animation) {
                    detailsTable.setVisibility(View.GONE);
                }

            });
            detailsTable.startAnimation(animation);
        } else {
            detailsTable.setVisibility(View.VISIBLE);
            detailsDropIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_180));
            detailsTable.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_down));
        }

    }

    @Click(R.id.nearby_card)
    public void toggleNearByItems(){
        if(llNearbyAttrationsLayout.getVisibility() == View.VISIBLE){

            slideUpAnimation.setAnimationListener(new AnimationListenerAdapter() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    llNearbyAttrationsLayout.setVisibility(View.GONE);
                }

            });
            slideUpAnimation.cancel();
            llNearbyAttrationsLayout.startAnimation(slideUpAnimation);
            ivNearByDropIcon.startAnimation(rotate180backAnimation);

        } else {
            ivNearByDropIcon.startAnimation(rotate180Animation);
            llNearbyAttrationsLayout.setVisibility(View.VISIBLE);
            llNearbyAttrationsLayout.startAnimation(slideDownAnimation);

        }

    }



    @Override
    @UiThread
    public void setImagePreviews(List<String> urls) {
        imageRecyclerAdapter = new ImageRecyclerAdapter(getContext(), urls, this);
        imageRecyclerView.setAdapter(imageRecyclerAdapter);
    }

    @Override
    @UiThread
    public void setDetailsDescription(String text) {
        detailsTextView.setText(Html.fromHtml(text));
    }

    @Override
    @UiThread
    public void setTitle(String mainTitle, String subTitle, String iconUrl) {
        tvTitle.setText(Html.fromHtml("<b>" + mainTitle + "</b>"));
        tvPlace.setText(subTitle);

        Glide.with(this).load(iconUrl)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(ivBaseIcon);
    }

    @Override
    @UiThread
    public void setCategory(String category) {
        tvCategory.setText(category);
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
        llNearbyAttrationsLayout.removeAllViews();

        for(final AttractionDetails nearbyAttraction : nearbyAttractions){
            final ItemView item = ItemView_.build(getContext());
            item.setName(nearbyAttraction.getName());
            item.setPlace(nearbyAttraction.getTown());

            if(!nearbyAttraction.getImageUrls().isEmpty()){
                item.setIcon(nearbyAttraction.getImageUrls().get(0));
            }

            llNearbyAttrationsLayout.addView(item );
            System.out.println("adding nearby item");
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.nearByAttractionPicked(nearbyAttraction);
                }
            });
            View v = new View(getContext());
            v.setBackgroundResource(R.drawable.line_separator);

        }

        llNearbyAttrationsLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNearbyAttractionDetails(AttractionDetails nearbyAttraction) {
        DetailsActivity2_.intent(getContext())
                .url(nearbyAttraction.getSourceUrl())
                .attractionDetails(nearbyAttraction)
                .start();
    }

    @Override
    public void showFullImagePreviews(List<String> urls, int startIndex) {
        Intent i = new Intent(getContext(), ImageBrowseActivity_.class);
        i.putExtra(ImageBrowseActivity_.IMAGE_URLS, (Serializable) urls);
        i.putExtra(ImageBrowseActivity_.PAGER_POSITION, startIndex);
        startActivity(i);
    }

    @Override
    public void showCannotLoadData() {

    }

    @Override
    public void imageClicked(int position) {
        presenter.previewImagePicked(position);
    }

    @Override
    public DetailsContract.Presenter getPresenter() {
        return presenter;
    }
}
