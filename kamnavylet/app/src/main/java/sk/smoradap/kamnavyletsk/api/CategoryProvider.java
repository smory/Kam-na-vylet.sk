package sk.smoradap.kamnavyletsk.api;

import org.jsoup.select.Elements;
import sk.smoradap.kamnavyletsk.model.Category;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by smora on 02.09.2016.
 */
public class CategoryProvider {

    public static final String BASE_URL = "http://kamnavylet.sk";

    public static List<Category> loadCategories(){

        Document document = null;
        try{
            document = Jsoup.connect(BASE_URL).get();
        } catch (IOException e){
            e.printStackTrace();
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
        System.out.println(list);
        return list;
    }
}
