package sk.smoradap.kamnavyletsk.api;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.NearbyAttraction;

import java.io.IOException;

/**
 * Created by smora on 01.09.2016.
 */
public class DetailsProvider {

    public static String BASE_URL = "http://kamnavylet.sk";

    public static AttractionDetails details(String url) throws IOException{

        AttractionDetails ad = new AttractionDetails();
        ad.setSourceUrl(url);

        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch(HttpStatusException se){
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        setLocationDetails(ad, doc);
        setDescription(ad, doc);
        setImageUrls(ad, doc);
        setNearbyAttractions(ad, doc);

        return ad;
    }

    private static void setNearbyAttractions(AttractionDetails ad, Document doc){
        Element nearest = doc.getElementById("nearest");
        Elements links = nearest.select("a");
        Elements distances = nearest.select("b");

        for(int i = 0; i < links.size(); i++){
            NearbyAttraction nearbyAttraction = new NearbyAttraction();
            nearbyAttraction.setName((links.get(i).text()));
            nearbyAttraction.setUrl(BASE_URL + links.get(i).attr("href"));
            nearbyAttraction.setDistance(distances.get(i).text());
            ad.addNearbyAttraction(nearbyAttraction);
        }
    }

    private static void setImageUrls(AttractionDetails ad, Document doc){


        Elements elements = doc.select("div.ib a");
        for(Element el : elements){
            ad.addImageUrl(BASE_URL + el.attr("href"));
            System.out.println(el.attr("href"));
        }

        if(ad.getImageUrls().size() == 0){
            Element e = doc.getElementById("lblink");
            if(e != null){
                ad.addImageUrl(BASE_URL + e.attr("href"));
            }
        }
    }

    private static void setDescription(AttractionDetails ad, Document doc){
        Elements ele = doc.select("div#goal-desc p");
        String desc = "";
        for(Element e : ele){
            desc += e.toString();
        }
        desc = desc.replaceFirst("<p>", "");
        desc = desc.replaceAll("<p>", "<br/><br/>");
        desc = desc.replaceAll("<\\s*/p>", "").trim();

        System.out.println(desc);
        ad.setDescription(desc);
    }

    private static void setLocationDetails(AttractionDetails ad, Document doc){

        Elements elements = doc.select("div.lh div.onerow");
        System.out.println(elements);
        for(Element e : elements){
            String info = e.select("b").get(0).text();
            Elements forValue = e.select("span");
            String value = forValue.size( )> 0? forValue.get(0).text(): null;
            //System.out.println(info);
            //System.out.println(value);
            switch (info){
                case "Kategória:":
                    ad.setCategory(value);
                    break;
                case "Názov:":
                    ad.setName(value);
                    break;
                case "Región:":
                    ad.setRegion(value);
                    break;
                case "Kraj:":
                    ad.setArea(value);
                    break;
                case "Okres:":
                    ad.setDistrict(value);
                    break;
                case "Obec:":
                    ad.setTown(value);
                    break;
                case "Adresa:":
                    ad.setEmail(value);
                    break;
                case "GPS:":
                    value = value.replace(" mapa", "");
                    ad.setGps(value);
                    break;
                case "Telefón:":
                    //System.out.println(e.select("div#phone").get(0).text());
                    continue;
                case "WWW:":
                    System.out.println(e.select("a").get(0).attr("href"));
                    ad.setWebSite(e.select("a").get(0).attr("href"));
            }
            ad.addDetailsPair(info, value);
        }


    }
}
