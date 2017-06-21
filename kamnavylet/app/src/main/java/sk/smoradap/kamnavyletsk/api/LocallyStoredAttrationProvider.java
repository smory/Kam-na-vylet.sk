package sk.smoradap.kamnavyletsk.api;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.model.Attraction;
import sk.smoradap.kamnavyletsk.utils.Utils;

/**
 * Class to get information from locally stored data
 * Created by Peter Smorada on 19.10.2016.
 */
public class LocallyStoredAttrationProvider {

    private static LocallyStoredAttrationProvider provider;
    private List<Attraction> attractions;
    private static final Logger LOGGER = Logger.getLogger(LocallyStoredAttrationProvider.class.getName());

    public static LocallyStoredAttrationProvider getInstance(Context context) {
        if (provider == null) {
            List<Attraction> l = build(context);
            provider = new LocallyStoredAttrationProvider();
            provider.attractions = l;
        }

        return provider;
    }

    private static List<Attraction> build(Context context) {
        List<String> lines = Utils.loadRawTextResourceAsList(context, R.raw.db);
        return parseLines(lines);
    }

    protected static List<Attraction> build(File file) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file, "UTF-8");

            while (scanner.hasNext()) {
                arrayList.add(scanner.nextLine());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            LOGGER.warning("File was not found");
        }

        return parseLines(arrayList);

    }

    private static List<Attraction> parseLines(List<String> lines) {
        ArrayList<Attraction> a = new ArrayList<>();

        for (String line : lines) {
            Attraction attraction = createAttraction(line);
            a.add(attraction);

        }
        return a;
    }

    static Attraction createAttraction(String line) {
        String[] details = line.split("\t");
        Attraction attraction = new Attraction();
        attraction.setName(details[0]);
        attraction.setTown(details[1]);
        attraction.setDistrict(details[2]);
        attraction.setArea(details[3]);
        attraction.setRegion(RegionProvider.getInstance().getRegion(details[4]));
        attraction.setSourceUrl(details[6]);
        attraction.setCategory(CategoryProvider.getInstance().getCategory(details[8]));

        String[] gpsCoordinates = attraction.getGps().split(",\\s?");
        for (String coordinate : gpsCoordinates) {

            if (coordinate.startsWith("N")) {
                attraction.setLatitude(coordinate.replace("N", ""));
            }

            if (coordinate.startsWith("E")) {
                attraction.setLongitude(coordinate.replace("E", ""));
            }
        }
        return attraction;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
}
