package sk.smoradap.kamnavyletsk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
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

    @ViewById(R.id.progressLayout)
    RelativeLayout mProgressLayout;

    @Bean
    KamNaVyletApi api;

    @Extra(SEARCH_TERM)
    String mSearchTerm;


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
        api.search(mSearchTerm, 15, null, this);
    }

    @AfterViews
    public void setUpToolbar(){
        mToolbar.setTitle(mSearchTerm);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mProgressLayout.setVisibility(View.VISIBLE);
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simple_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQuery(mSearchTerm, false);
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchTerm = query;
                performSearch();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    @UiThread
    public void onSearchResults(List<SearchResult> results) {
        SearchRecyclerAdapter adapter = new SearchRecyclerAdapter(this, results);
        mRecyclerView.setAdapter(adapter);
        System.out.println("Setting adapter");
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onSearchFailure(Exception e) {

    }
}
