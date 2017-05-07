package sk.smoradap.kamnavyletsk.api;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.model.Prediction;
import sk.smoradap.kamnavyletsk.utils.Utils;

/**
 * Class to provide prediction for search queries.
 * Created by Peter Smorada on 21.10.2016.
 */
public class PredictionProvider {

    private static PredictionProvider predictionProvider;
    private Map<String, List<Prediction>> preditionMap = new HashMap<>();

    public static PredictionProvider getInstance(Context context){

        if(predictionProvider == null){
            predictionProvider = build(context);
        }

        return predictionProvider;
    }

    private static PredictionProvider build(Context context){
        String[] lines = Utils.loadRawTextResourceAsArray(context, R.raw.predictions);
        PredictionProvider provider = new PredictionProvider();
        Map<String,List<Prediction>> predictionMap = provider.preditionMap;
        for(String line : lines){
            Prediction prediction = parseLine(line);

            String firstLetter = prediction.getPredictionString().substring(0,1);
            List<Prediction> list = predictionMap.get(firstLetter);
            if(list == null){
                list = new ArrayList<>();
                predictionMap.put(firstLetter, list);
            }

            list.add(prediction);
        }
        return provider;
    }

    public static void asyncBuild(final Context context){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                build(context);
            }
        };
        new Thread(r).start();
    }

    private static Prediction parseLine(String line){
        Prediction predition = new Prediction();
        String[] a = line.split("\t");
        predition.setPredictionString(a[0]);
        predition.setStandardString(a[1]);
        predition.setAdvancedPredictions(a[0].split("\\s+"));
        return predition;
    }

    public List<Prediction> findPreditions(String string){
        String standardized = Utils.stripAccents(string).toLowerCase();
        List<Prediction> list = new ArrayList<>();
        String firstLetter = string.substring(0, 1);
        List<Prediction> startingWithLetterPreditions = preditionMap.get(firstLetter);

        if(startingWithLetterPreditions == null){
            return list;
        }
        
        for(Prediction prediction : startingWithLetterPreditions){
            if(prediction.getPredictionString().startsWith(standardized)){
                list.add(prediction);
            }
        }
        return list;
    }
}
