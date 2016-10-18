package sk.smoradap.kamnavyletsk.api;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import sk.smoradap.kamnavyletsk.model.AttractionDetails;
import sk.smoradap.kamnavyletsk.model.SearchResult;

import static org.junit.Assert.assertNotNull;

/**
 * Created by psmorada on 18.10.2016.
 */
public class LocalDbBuildTest {

    @Ignore("time consuming")
    @Test
    public void testWholeDatabase()throws IOException {
        int i = 0;
        List<SearchResult> list = SearchProvider.search("hnilec", 400, null);
        PrintWriter wr = new PrintWriter(new File("db.txt"));
        for(SearchResult sr : list){
            AttractionDetails ad = DetailsProvider.details(sr.getDescriptionUrl());
            try{
                String s = ad.getName() + "\t" + ad.getTown() + "\t" + ad.getDistrict() + "\t" + ad.getArea() + "\t";
                s += ad.getRegion() + "\t" + ad.getGps() + "\t" + ad.getSourceUrl() + "\t" + sr.getPreviewImageUlr() + "\t";
                s +=  ad.getCategory();
                wr.println(s);
            } catch(Exception e){
                e.printStackTrace();
                i++;
                continue;
            }
            assertNotNull("Details should not be null" , DetailsProvider.details(sr.getDescriptionUrl()));
        }
        wr.flush();
        wr.close();
        System.out.println("number of errors: " + i);
    }
}
