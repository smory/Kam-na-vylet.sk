package sk.smoradap.kamnavyletsk.utils;

import android.content.Context;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

import sk.smoradap.kamnavyletsk.suggestions.SimpleSuggestionProvider;
import sk.smoradap.kamnavyletsk.suggestions.SimpleSuggestionProvider_;
import sk.smoradap.kamnavyletsk.suggestions.model.Suggestion;

/**
 * Created by Peter Smorada on 21.10.2016.
 */
public class SuggestionsUtils {

    public static MatrixCursor createSugestionCursor(Context context, String query){
        MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "name" });

        SimpleSuggestionProvider provider = SimpleSuggestionProvider_.getInstance_(context);
        List<Suggestion> suggestions = provider.findSuggestions(query);
        for(int i = 0; i < suggestions.size(); i++){
            c.addRow(new Object[]{i, suggestions.get(i).getBody()});
        }
        return c;
    }

    public static List<Suggestion> getSearchSuggestions(Context context, String query){
        if(!TextUtils.isEmpty(query)) {
            SimpleSuggestionProvider provider = SimpleSuggestionProvider_.getInstance_(context);
            return provider.findSuggestions(query);
        } else {
            return new LinkedList<>();
        }
    }

}
