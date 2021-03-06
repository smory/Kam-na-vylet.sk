package sk.smoradap.kamnavyletsk;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.api.model.BaseAttractionInfo;
import sk.smoradap.kamnavyletsk.api.model.SearchResult;
import sk.smoradap.kamnavyletsk.base.KamNaVyletApiBean;
import sk.smoradap.kamnavyletsk.details.DetailsActivity_;
import sk.smoradap.kamnavyletsk.gui.ItemRecyclerAdapter;
import sk.smoradap.kamnavyletsk.utils.SuggestionsUtils;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements KamNaVyletApi.OnSearchResultsListener {

    public static final String TAG = SearchActivity.class.getSimpleName();

    public static final String SEARCH_TERM = "term";

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.search_recycler)
    RecyclerView recyclerView;

    @ViewById(R.id.progressLayout)
    RelativeLayout mProgressLayout;

    @Bean
    KamNaVyletApiBean api;

    @Extra(SEARCH_TERM)
    String mSearchTerm;

    private SimpleCursorAdapter mSugestionAdapter;

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

    @AfterViews
    public void addRecyclerDecoration(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this,R .drawable.line_separator));
        recyclerView.addItemDecoration(dividerItemDecoration);
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
                if(newText.length() > 1){
                    MatrixCursor c = SuggestionsUtils.createSugestionCursor(getApplicationContext(), newText);
                    mSugestionAdapter.changeCursor(c);
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
                mSearchTerm = c.getString(1);
                searchView.setQuery(c.getString(1), true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });

        return true;
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
            public void onItemPicked(BaseAttractionInfo item) {
                showAttractionDetails(item.getSourceUrl());
            }
        });
        recyclerView.setAdapter(adapter);
        mProgressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onSearchFailure(Exception e) {

    }

    @UiThread
    public void showAttractionDetails(String url){
        DetailsActivity_.intent(this)
                .url(url)
                .start();
    }
}
