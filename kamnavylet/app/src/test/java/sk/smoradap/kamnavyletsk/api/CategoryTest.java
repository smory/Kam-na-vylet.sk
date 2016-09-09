package sk.smoradap.kamnavyletsk.api;

import org.junit.Test;
import sk.smoradap.kamnavyletsk.model.Category;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smora on 02.09.2016.
 */
public class CategoryTest {

    @Test
    public void loadCategoriesTest(){
        List<Category> list = CategoryProvider.loadCategories();
        assertNotNull("Categories should not be null", list);
        assertTrue("Categories should not be empty", list.size() > 0);
    }
}
