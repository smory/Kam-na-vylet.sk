package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import sk.smoradap.kamnavyletsk.R;

/**
 * Created by Peter Smorada on 14.09.2016.
 */
@EViewGroup(R.layout.item_layout)
public class ItemView extends LinearLayout{

    @ViewById(R.id.iv_base_icon)
    ImageView icon;

    @ViewById(R.id.tv_name_si)
    TextView name;

    @ViewById(R.id.tv_place)
    TextView place;


    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initialSetup(){
        setIcon(null);
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

}
