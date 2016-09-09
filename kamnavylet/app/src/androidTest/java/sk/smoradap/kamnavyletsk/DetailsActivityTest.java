package sk.smoradap.kamnavyletsk;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.ApplicationTestCase;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by smora on 07.09.2016.
 */
@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<DetailsActivity> activityRule
            = new ActivityTestRule<>(DetailsActivity.class);

    @Test
    public void testIntent() {
        Intent intent = new Intent();
        intent.putExtra("your_key", "your_value");

        //activityRule.launchActivity(intent);
        assertTrue(true);


        // Continue with your test
    }

}
