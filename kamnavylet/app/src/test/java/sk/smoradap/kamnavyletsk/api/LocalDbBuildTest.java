package sk.smoradap.kamnavyletsk.api;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.SearchResult;
import sk.smoradap.kamnavyletsk.utils.Utils;

import static org.junit.Assert.assertNotNull;

/**
 * Class to create a local databases. Should be moved out of test cases in future...
 * Created by psmorada on 18.10.2016.
 */
public class LocalDbBuildTest {


    @Ignore("time consuming")
    @Test
    public void buildAttractionDatabaseAndPreditions()throws IOException {
        int i = 0;
        List<SearchResult> list = SearchProvider.search("poprad", 400, null);
        PrintWriter wr = new PrintWriter(new File("db.txt"));
        TreeMap<String, String> map = new TreeMap<>();

        StringBuilder s = new StringBuilder();
        for(SearchResult sr : list){
            AttractionDetails ad = DetailsProvider.details(sr.getSourceUrl());
            try{
                s.setLength(0);
                s.append(ad.getName() + "\t");
                s.append(ad.getTown() + "\t");
                s.append(ad.getDistrict() + "\t");
                s.append(ad.getArea() + "\t");
                s.append(ad.getRegion() + "\t");
                s.append(ad.getGps() + "\t");
                s.append(ad.getSourceUrl() + "\t");
                s.append(sr.getPreviewImageUrl() + "\t");
                s.append(ad.getCategory());
                wr.println(s);

                map.put(Utils.stripAccents(ad.getName()).toLowerCase(), ad.getName());
                map.put(Utils.stripAccents(ad.getTown()).toLowerCase(), ad.getTown());
                map.put(Utils.stripAccents(ad.getDistrict()).toLowerCase(), ad.getDistrict());
                map.put(Utils.stripAccents(ad.getArea()).toLowerCase(), ad.getRegion());
                String regions[] = ad.getRegion().split(",");

                for(String region : regions){
                    map.put(Utils.stripAccents(region.trim()).toLowerCase(), region.trim());
                }

            } catch(Exception e){
                e.printStackTrace();
                i++;
                continue;
            }
        }
        wr.flush();
        wr.close();

        wr = new PrintWriter(new File("sug.txt"));
        for(String key : map.keySet()){
            wr.println(key + "\t" + map.get(key));
        }
        wr.flush();
        wr.close();

        System.out.println("number of errors: " + i);
    }

}
