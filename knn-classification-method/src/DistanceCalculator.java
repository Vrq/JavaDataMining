/**
 * DistanceCalculator class provides methods for the distance calculation between 2 points (which can be for example 2 objects) with multiple values
 * Created by Varq on 2016-04-07.
 */
import java.util.ArrayList;
public class DistanceCalculator {

    // calculate
    public static double calculateEuclides (double[] newElement, double[] datasetElement) {
        double distance = 0;
        for(int i = 0; i<newElement.length; i++)
        {
            distance = distance + (newElement[i] -  datasetElement[i]) * (newElement[i] - datasetElement[i]);
        }

        return Math.sqrt(distance);
    };
    
    public static void main (String[] args) {

    	double[] tested = {0, 0};
    	double[] referenced = {3, 4};
    	
    	System.out.print("Distance =:" + DistanceCalculator.calculateEuclides(tested, referenced));
    }


}
