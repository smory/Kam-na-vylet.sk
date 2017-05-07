package sk.smoradap.kamnavyletsk.api;

import org.androidannotations.annotations.EBean;

import sk.smoradap.kamnavyletsk.model.Area;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.Category;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for easy access to kamnavylet.sk database
 * Created by Peter Smorada on 01.09.2016.
 */
@EBean
@SuppressWarnings("PMD.GuardLogStatementJavaUtil")
public class KamNaVyletApi {

    private static final Logger LOGGER = Logger.getLogger(KamNaVyletApi.class.getName());

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

    public List<SearchResult> search(final String place, final int distance, final Category category){
        try {
            return SearchProvider.search(place, distance, category);
        } catch (IOException e) {
            LOGGER.warning("Faild to perform search: " + e);
            return null;
        }
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

    public AttractionDetails loadDetails(final String url){
        try {
            return DetailsProvider.details(url);
        } catch (IOException e) {
            LOGGER.warning("Failed to load details:" + e);
            return null;
        }

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

    public List<Area> loadAreas(){
        return AreaProvider.loadAreas();
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

    public List<Category> loadCategories(){
        return CategoryProvider.loadCategories();
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
