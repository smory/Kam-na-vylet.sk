package sk.smoradap.kamnavyletsk.api;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sk.smoradap.kamnavyletsk.model.Category;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by smora on 01.09.2016.
 */
public class SearchProvider {
    public static String SEARCH_URL = "http://www.kamnavylet.sk/atrakcie/";
    public static String BASE_URL = "http://kamnavylet.sk";

    public static List<SearchResult> search(String query, int distance, Category category) throws IOException{
        Document document = null;
        try {
            document = Jsoup.connect(createSearchUrl(query, distance, 0, category)).get();
        } catch (HttpStatusException se){
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        List<SearchResult> results = processResponce(document);

        int numberOfPages = nuberOfPagesWithResults(document);
        System.out.println("number of pages: " + numberOfPages);
        for(int i = 2; i <= numberOfPages; i++){
            try {
                document = Jsoup.connect(createSearchUrl(query, distance, i, category)).get();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            List<SearchResult> temp = processResponce(document);
            results.addAll(temp);

        }
        System.out.println("number of results: " + results.size());
        return results;

    }

    public static void searchWithPartialResultDelivery(String query, int distance, Category category,
                                            OnDataChunkAvailableListener callback) throws IOException{
        Document document = null;
        try {
            document = Jsoup.connect(createSearchUrl(query, distance, 0, category)).get();
        } catch (HttpStatusException se){
            return;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        List<SearchResult> results = processResponce(document);
        int numberOfPages = nuberOfPagesWithResults(document);
        callback.DataChunkAvailable(results, 1, numberOfPages);

        System.out.println("number of pages: " + numberOfPages);
        for(int i = 2; i <= numberOfPages; i++){
            try {
                document = Jsoup.connect(createSearchUrl(query, distance, i, category)).get();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            List<SearchResult> temp = processResponce(document);
            callback.DataChunkAvailable(temp, i, numberOfPages);

        }
    }

    private static String createSearchUrl(String query, int distance, int pageNumber, Category category){
        StringBuilder url = new StringBuilder(SEARCH_URL);
        url.append(category == null? "" : category.getUrlString() + "/");
        url.append(query);
        url.append("?o=" + distance);
        url.append(pageNumber > 0? "&page=" + pageNumber : "");
        System.out.println(url);
        return url.toString();
    }

    private static int nuberOfPagesWithResults(Document document){
        Elements pages = document.select("center div.flickr_pagination a:not(.next_page)");
        System.out.println(pages);
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
            System.out.println(sr);
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
        sr.setDescriptionUrl(url);
        sr.setPreviewImageUlr(smallPictureUrl);
        sr.setFullImageUrl(largePictureUrl);
        sr.setDistance(distance);

        return sr;

    }

    private static String getDistance(Element goalPreview){
        try {
            return goalPreview.getElementsByClass("di").get(0).text();
        }catch (Exception e){
            //e.printStackTrace();
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
        public void DataChunkAvailable(List<SearchResult> chunk, int chunkNumber, int numberOfChunks);
    }

}
