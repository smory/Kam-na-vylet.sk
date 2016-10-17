package sk.smoradap.kamnavyletsk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.SearchActivity_;

public class MainActivity extends AppCompatActivity {

    private MainViewFragment mFragment;
    private LinearLayout mFragmentHolderLayout;


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

        mFragmentHolderLayout = (LinearLayout) findViewById(R.id.recycler_holder);
        mFragment = new MainViewFragment_();

        MainContract.Presenter presenter = new MainPresenter(mFragment);
        presenter.start();

    }


}
