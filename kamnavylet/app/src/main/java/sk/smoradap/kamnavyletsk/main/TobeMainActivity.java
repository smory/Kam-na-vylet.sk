package sk.smoradap.kamnavyletsk.main;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import sk.smoradap.kamnavyletsk.base.BaseActivity;
import sk.smoradap.kamnavyletsk.base.BaseContract;
import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.details.DetailsActivity_;
import sk.smoradap.kamnavyletsk.gui.ItemRecyclerAdapter;
import sk.smoradap.kamnavyletsk.model.Item;
import sk.smoradap.kamnavyletsk.model.SearchResult;
import sk.smoradap.kamnavyletsk.suggestions.SimpleSuggestionProvider;
import sk.smoradap.kamnavyletsk.suggestions.SuggestionProvider;
import sk.smoradap.kamnavyletsk.suggestions.model.Suggestion;

/**
 * Created by Peter Smorada on 13.5.2017.
 */
@EActivity(R.layout.main_cont)
public class TobeMainActivity extends BaseActivity implements MainContract2.ListView,
        MainContract2.SearchView {

    public static final String TAG = TobeMainActivity.class.getSimpleName();

    @ViewById(R.id.rv_search_results)
    RecyclerView rvSearchResults;

    @ViewById(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;

    @Bean(SimpleSuggestionProvider.class)
    SuggestionProvider suggestionProvider;

    @Bean(MainPresenter2.class)
    MainContract2.Presenter presenter;

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @AfterViews
    void setupSearchView() {
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Log.v(TAG, "query change: " + newQuery);
                presenter.onSearchQueryChanged(newQuery);
            }
        });

        floatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView,
                                         SearchSuggestion item, int itemPosition) {
                if(item instanceof Suggestion) {
                    leftIcon.setImageResource(((Suggestion)item).getIcon());
                }
            }
        });

        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                onSearchAction(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                Log.d(TAG, String.format("Sending query for search: %s", currentQuery));
                presenter.searchQueryConfirmed(currentQuery);
            }
        });
    }

    @Override
    @UiThread
    public void showAttractions(List<SearchResult> results) {
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(this, results, new ItemRecyclerAdapter.OnItemPickedListener() {
            @Override
            public void onItemPicked(Item item) {
                DetailsActivity_.intent(TobeMainActivity.this)
                        .url(item.getSourceUrl())
                        .start();
            }
        });
        if(results == null){
            Log.v(TAG, "No results to show");
            return;
        }
        Log.v(TAG, "Number of results " + results.size());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration divider = new DividerItemDecoration(rvSearchResults.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_separator));
        rvSearchResults.addItemDecoration(divider);
        rvSearchResults.setLayoutManager(llm);
        rvSearchResults.setAdapter(adapter);
    }

    @AfterInject
    void setupPresenter(){
        presenter.setListView(this);
        presenter.setSearchView(this);
    }

    @Override
    @UiThread
    public void showSuggestions(List<Suggestion> suggestions) {
        floatingSearchView.swapSuggestions(suggestions);
    }


    @Override
    @UiThread
    public void clearQuery() {
        floatingSearchView.clearQuery();
    }
}
