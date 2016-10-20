package sk.smoradap.kamnavyletsk;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.RawRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
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
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.details.DetailsActivity;
import sk.smoradap.kamnavyletsk.details.DetailsActivity_;
import sk.smoradap.kamnavyletsk.gui.ItemRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.Item;
import sk.smoradap.kamnavyletsk.model.SearchResult;
import sk.smoradap.kamnavyletsk.utils.Utils;

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

    private SimpleCursorAdapter mSugestionAdapter;
    private String[] suggestions;


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
        loadSuggestions();
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
                if(suggestions != null){
                    populateAdapter(newText);
                }
                return false;
            }

        });

        createSuggestionsAdapter();
        searchView.setSuggestionsAdapter(mSugestionAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor c = (Cursor) mSugestionAdapter.getItem(position);
                System.out.println(c.getString(1));
                mSearchTerm = c.getString(1);
                searchView.setQuery(c.getString(1), true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                return true;
            }
        });

        return true;
    }

    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "name" });
        for (int i=0; i<suggestions.length; i++) {
            if (suggestions[i].toLowerCase().startsWith(query.toLowerCase())) {
                c.addRow(new Object[]{i, suggestions[i]});
                System.out.println("Adding row: " + i + ", " + suggestions[i]);
            }
        }
        mSugestionAdapter.changeCursor(c);
    }

    @Background
    void loadSuggestions(){
        suggestions = Utils.loadRawTextResourceAsArray(this, R.raw.suggestions);
    }

    void createSuggestionsAdapter(){
        final String[] from = new String[] {"name"};
        final int[] to = new int[] {android.R.id.text1};
        mSugestionAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    @UiThread
    public void onSearchResults(List<SearchResult> results) {
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(this, results, new ItemRecyclerAdapter.OnItemPickedListener() {
            @Override
            public void onItemPicked(Item item) {
                showAttractionDetails(item.getSourceUrl());
            }
        });
        mRecyclerView.setAdapter(adapter);
        System.out.println("Setting adapter");
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onSearchFailure(Exception e) {

    }

    @UiThread
    public void showAttractionDetails(String url){
        Intent i = new Intent(this, DetailsActivity_.class);
        i.putExtra(DetailsActivity.URL, url);
        startActivity(i);
    }
}
