package sk.smoradap.kamnavyletsk.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import sk.smoradap.kamnavyletsk.R;


public class MainActivity extends AppCompatActivity {

    private MainViewFragment mFragment;
    private FrameLayout mFragmentHolderLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFragmentHolderLayout = (FrameLayout) findViewById(R.id.recycler_holder);
        mFragment = new MainViewFragment_();

        // initialize presenter
        new MainPresenter(mFragment, getApplicationContext());

        getSupportFragmentManager().beginTransaction()
                .add(mFragmentHolderLayout.getId(), mFragment)
                .commit();

        ImageView image = (ImageView) findViewById(R.id.toolbar_image);
        Glide.with(this).load(R.drawable.foto).into(image);
    }


}
