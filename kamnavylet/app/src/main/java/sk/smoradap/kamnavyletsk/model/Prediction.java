package sk.smoradap.kamnavyletsk.model;

/**
 * Created by psmorada on 21.10.2016.
 */
public class Prediction {

    private String predictionString;
    private String standardString;
    private String[] advancedPredictions;


    public String getPredictionString() {
        return predictionString;
    }

    public void setPredictionString(String predictionString) {
        this.predictionString = predictionString;
    }

    public String getStandardString() {
        return standardString;
    }

    public void setStandardString(String standardString) {
        this.standardString = standardString;
    }

    public String[] getAdvancedPredictions() {
        return advancedPredictions;
    }

    public void setAdvancedPredictions(String[] advancedPredictions) {
        this.advancedPredictions = advancedPredictions;
    }
}
