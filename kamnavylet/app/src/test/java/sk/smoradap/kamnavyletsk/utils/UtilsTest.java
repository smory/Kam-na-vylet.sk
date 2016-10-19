package sk.smoradap.kamnavyletsk.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by psmorada on 19.10.2016.
 */
public class UtilsTest {

    @Test
    public void testStripAccents() throws Exception {
        String testString = "ľščťžýáíúäňô";
        String expectedResult = "lsctzyaiuano";
        assertTrue(expectedResult.equals(Utils.stripAccents(testString)));
    }
}