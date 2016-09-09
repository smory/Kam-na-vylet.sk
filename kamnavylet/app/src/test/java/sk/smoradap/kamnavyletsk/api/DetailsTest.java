package sk.smoradap.kamnavyletsk.api;

import org.junit.Ignore;
import org.junit.Test;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by smora on 01.09.2016.
 */
public class DetailsTest {

    public static final String TEST_URL = "http://www.kamnavylet.sk/atrakcia/bansky-skanzen-hnilcik";
    public static final String TEST_URL2 = "http://www.kamnavylet.sk/atrakcia/hrad-lubovna-stara-lubovna";
    public static final String TEST_URL3 = "http://www.kamnavylet.sk/atrakcia/hrad-lubovna-stara";


    @Test
    public void testDetails() throws IOException {
        assertNotNull("Details should be null", DetailsProvider.details(TEST_URL));
    }

    @Test
    public void testDetails1() throws IOException{
        assertNotNull("Details should be null", DetailsProvider.details(TEST_URL2));
    }

    @Test
    public void testDetailsNoResult() throws IOException{
        assertNull("Details should be null", DetailsProvider.details(TEST_URL3));
    }

    @Ignore("time consuming")
    @Test
    public void testWholeDatabase()throws IOException {
        List<SearchResult> list = SearchProvider.search("stara lubovna", 400, null);
        for(SearchResult sr : list){
            assertNotNull("Details should not be null" , DetailsProvider.details(sr.getDescriptionUrl()));
        }
    }
}
