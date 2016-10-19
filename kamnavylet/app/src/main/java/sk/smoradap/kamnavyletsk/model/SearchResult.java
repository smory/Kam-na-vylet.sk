package sk.smoradap.kamnavyletsk.model;

/**
 * Created by smora on 01.09.2016.
 */
public class SearchResult implements Item {

    private String name;
    private String town;
    private String sourceUrl;
    private String briefDescription;
    private String previewImageUrl;
    private String fullImageUrl;
    private String distance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "name='" + name + '\'' +
                ", town='" + town + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", briefDescription='" + briefDescription + '\'' +
                ", previewImageUrl='" + previewImageUrl + '\'' +
                ", fullImageUrl='" + fullImageUrl + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
