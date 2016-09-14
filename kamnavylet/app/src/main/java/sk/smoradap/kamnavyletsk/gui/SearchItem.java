package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.internal.Utils;
import sk.smoradap.kamnavyletsk.R;

/**
 * Created by smora on 14.09.2016.
 */
@EViewGroup(R.layout.search_item_layout)
public class SearchItem extends CardView{

    @ViewById(R.id.iv_base_icon)
    ImageView icon;

    @ViewById(R.id.tv_name_si)
    TextView name;

    @ViewById(R.id.tv_place)
    TextView place;

    @ViewById(R.id.distance_label)
    TextView distance_label;

    @ViewById(R.id.distance_info)
    TextView distance;

    @ViewById(R.id.category_label)
    TextView category;

    @ViewById(R.id.short_description_text_view)
    TextView briefDescription;

    @ViewById(R.id.view_to_move_right)
    View toMoveRight;

    public SearchItem(Context context) {
        super(context);
    }

    public SearchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initialSetup(){
        setBriefDescription(null);
        setDistance(null);
        setCategory(null);
        setIcon(null);
        setCardElevation(sk.smoradap.kamnavyletsk.utils.Utils.convertDpToPixel(4));
//        setPadding(sk.smoradap.kamnavyletsk.utils.Utils.convertDpToPixel(8f),
//                sk.smoradap.kamnavyletsk.utils.Utils.convertDpToPixel(8f),
//                sk.smoradap.kamnavyletsk.utils.Utils.convertDpToPixel(8f),
//                sk.smoradap.kamnavyletsk.utils.Utils.convertDpToPixel(8f));

        setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    public void setName(String text){
        name.setText(text);
    }

    public void setPlace(String text){
        place.setText(text);
    }

    public void setIcon(String url){
        if(url == null){
            icon.setVisibility(View.GONE);
        } else {
            icon.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(url)
                    .bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(icon);
        }
    }

    public void setCategory(String text){
        if(text == null || text.isEmpty()){
            category.setText(null);
            category.setVisibility(View.GONE);
            toMoveRight.setVisibility(View.GONE);
        } else {
            category.setVisibility(View.VISIBLE);
            category.setText(text);
            toMoveRight.setVisibility(View.VISIBLE);
        }
    }

    public void setDistance(String text){
        if(text == null || text.isEmpty()){
            distance.setText(null);
            distance.setVisibility(View.GONE);
            distance_label.setVisibility(View.GONE);
        } else {
            distance.setVisibility(View.VISIBLE);
            distance.setText(text);
            distance_label.setVisibility(View.VISIBLE);
        }
    }

    public void setBriefDescription(String text){
        if(text == null || text.isEmpty()){
            briefDescription.setText(null);
            briefDescription.setVisibility(View.GONE);
        } else {
            briefDescription.setVisibility(View.VISIBLE);
            briefDescription.setText(text);
        }
    }
}
