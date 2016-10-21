package sk.smoradap.kamnavyletsk.utils;

import android.content.Context;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import java.util.List;

import sk.smoradap.kamnavyletsk.api.PredictionProvider;
import sk.smoradap.kamnavyletsk.model.Prediction;

/**
 * Created by psmorada on 21.10.2016.
 */
public class SuggestionsUtils {

    public static MatrixCursor createSugestionCursor(Context context, String query){
        MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "name" });

        PredictionProvider provider = PredictionProvider.getInstance(context);
        List<Prediction> predictions = provider.findPreditions(query);
        for(int i = 0; i < predictions.size(); i++){
            c.addRow(new Object[]{i, predictions.get(i).getStandardString()});
        }
        return c;
    }

}
