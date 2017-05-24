package sk.smoradap.kamnavyletsk.suggestions.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Peter Smorada on 21.10.2016.
 */
public class Suggestion implements SearchSuggestion {

    private String suggestionWithoutAccents;
    private String suggestion;

    @DrawableRes
    private int icon = 0;

    public Suggestion(){
    }

    public Suggestion(Parcel in){
        suggestion = in.readString();
        suggestionWithoutAccents = in.readString();
    }

    public String getSuggestionWithoutAccents() {
        return suggestionWithoutAccents;
    }

    public void setSuggestionWithoutAccents(String suggestionWithoutAccents) {
        this.suggestionWithoutAccents = suggestionWithoutAccents;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public int getIcon() {
        return icon;
    }

    public Suggestion setIcon(@DrawableRes int icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public String getBody() {
        return suggestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(suggestion);
        dest.writeString(suggestionWithoutAccents);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Suggestion createFromParcel(Parcel in) {
            return new Suggestion(in);
        }

        public Suggestion[] newArray(int size) {
            return new Suggestion[size];
        }
    };
}
