package sk.smoradap.kamnavyletsk.api;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by smora on 02.09.2016.
 */
public class AreaTest {
   @Test
    public void testAreas(){
       assertNotNull("Areas should not be null", AreaProvider.loadAreas());

    }
}
