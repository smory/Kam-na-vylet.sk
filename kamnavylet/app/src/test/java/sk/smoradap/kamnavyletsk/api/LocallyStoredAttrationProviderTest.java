package sk.smoradap.kamnavyletsk.api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import sk.smoradap.kamnavyletsk.model.Attraction;

import static org.junit.Assert.*;

/**
 * Created by psmorada on 19.10.2016.
 */
public class LocallyStoredAttrationProviderTest {

    private static String testString = "AquaCity Poprad\tPoprad\tPoprad\tPrešovský\tVysoké Tatry, Belianske Tatry, Roháče\tN49.05993, E20.30761\thttp://kamnavylet.sk/atrakcia/aquacity-poprad-poprad\thttp://kamnavylet.sk/images/goals/2/small_190.jpg?1232295657\tKúpanie";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testcreateAttraction(){
        Attraction a = new Attraction();
        a.setName("AquaCity Poprad");
        a.setTown("Poprad");
        a.setDistrict("Poprad");
        a.setArea("Prešovský");
        a.setGps("N49.05993, E20.30761");
        a.setSourceUrl("http://kamnavylet.sk/atrakcia/aquacity-poprad-poprad");
        a.setPreviewImageUrl("http://kamnavylet.sk/images/goals/2/small_190.jpg?1232295657");
        a.setLatitude(49.05993f);
        a.setLongitude(20.30761f);
        a.setCategory("Kúpanie");
        assertEquals(LocallyStoredAttrationProvider.createAttraction(testString), a);


    }

    @Test
    public void testBuild() throws Exception {
        File f = new File("src/main/res/raw/db");
        List<Attraction> list = LocallyStoredAttrationProvider.build(f);
        assertTrue(list.size() > 900);
    }
}