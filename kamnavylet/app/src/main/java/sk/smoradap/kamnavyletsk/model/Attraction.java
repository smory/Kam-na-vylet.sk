package sk.smoradap.kamnavyletsk.model;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by psmorada on 19.10.2016.
 */
public class Attraction implements Item {

    private String name;
    private String category;
    private List<String> regions;
    private String area;
    private String district;
    private String town;
    private String gps;
    private float longitude;
    private float latitude;
    private String sourceUrl;
    private String previewImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attraction that = (Attraction) o;

        if (Float.compare(that.longitude, longitude) != 0) return false;
        if (Float.compare(that.latitude, latitude) != 0) return false;
        if (!name.equals(that.name)) return false;
        if (!category.equals(that.category)) return false;
        if (!area.equals(that.area)) return false;
        if (!district.equals(that.district)) return false;
        if (!town.equals(that.town)) return false;
        if (!gps.equals(that.gps)) return false;
        if (!sourceUrl.equals(that.sourceUrl)) return false;
        return previewImageUrl.equals(that.previewImageUrl);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + area.hashCode();
        result = 31 * result + district.hashCode();
        result = 31 * result + town.hashCode();
        result = 31 * result + gps.hashCode();
        result = 31 * result + (longitude != +0.0f ? Float.floatToIntBits(longitude) : 0);
        result = 31 * result + (latitude != +0.0f ? Float.floatToIntBits(latitude) : 0);
        result = 31 * result + sourceUrl.hashCode();
        result = 31 * result + previewImageUrl.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Attraction{" +
                "name='" + name + '\'' +
                ", town='" + town + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}
