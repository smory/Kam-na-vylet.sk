package sk.smoradap.kamnavyletsk.model;

import java.io.Serializable;

/**
 * Created by smora on 02.09.2016.
 */
public class NearbyAttraction implements Serializable {

    private String name;
    private String url;
    private String distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "NearbyAttraction{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
