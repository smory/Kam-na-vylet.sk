package sk.smoradap.kamnavyletsk.main;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import sk.smoradap.kamnavyletsk.api.KamNaVyletApi;
import sk.smoradap.kamnavyletsk.api.model.SearchResult;
import sk.smoradap.kamnavyletsk.base.KamNaVyletApiBean;
import sk.smoradap.kamnavyletsk.suggestions.SimpleSuggestionProvider;
import sk.smoradap.kamnavyletsk.suggestions.SuggestionProvider;

/**
 * Created by Peter Smorada on 13.5.2017.
 */
@EBean
public class MainPresenter2 implements MainContract2.Presenter {

    @RootContext
    Context context;

    @Bean(SimpleSuggestionProvider.class)
    SuggestionProvider suggestionProvider;

    @Bean
    KamNaVyletApiBean api;

    MainContract2.SearchView searchView;
    MainContract2.ListView listView;

    @Override
    public void onSearchQueryChanged(String query) {
        searchView.showSuggestions(suggestionProvider.findSuggestions(query));
    }

    @Override
    public void searchQueryConfirmed(String query) {
        api.search(query, 15, null, new KamNaVyletApi.OnSearchResultsListener(){

            @Override
            public void onSearchResults(List<SearchResult> results) {
                listView.showAttractions(results);
            }

            @Override
            public void onSearchFailure(Exception e) {

            }
        });
    }

    @Override
    public void setSearchView(MainContract2.SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public void setListView(MainContract2.ListView listView) {
        this.listView = listView;
    }

    @Override
    public void searchForCurrentLocation() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}
