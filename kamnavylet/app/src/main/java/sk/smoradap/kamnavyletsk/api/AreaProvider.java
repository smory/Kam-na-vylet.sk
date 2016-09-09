package sk.smoradap.kamnavyletsk.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sk.smoradap.kamnavyletsk.model.Area;
import sk.smoradap.kamnavyletsk.model.District;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by smora on 02.09.2016.
 */
public class AreaProvider {

    public static final String BASE_URL = "http://kamnavylet.sk";

    public static List<Area> loadAreas(){

        Document document = null;

        try {
            document = Jsoup.connect(BASE_URL).get();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return parseAreas(document);
    }

    private static List<Area> parseAreas(Document document){
        List<Area> areas = new LinkedList<>();
        Elements a = document.select("table#rlist td");
        System.out.println(a);
        for(Element e : a){
            Area area = new Area();
            area.setName(e.getElementsByTag("h2").get(0).text());
            Elements dists = e.select("a");
            for(Element d : dists){
                District dist = new District();
                dist.setDisplayName(d.text());
                dist.setDetailsUrl(BASE_URL + d.attr("href"));
                area.addDistrict(dist);
            }
            areas.add(area);
        }
        System.out.println(areas);
        return areas;
    }
}
