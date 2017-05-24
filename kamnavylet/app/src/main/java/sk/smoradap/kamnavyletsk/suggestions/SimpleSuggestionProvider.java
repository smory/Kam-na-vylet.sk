package sk.smoradap.kamnavyletsk.suggestions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.suggestions.model.Suggestion;
import sk.smoradap.kamnavyletsk.utils.Utils;

/**
 * Class to provide suggestions for search queries.
 */
@EBean(scope = EBean.Scope.Singleton)
public class SimpleSuggestionProvider implements SuggestionProvider {

    public static final String TAG = SimpleSuggestionProvider.class.getSimpleName();

    @RootContext
    Context context;

    private Map<String, List<Suggestion>> suggestionMap = new HashMap<>();

    private boolean buildCompleted = false;

    public SimpleSuggestionProvider(@NonNull Context context){
        build(context);
    }


    public List<Suggestion> findSuggestions(@NonNull String query){
        List<Suggestion> list = new ArrayList<>();
        if(!TextUtils.isEmpty(query)) {
            String standardized = Utils.stripAccents(query).toLowerCase();
            String firstLetter = query.substring(0, 1);
            List<Suggestion> getStartingLetterSuggestions = suggestionMap.get(firstLetter);

            if (getStartingLetterSuggestions == null) {
                return list;
            }

            for (Suggestion suggestion : getStartingLetterSuggestions) {
                if (suggestion.getSuggestionWithoutAccents().startsWith(standardized)) {
                    list.add(suggestion);
                }
            }
        }
        return list;
    }

    @Background
    void build(Context context){
        Log.v(TAG, "Started building of suggestions");
        String[] lines = Utils.loadRawTextResourceAsArray(context, R.raw.predictions);
        for(String line : lines){
            Suggestion suggestion = createSuggestion(line);

            String firstLetter = suggestion.getSuggestionWithoutAccents().substring(0,1);
            List<Suggestion> list = suggestionMap.get(firstLetter);
            if(list == null){
                list = new ArrayList<>();
                suggestionMap.put(firstLetter, list);
            }

            suggestion.setIcon(R.drawable.ic_place_black_24dp);
            list.add(suggestion);
        }

        buildCompleted = true;
        Log.v(TAG, String.format("Suggestion database was built, it contains %d suggestions", lines.length));
    }

    private static Suggestion createSuggestion(String line){
        Suggestion suggestion = new Suggestion();
        String[] a = line.split("\t");
        suggestion.setSuggestionWithoutAccents(a[0]);
        suggestion.setSuggestion(a[1]);
        return suggestion;
    }
}
