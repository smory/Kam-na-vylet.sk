package sk.smoradap.kamnavyletsk.details;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
import org.androidannotations.annotations.res.DimensionPixelSizeRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.ImageBrowseActivity_;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.model.Attraction;
import sk.smoradap.kamnavyletsk.api.model.BaseAttractionInfo;
import sk.smoradap.kamnavyletsk.api.model.DetailType;
import sk.smoradap.kamnavyletsk.api.model.Photo;
import sk.smoradap.kamnavyletsk.base.BaseFragment;
import sk.smoradap.kamnavyletsk.gui.DetailsTable;
import sk.smoradap.kamnavyletsk.gui.ImageRecyclerAdapter;
import sk.smoradap.kamnavyletsk.gui.ItemView;
import sk.smoradap.kamnavyletsk.gui.ItemView_;
import sk.smoradap.kamnavyletsk.utils.StringResolver;

/**
 * Created by Peter Smorada on 19.4.2017.
 */

@EFragment(R.layout.fragment_details)
public class DetailsFragment extends BaseFragment implements DetailsContract.View,
        ImageRecyclerAdapter.OnImageClickedInterface, NestedScrollView.OnScrollChangeListener,
        AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.5f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    @ViewById(R.id.rv_images_preview)
    RecyclerView imageRecyclerView;

    @ViewById(R.id.sw_parent)
    NestedScrollView scrollView;

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

    @ViewById(R.id.nearby_item_layout)
    LinearLayout llNearbyAttrationsLayout;

    @ViewById(R.id.nearby_card)
    CardView nearbyCard;

    @ViewById(R.id.progressLayout)
    RelativeLayout progressLayout;

    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @ViewById(R.id.appbar)
    AppBarLayout appBarLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.ll_toolbar_title_holder)
    LinearLayout llToolbarTitleHolder;

    @ViewById
    TextView tvToolbarTitle;

    @ViewById
    TextView tvToolbarSubtitle;

    @AnimationRes(R.anim.rotate_180)
    Animation rotate180Animation;

    @AnimationRes(R.anim.rotate_180_back)
    Animation rotate180backAnimation;

    @AnimationRes(R.anim.slide_down)
    Animation slideDownAnimation;

    @AnimationRes(R.anim.slide_up)
    Animation slideUpAnimation;

    @DimensionPixelSizeRes(R.dimen.basic_item_image_size)
    int headerSize;

    @DimensionPixelSizeRes(R.dimen.padding_base2x)
    int basePadding2x;

    @FragmentArg
    String url;

    @FragmentArg
    Attraction attraction;

    @Bean(DetailsPresenter.class)
    DetailsContract.Presenter presenter;

    private ImageRecyclerAdapter imageRecyclerAdapter;

    private boolean visibleToolbarTitle = false;
    private boolean visibleTitle = true;

    @AfterViews
    void setupPresenter() {
        presenter.setView(this);
    }

    @AfterViews
    void configureViews() {
        imageRecyclerView.setNestedScrollingEnabled(false);
        detailsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        scrollView.setOnScrollChangeListener(this);
        appBarLayout.addOnOffsetChangedListener(this);

        //toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (attraction != null) {
            presenter.start(attraction);
        } else {
            presenter.start(url);
        }

    }

    @Click(R.id.details_description_layout)
    void descriptionToggle() {

        ObjectAnimator animator;
        if (detailsTextView.getMaxLines() == 5) {
            animator = ObjectAnimator.ofInt(detailsTextView, "maxLines", 5, detailsTextView.getLineCount());
            detailsMoreLessTextVew.setText(getString(R.string.less));
        } else {
            animator = ObjectAnimator.ofInt(detailsTextView, "maxLines", detailsTextView.getLineCount(), 5);
            detailsMoreLessTextVew.setText(getString(R.string.more));
        }

        animator.setDuration(200);
        animator.start();

    }


    @Override
    @UiThread
    public void setImagePreviews(Collection<? extends Photo> photos) {
        imageRecyclerAdapter = new ImageRecyclerAdapter(getContext(), photos, this);
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
        tvTitle.setText(mainTitle);
        tvPlace.setText(subTitle);
        tvToolbarTitle.setText(mainTitle);
        tvToolbarSubtitle.setText(subTitle);

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
    public void setDetail(DetailType type, String detailValue) {
        if(detailsTable != null) {
            detailsTable.add(StringResolver.getDetailDescription(type), detailValue, type);
        }
    }

    @Override
    @UiThread
    public void setNearByAttractions(List<? extends BaseAttractionInfo> nearbyAttractions) {
        llNearbyAttrationsLayout.removeAllViews();

        for (final BaseAttractionInfo nearbyAttraction : nearbyAttractions) {

            if (llNearbyAttrationsLayout.getChildCount() > 0) {
                View separator = LayoutInflater.from(getActivity()).inflate(R.layout.view_separator, llNearbyAttrationsLayout, true);
                separator.setPadding(basePadding2x, 0, basePadding2x, 0);
            }

            final ItemView item = ItemView_.build(getContext());
            item.setName(nearbyAttraction.getName());
            item.setPlace(nearbyAttraction.getTown());

            if (nearbyAttraction.getPreviewImageUrl() != null) {
                item.setIcon(nearbyAttraction.getPreviewImageUrl());
            }

            llNearbyAttrationsLayout.addView(item);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.nearByAttractionPicked(nearbyAttraction);
                }
            });
            View v = new View(getContext());
            v.setBackgroundResource(R.drawable.line_separator);

        }
    }

    @Override
    public void showNearbyAttractionDetails(BaseAttractionInfo nearbyAttraction) {
        DetailsActivity_.intent(getContext())
                .url(nearbyAttraction.getSourceUrl())
//                .attraction(nearbyAttraction)
                .start();
    }

    @Override
    public void showFullImagePreviews(Collection<? extends Photo> photos, int startIndex) {
        ArrayList<String> urls = new ArrayList<>();
        for(Photo photo : photos){
            urls.add(photo.getUrl());
        }
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

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//        if (scrollY > headerSize) {
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(attraction.getName());
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(attraction.getTown());
//        } else {
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
//        }
    }

    @Override
    public void setAttactionDetails(Attraction attraction) {
        this.attraction = attraction;
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        v.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!visibleToolbarTitle) {
                startAlphaAnimation(llToolbarTitleHolder, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                visibleToolbarTitle = true;
            }

        } else {

            if (visibleToolbarTitle) {
                startAlphaAnimation(llToolbarTitleHolder, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                visibleToolbarTitle = false;
            }
        }
    }


    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (visibleTitle) {
                startAlphaAnimation(tvTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(tvPlace, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(ivBaseIcon, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                visibleTitle = false;
            }

        } else {
            if (!visibleTitle) {
                startAlphaAnimation(tvTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(tvPlace, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(ivBaseIcon, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                visibleTitle = true;
            }
        }
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }
}
