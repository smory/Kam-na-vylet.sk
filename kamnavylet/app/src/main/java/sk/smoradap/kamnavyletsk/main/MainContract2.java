package sk.smoradap.kamnavyletsk.main;

import java.util.List;

import sk.smoradap.kamnavyletsk.api.model.SearchResult;
import sk.smoradap.kamnavyletsk.base.BaseContract;
import sk.smoradap.kamnavyletsk.suggestions.model.Suggestion;

/**
 * Created by Peter Smorada on 13.5.2017.
 */

public interface MainContract2 {

    interface SearchView extends BaseContract.View {
        void showSuggestions(List<Suggestion> suggestions);
         void clearQuery();
    }

    interface ListView extends BaseContract.View {
        void showAttractions(List<SearchResult> results);
    }

    interface Presenter extends BaseContract.Presenter {
        void onSearchQueryChanged(String query);
        void searchQueryConfirmed(String query);
        void setSearchView(SearchView searchView);
        void setListView(ListView listView);
        void searchForCurrentLocation();
    }
}
