package sk.smoradap.kamnavyletsk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by smora on 14.09.2016.
 */
public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

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
            if(line != null){
                builder.append(line);
            }
            while((line = br.readLine()) != null){
                builder.append(line);
            }

            br.close();
        } catch (IOException e) {
            Log.w(TAG, e);
        }

        return builder.toString();
    }

    public static List<String> loadRawTextResourceAsList(Context c, int fileId){
        InputStream inputStream = c.getResources().openRawResource(fileId);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> list = new ArrayList<>();

        try {
            String line = br.readLine();
            if(line != null) {
                list.add(line);
            }
            while((line = br.readLine()) != null){
                list.add(line);
            }

            br.close();
        } catch (IOException e) {
            Log.w(TAG, e);
        }

        return list;
    }

    public static String[] loadRawTextResourceAsArray(Context c, int fileId){
        List<String> l = loadRawTextResourceAsList(c, fileId);
        return l.toArray(new String[l.size()]);
    }

    // this is taken from apache commons lang
    public static String stripAccents(final String input) {
        if(input == null) {
            return null;
        }
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");//$NON-NLS-1$
        final StringBuilder decomposed = new StringBuilder(Normalizer.normalize(input, Normalizer.Form.NFD));
        convertRemainingAccentCharacters(decomposed);
        // Note that this doesn't correctly remove ligatures...
        return pattern.matcher(decomposed).replaceAll("");
    }

    private static void convertRemainingAccentCharacters(StringBuilder decomposed) {
        for (int i = 0; i < decomposed.length(); i++) {
            if (decomposed.charAt(i) == '\u0141') {
                decomposed.deleteCharAt(i);
                decomposed.insert(i, 'L');
            } else if (decomposed.charAt(i) == '\u0142') {
                decomposed.deleteCharAt(i);
                decomposed.insert(i, 'l');
            }
        }
    }
}
