package sk.smoradap.kamnavyletsk.model;

/**
 * Created by smora on 02.09.2016.
 */
public class District {

    private String displayName;
    private String detailsUrl;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    @Override
    public String toString() {
        return "District{" +
                "displayName='" + displayName + '\'' +
                ", detailsUrl='" + detailsUrl + '\'' +
                '}';
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
}
