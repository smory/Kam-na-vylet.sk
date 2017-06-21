package sk.smoradap.kamnavyletsk.utils;

import android.location.Location;

import java.util.LinkedList;
import java.util.List;

import sk.smoradap.kamnavyletsk.api.model.Attraction;


/**
 * Created by psmorada on 19.10.2016.
 */
public class GpsUtils {

    private static final double EARTH_RADIUS = 6371.0;

    // in km;
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = EARTH_RADIUS * c;

        return dist;
    }

    public static double distanceFrom(Location loc, Attraction attraction){
        return distFrom(loc.getLatitude(), loc.getLongitude(), Double.parseDouble(attraction.getLatitude()), Double.parseDouble(attraction.getLongitude()));
    }

    public static List<Attraction> getAtractionsInRadius(Location loc, List<Attraction> attractions, double radiusInKm){
        List<Attraction> list = new LinkedList<>();
        for(Attraction a : attractions){
            double distance = distanceFrom(loc, a);
            if(distance <= radiusInKm){
                list.add(a);
            }
        }
        return list;
    }


}
