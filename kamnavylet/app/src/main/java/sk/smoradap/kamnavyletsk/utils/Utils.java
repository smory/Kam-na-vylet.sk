package sk.smoradap.kamnavyletsk.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by smora on 14.09.2016.
 */
public class Utils {

    public static int convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }
}
