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
import java.util.logging.Logger;

/**
 * Class to load areas
 * Created by Peter Smoarada on 02.09.2016.
 */
class AreaProvider {

    private static final String BASE_URL = "http://kamnavylet.sk";
    private static final Logger LOGGER = Logger.getLogger(AreaProvider.class.getName());

    static List<Area> loadAreas(){

        Document document;
        try {
            document = Jsoup.connect(BASE_URL).get();
        } catch (IOException e){
            LOGGER.warning("Could not load areas: " + e); //NOPMD
            return null;
        }

        return parseAreas(document);
    }

    private static List<Area> parseAreas(Document document){
        List<Area> areas = new LinkedList<>();
        Elements a = document.select("table#rlist td");
        LOGGER.fine(a.toString());
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
        LOGGER.fine(areas.toString());
        return areas;
    }
}
