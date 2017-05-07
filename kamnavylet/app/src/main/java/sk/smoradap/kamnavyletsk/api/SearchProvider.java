package sk.smoradap.kamnavyletsk.api;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sk.smoradap.kamnavyletsk.model.Category;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class provides easier access to search capabilities.
 * Created by Peter Smorada on 01.09.2016.
 */
@SuppressWarnings("PMD.GuardLogStatementJavaUtil")
public class SearchProvider {
    public static final String SEARCH_URL = "http://www.kamnavylet.sk/atrakcie/";
    public static final String BASE_URL = "http://kamnavylet.sk";

    private static final Logger LOGGER = Logger.getLogger(SearchProvider.class.getName());

    public static List<SearchResult> search(String query, int distance, Category category) throws IOException{
        List<SearchResult> results = search(query, distance, category, false);
        if(results == null || results.size() == 0){
            results = search(query, distance, category, true);
        }
        return results;
     }

    private static List<SearchResult> search(String query, int distance, Category category,
                                             boolean alternativeUrl) throws IOException{
        Document document;
        try {
            document = Jsoup.connect(alternativeUrl?
                    createAlternativeSearchUrl(query, distance, 0):
                    createSearchUrl(query, distance, 0, category)).get();
        } catch (HttpStatusException se){
            return null;
        } catch (IOException e) {
            LOGGER.warning("Unable to load data: " + e); //NOPMD
            throw e;
        }

        List<SearchResult> results = processResponce(document);

        int numberOfPages = nuberOfPagesWithResults(document);
        LOGGER.fine("number of pages: " + numberOfPages);
        for(int i = 2; i <= numberOfPages; i++){
            try {
                document = Jsoup.connect( alternativeUrl?
                        createAlternativeSearchUrl(query, distance,i):
                        createSearchUrl(query, distance, i, category)).get();
            } catch (IOException e) {
                LOGGER.warning("Unable to load data: " + e);
                continue;
            }
            List<SearchResult> temp = processResponce(document);
            results.addAll(temp);

        }
        LOGGER.fine("number of results: " + results.size());
        return results;

    }

    public static void searchWithPartialResultDelivery(String query, int distance, Category category,
                                            OnDataChunkAvailableListener callback) throws IOException{
        Document document;
        try {
            document = Jsoup.connect(createSearchUrl(query, distance, 0, category)).get();
        } catch (HttpStatusException se){
            return;
        } catch (IOException e) {
            LOGGER.warning("Unable to load data: " + e);
            throw e;
        }

        List<SearchResult> results = processResponce(document);
        int numberOfPages = nuberOfPagesWithResults(document);
        callback.onDataChunkAvailable(results, 1, numberOfPages);

        LOGGER.fine("number of pages: " + numberOfPages);
        for(int i = 2; i <= numberOfPages; i++){
            try {
                document = Jsoup.connect(createSearchUrl(query, distance, i, category)).get();
            } catch (IOException e) {
                LOGGER.warning("Unable to load data: " + e);
                continue;
            }
            List<SearchResult> temp = processResponce(document);
            callback.onDataChunkAvailable(temp, i, numberOfPages);

        }
    }

    private static String createSearchUrl(String query, int distance, int pageNumber, Category category){
        StringBuilder url = new StringBuilder(SEARCH_URL);
        url.append(category == null? "" : category.getUrlString() + "/");
        try {
            url.append(URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.warning("Unsuported encoding: " + e);
            url.append(query);
        }
        url.append("?o=")
                .append(distance)
                .append(pageNumber > 0? "&page=" + pageNumber : "");
        LOGGER.fine(url.toString());
        return url.toString();
    }

    private static String createAlternativeSearchUrl(String query, int distance, int pageNumber){
        StringBuilder url = new StringBuilder(BASE_URL);
        url.append("/search?q=");
        try {
            url.append(URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.warning("Unsuported encoding: " + e);
            url.append(query);
        }
        url.append(pageNumber > 0? "&page=" + pageNumber : "")
                .append("&x=0&y=0");
        LOGGER.fine(url.toString());
        return url.toString();
    }

    private static int nuberOfPagesWithResults(Document document){
        Elements pages = document.select("center div.flickr_pagination a:not(.next_page)");
        LOGGER.fine(pages.toString());
        if (pages.isEmpty()){
            return 1;
        } else {
            return Integer.parseInt(pages.last().text());
        }
    }

    private static List<SearchResult> processResponce(Document document){
        Elements searchResults = document.select("div.goal-preview");
        LinkedList<SearchResult> results = new LinkedList<>();
        for(Element result : searchResults){
            SearchResult sr = parseResult(result);
            LOGGER.fine(sr.toString());
            results.add(sr);
        }

        return results;
    }

    private static SearchResult parseResult(Element goalPreview){
        SearchResult sr = new SearchResult();

        String name = getName(goalPreview);
        String desc = getDescription(goalPreview);
        String url = getUrl(goalPreview);
        String town = getTown(goalPreview);
        String smallPictureUrl = getPreviewImageUrl(goalPreview);
        String largePictureUrl = getLargePreviewPictureUrl(smallPictureUrl);
        String distance = getDistance(goalPreview);

        sr.setBriefDescription(desc);
        sr.setName(name);
        sr.setTown(town);
        sr.setSourceUrl(url);
        sr.setPreviewImageUrl(smallPictureUrl);
        sr.setFullImageUrl(largePictureUrl);
        sr.setDistance(distance);

        return sr;

    }

    private static String getDistance(Element goalPreview){
        try {
            return goalPreview.getElementsByClass("di").get(0).text();
        }catch (Exception e){
              return null;
        }
    }

    private static String getPreviewImageUrl(Element goalPreview){
        Elements images = goalPreview.select("a img");
        return BASE_URL + images.get(0).attr("src");

    }

    private static String getLargePreviewPictureUrl(String previewUrl){
        return previewUrl.replace("small", "large");
    }

    private static String getTown(Element goalPreview){
        Elements e = goalPreview.select("div.w div div:not(.t,.c)");
        return e.get(0).text();
    }

    private static String getUrl(Element goalPreview){
        Element e = goalPreview.select("div.w div.d a").get(0);
        return BASE_URL + e.attr("href");
    }

    private static String getDescription(Element goalPreview){
        return goalPreview.select("div.w div.h").get(0).text();

    }

    private static String getName(Element goalPreview){
        return goalPreview.select("div.w div div.t h2 span").get(0).text();
    }

    public interface OnDataChunkAvailableListener {
        void onDataChunkAvailable(List<SearchResult> chunk, int chunkNumber, int numberOfChunks);
    }

}
