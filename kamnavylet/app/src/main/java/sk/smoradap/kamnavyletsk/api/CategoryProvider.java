package sk.smoradap.kamnavyletsk.api;

import org.jsoup.select.Elements;
import sk.smoradap.kamnavyletsk.model.Category;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class to provide cathegories
 * Created by Peter Smorada on 02.09.2016.
 */
class CategoryProvider {

    private static final String BASE_URL = "http://kamnavylet.sk";
    private static final Logger LOGGER = Logger.getLogger(CategoryProvider.class.getName());

    static List<Category> loadCategories(){

        Document document;
        try{
            document = Jsoup.connect(BASE_URL).get();
        } catch (IOException e){
             return null;
        }

        return parseCategories(document);
    }

    private static List<Category> parseCategories(Document document){
        List<Category> list = new LinkedList<>();

        Elements cat = document.select("div#tc1 div a");
        for(Element c : cat){
            Category category = new Category();
            category.setDisplayName(c.text());
            String categoryUrlPart = c.attr("href");
            category.setCategoryUrl(BASE_URL + categoryUrlPart);
            category.setUrlString(categoryUrlPart.replace("/atrakcie/", ""));
            list.add(category);
        }
        LOGGER.fine(list.toString());
        return list;
    }
}
