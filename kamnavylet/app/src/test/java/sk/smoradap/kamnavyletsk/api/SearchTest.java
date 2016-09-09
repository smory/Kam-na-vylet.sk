package sk.smoradap.kamnavyletsk.api;

import org.junit.Test;
import sk.smoradap.kamnavyletsk.model.Category;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smora on 01.09.2016.
 */
public class SearchTest {

    private static final String SEARCH_STRING = "Hnilec";
    private static final String SEARCH_STRING2 = "Stara lubovna";
    private static final String SEARCH_STRING_NO_RESULTS = "blald";



    @Test
    public void testSearch() throws IOException{
        Category category = new Category();
        category.setUrlString("kupanie");

        List<SearchResult> list1 = SearchProvider.search(SEARCH_STRING, 10, null);
        List<SearchResult> list2 = SearchProvider.search(SEARCH_STRING2, 15, null);
        List<SearchResult> list3 = SearchProvider.search(SEARCH_STRING2, 15, category);

        assertNotNull("Search result should not be null", list1);
        assertTrue("Search reasults should not be empty" , list1.size() > 0);
        assertNotNull("Search result should not be null", list2);
        assertTrue("Search reasults should not be empty" , list2.size() > 0);
        assertTrue("Filtered searched should be less than not filtered", list2.size() > list3.size());
    }

    @Test
    public void testSearchNoResults() throws IOException{
        assertNull("Search should be null", SearchProvider.search(SEARCH_STRING_NO_RESULTS, 10, null));
    }



    @Test
    public void testPartialReturnedResults()throws IOException {

        TestListener callback = new TestListener();
        SearchProvider.searchWithPartialResultDelivery(SEARCH_STRING2, 25, null, callback);
        assertTrue("Number of iterations should be the same as total chunks", callback.iliterations == callback.numberOfChunks);
    }

    static class TestListener implements SearchProvider.OnDataChunkAvailableListener {

        int numberOfChunks = 0;
        int iliterations = 0;

        @Override
        public void DataChunkAvailable(List<SearchResult> chunk, int chunkNumber, int numberOfChunks) {
            this.numberOfChunks = numberOfChunks;
            iliterations++;
            System.out.println("iteration: " + iliterations);
        }
    }

}
