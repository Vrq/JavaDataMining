import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varq on 2016-04-24.
 */
public class DataPreparator {

    //returns the dataset with normalized parameter values (between 0 and 1)
    public static List<List<String>> normalizeValues (List<List<String>> dataset) {

        List<List<String>> normalizedData = new ArrayList(dataset);

        double maxValue = 0;
        double currValue = 0;
        for(int paramIndex = 0; paramIndex < dataset.get(0).size()-1; paramIndex++) {

            //find the max value of a parameter:
            for(int flowerIndex = 1; flowerIndex < dataset.size(); flowerIndex++) {
                if(Double.parseDouble(dataset.get(flowerIndex).get(paramIndex)) > maxValue) { //pytanie - czy nie powinienem utworzyc dodatkowej zmiennej
                    maxValue = Double.parseDouble(dataset.get(flowerIndex).get(paramIndex));  // do trzymania wartosci tak zeby kod był bardziej czytelny?
                }
            }
            //normalize the values by dividing: value/maxValue
            for(int flowerIndex = 1; flowerIndex < dataset.size(); flowerIndex++) {
                currValue = Double.parseDouble(dataset.get(flowerIndex).get(paramIndex)); // tutaj uzyłem zmiennej :)
                normalizedData.get(flowerIndex).set(paramIndex,Double.toString(1.0*currValue/maxValue));
            }
        }

        return normalizedData;
    }
}
