package sk.smoradap.kamnavyletsk.suggestions;

import java.util.List;

import sk.smoradap.kamnavyletsk.suggestions.model.Suggestion;

/**
 * Created by Peter Smorada on 13.5.2017.
 */

public interface SuggestionProvider {

    List<Suggestion> findSuggestions(String query);
}
