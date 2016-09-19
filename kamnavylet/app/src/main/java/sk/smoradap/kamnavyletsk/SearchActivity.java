package sk.smoradap.kamnavyletsk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.gui.SearchRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.SearchResult;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements KamNaVyletApi.OnSearchResultsListener {

    public static final String SEARCH_TERM = "term";

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.search_recycler)
    RecyclerView mRecyclerView;

    @Bean
    KamNaVyletApi api;


    @AfterViews
    void setFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @AfterViews
    public void performSearch(){
        Intent i = getIntent();
        api.search(i.getStringExtra(SEARCH_TERM), 15, null, this);
    }

    @Override
    @UiThread
    public void onSearchResults(List<SearchResult> results) {
        SearchRecyclerAdapter adapter = new SearchRecyclerAdapter(this, results);
        mRecyclerView.setAdapter(adapter);
        System.out.println("Setting adapter");
    }

    @Override
    public void onSearchFailure(Exception e) {

    }
}
