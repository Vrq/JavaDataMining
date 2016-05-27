import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Association class includes method to calculate such dataset properties as: support, confidence and association rules
 *
 * Created by Varq on 2016-04-24.
 */
public class Association {
    //support is for each item the percentage of the transactions the item is included
    public static List<ArrayList<String>> calculateSupport(List<List<String>> dataset) {

        List<ArrayList<String>> supportValues = new ArrayList<ArrayList<String>>();

        for(int i = 1; i<dataset.get(0).size(); i++) {
            supportValues.add(new ArrayList(Arrays.asList(dataset.get(0).get(i))));
            int counter = 0;
            double suppValue = 0;
            for (int j = 1; j<dataset.size(); j++) {
                if(!dataset.get(j).get(i).isEmpty()) {
                    counter++;
                }
            }
            suppValue = (counter/(dataset.size()-1.0))*100; //jak wczesniej odejmowałem 1 zamiast 1.0 to konwertowało calosc do int - dlaczego?
            supportValues.get(i-1).add(Double.toString(suppValue));
            counter = 0;
        }
        return supportValues;
    }

    //isFrequent specifies if the support level is equal or bigger than a certain value (minsup) - frequent, or less - weak
    public static List<ArrayList<String>> isFrequent(List<ArrayList<String>> supportValues, double minsup) {
        for(int i = 0; i<supportValues.size(); i++) {
            if(Double.parseDouble(supportValues.get(i).get(1)) >= minsup) {
                supportValues.get(i).add("frequent");
            }
            else {
                supportValues.get(i).add("weak");
            }
        }
        return supportValues;
    }

    //simple association rules with 2 items X, Y, the result (each row) should be: X-Y,s,c
    // s - support: percentage of transactions which include X OR Y relative to the total transaction number
    // c - confidence: percentage of transactions which include Y AND X relative to the number of transactions which include X
    public static List<ArrayList<String>> calculateAssociationRules (List<List<String>> dataset) {

        int xCounter = 0;
        int orCounter = 0;
        int andCounter = 0;
        double support = 0;
        double confidence = 0;
        String[] assocPair = new String[2];
        List<ArrayList<String>> associationValues = new ArrayList<ArrayList<String>>();

        for(int x = 1; x<dataset.get(0).size(); x++) {
            for(int y = 1; y<dataset.get(0).size(); y++) {
                if(x == y) {
                    continue;
                }
                assocPair[0] = dataset.get(0).get(x);
                assocPair[1] = dataset.get(0).get(y);
                associationValues.add(new ArrayList(Arrays.asList(assocPair)));

                for(int transactionRow = 1; transactionRow<dataset.size(); transactionRow++) {

                    //calculate the number of x-item in total transactions
                    if(!dataset.get(transactionRow).get(x).isEmpty()) {
                        xCounter++;
                    }

                    //calculate the number of transactions with x OR y
                    if(!dataset.get(transactionRow).get(x).isEmpty() || !dataset.get(transactionRow).get(y).isEmpty()) {
                        orCounter++;
                    }

                    //calculate the number of transactions with x AND y
                    if(!dataset.get(transactionRow).get(x).isEmpty() && !dataset.get(transactionRow).get(y).isEmpty()) {
                        andCounter++;
                    }

                }

                support = (orCounter/(dataset.size()-1.0))*100;
                confidence = (1.0*andCounter/xCounter)*100;

                //adding support and confidence for each item pair:
                associationValues.get(associationValues.size()-1).add(Double.toString(support));
                associationValues.get(associationValues.size()-1).add(Double.toString(confidence));

                //reseting the counters and variables:
                support = 0;
                confidence = 0;
                orCounter = 0;
                xCounter = 0;
                andCounter = 0;
            }
        }

        return associationValues;
    }

    public static List<ArrayList<String>> getFrequentAndConfident(List<ArrayList<String>> associationValues, double minsup, double minconf) {
        List<ArrayList<String>> filteredAssociations = new ArrayList();

        for(int assocNumber = 0; assocNumber<associationValues.size(); assocNumber++) {
            if(Double.parseDouble(associationValues.get(assocNumber).get(2)) >= minsup && Double.parseDouble(associationValues.get(assocNumber).get(3)) >= minconf) {
                filteredAssociations.add(associationValues.get(assocNumber));
            }
        }

        return filteredAssociations;
    }
}
