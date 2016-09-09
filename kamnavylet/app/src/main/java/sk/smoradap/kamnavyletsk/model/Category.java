package sk.smoradap.kamnavyletsk.model;

/**
 * Created by smora on 02.09.2016.
 */
public class Category {
    private String  displayName;
    private String categoryUrl;
    private String urlString;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }


    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    @Override
    public String toString() {
        return "Category{" +
                "displayName='" + displayName + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                ", urlString='" + urlString + '\'' +
                '}';
    }

}
