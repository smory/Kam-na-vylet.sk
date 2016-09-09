package sk.smoradap.kamnavyletsk.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by smora on 02.09.2016.
 */
public class Area {

    private String name;
    private List<District> districts = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public void addDistrict(District district){
        districts.add(district);
    }

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", districts=" + districts +
                '}';
    }
}
