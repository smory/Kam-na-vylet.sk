package sk.smoradap.kamnavyletsk.api;

import org.androidannotations.annotations.EBean;

import sk.smoradap.kamnavyletsk.model.Area;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.Category;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by smora on 01.09.2016.
 */
@EBean
public class KamNaVyletApi {

    public void search(final String place, final int distance, final Category category,
                               final OnSearchResultsListener callback){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onSearchResults(SearchProvider.search(place, distance, category));
                } catch (IOException e) {
                    callback.onSearchFailure(e);
                }
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    public void loadDetails(final String url, final OnDetailsListener callback){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    callback.onDetailsLoaded(DetailsProvider.details(url));
                } catch (IOException e) {
                    callback.onDetailsFailure(e);
                }
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    public void loadAreas(final OnAreaAvailableListener callback){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                callback.onAreaAvailable(AreaProvider.loadAreas());
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    public void loadCategories(final OnCategoryAvailableListener callback){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                callback.onCategoryAvailable(CategoryProvider.loadCategories());
            }
        };
        Thread thread = new Thread(r);
        thread.start();
    }

    public interface OnSearchResultsListener{
        void onSearchResults(List<SearchResult> results);
        void onSearchFailure(Exception e);
    }

    public interface OnDetailsListener{
        void onDetailsLoaded(AttractionDetails details);
        void onDetailsFailure(IOException e);
    }

    public interface OnAreaAvailableListener{
        void onAreaAvailable(List<Area> areas);
    }

    public interface OnCategoryAvailableListener{
        void onCategoryAvailable(List<Category> categories);
    }
}
