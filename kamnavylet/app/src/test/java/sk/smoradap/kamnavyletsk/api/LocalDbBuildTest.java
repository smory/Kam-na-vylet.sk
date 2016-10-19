package sk.smoradap.kamnavyletsk.api;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
        TreeSet<String> set = new TreeSet<>();

        StringBuilder s = new StringBuilder();
        for(SearchResult sr : list){
            AttractionDetails ad = DetailsProvider.details(sr.getDescriptionUrl());
            try{
                s.setLength(0);
                s.append(ad.getName() + "\t");
                s.append(ad.getTown() + "\t");
                s.append(ad.getDistrict() + "\t");
                s.append(ad.getArea() + "\t");
                s.append(ad.getRegion() + "\t");
                s.append(ad.getGps() + "\t");
                s.append(ad.getSourceUrl() + "\t");
                s.append(sr.getPreviewImageUlr() + "\t");
                s.append(ad.getCategory());
                wr.println(s);

                set.add(ad.getName());
                set.add(ad.getTown());
                set.add(ad.getDistrict());
                set.add(ad.getArea());
                String regions[] = ad.getRegion().split(",");

                for(String region : regions){
                    set.add(region.trim());
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
        for(String suggestion : set){
            wr.println(Utils.stripAccents(suggestion).toLowerCase() + "\t" + suggestion);
        }
        wr.flush();
        wr.close();

        System.out.println("number of errors: " + i);
    }

}
