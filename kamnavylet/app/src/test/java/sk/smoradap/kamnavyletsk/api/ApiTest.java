package sk.smoradap.kamnavyletsk.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by smora on 02.09.2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DetailsTest.class,
        SearchTest.class,
        AreaTest.class,
        CategoryTest.class
})
public class ApiTest {
}
