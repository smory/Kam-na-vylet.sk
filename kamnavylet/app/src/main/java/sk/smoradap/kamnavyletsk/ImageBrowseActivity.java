package sk.smoradap.kamnavyletsk;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.viewpagerindicator.CirclePageIndicator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import sk.smoradap.kamnavyletsk.gui.ImageBrowsePageAdapter;

@EActivity(R.layout.activity_image_browse)
@Fullscreen
public class ImageBrowseActivity extends AppCompatActivity {

    public static final String PAGER_POSITION = "position";
    public static final String IMAGE_URLS = "urls";

    @ViewById(R.id.image_browser_pager)
    ViewPager mPager;

    @ViewById(R.id.titles)
    CirclePageIndicator mIndicator;

    @AfterViews
    void initiatePagger(){
        Intent i = getIntent();
        int posiotion = i.getIntExtra(PAGER_POSITION, 0);
        List<String>  l = (List) i.getSerializableExtra(IMAGE_URLS);

        ImageBrowsePageAdapter adapter = new ImageBrowsePageAdapter(l, this);
        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager);
        mPager.setCurrentItem(posiotion);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed()){
            Glide.get(this).clearMemory();
        }
    }

}
