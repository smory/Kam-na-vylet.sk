package sk.smoradap.kamnavyletsk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smora on 14.09.2016.
 */
public class Utils {

    public static int convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }

    public static String loadRawTextResource(int fileId){
        InputStream inputStream = Resources.getSystem().openRawResource(fileId);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();

        try {
            String line = br.readLine();
            if(line != null) builder.append(line);
            while((line = br.readLine()) != null){
                builder.append(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static List<String> loadRawTextResourceAsList(Context c, int fileId){
        InputStream inputStream = c.getResources().openRawResource(fileId);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> list = new ArrayList<>();

        try {
            String line = br.readLine();
            if(line != null) list.add(line);
            while((line = br.readLine()) != null){
                list.add(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String[] loadRawTextResourceAsArray(Context c, int fileId){
        List<String> l = loadRawTextResourceAsList(c, fileId);
        return l.toArray(new String[l.size()]);
    }
}
