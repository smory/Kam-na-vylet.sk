package sk.smoradap.kamnavyletsk.model;

/**
 * Created by smora on 01.09.2016.
 */
public class SearchResult {

    private String name;
    private String town;
    private String descriptionUrl;
    private String briefDescription;
    private String previewImageUlr;
    private String fullImageUrl;
    private String distance;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewImageUlr() {
        return previewImageUlr;
    }

    public void setPreviewImageUlr(String previewImageUlr) {
        this.previewImageUlr = previewImageUlr;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
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
                ", descriptionUrl='" + descriptionUrl + '\'' +
                ", briefDescription='" + briefDescription + '\'' +
                ", previewImageUlr='" + previewImageUlr + '\'' +
                ", fullImageUrl='" + fullImageUrl + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
