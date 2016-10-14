package sk.smoradap.kamnavyletsk.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by smora on 01.09.2016.
 */
public class AttractionDetails {

    private String name;
    private String category;
    private String region;
    private String area;
    private String district;
    private String town;
    private String email;
    private String phone;
    private String gps;
    private String webSite;
    private String description;
    private String sourceUrl;
    private List<String> imageUrls = new LinkedList<>();
    private List<NearbyAttraction> nearbyAttractions = new LinkedList<>();
    private Map<String, String> detailsMap = new LinkedHashMap<>();

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void addImageUrl(String url){
        imageUrls.add(url);
    }

    public List<NearbyAttraction> getNearbyAttractions() {
        return nearbyAttractions;
    }

    public void setNearbyAttractions(List<NearbyAttraction> nearbyAttractions) {
        this.nearbyAttractions = nearbyAttractions;
    }

    public void addNearbyAttraction(NearbyAttraction nearbyAttraction){
        nearbyAttractions.add(nearbyAttraction);
    }

    public Map<String, String> getDetailsMap() {
        return detailsMap;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setDetailsMap(Map<String, String> detailsMap) {
        this.detailsMap = detailsMap;
    }

    public void addDetailsPair(String name, String value){
        detailsMap.put(name, value);
    }

    @Override
    public String toString() {
        return "AttractionDetails{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", town='" + town + '\'' +
                ", description='" + description + '\'' +
                ", nearbyAttractions=" + nearbyAttractions +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
