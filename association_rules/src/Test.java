import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test environment for the project: association_rules
 *
 * Created by Varq on 2016-04-24.
 */
public class Test {

    public static void main (String[] args) throws FileNotFoundException {

        double frequencyLevel = 50.0;
        double confidenceLevel = 50.0;
        List<List<String>> dataset = DataReaderCSV.readDataFromCSV();

       // printing the transaction list in equal width columns:
        for (int i = 0; i<dataset.size(); i++) {
            for (int j = 0; j<dataset.get(0).size(); j++) {
                System.out.printf("%-10s", dataset.get(i).get(j));
            }
            System.out.print("\n");
        }


        List<ArrayList<String>> supportValues = Association.calculateSupport(dataset);

        //printing all support values:
        for(int i = 0; i<dataset.size()-1; i++) {
            System.out.println(supportValues.get(i));
        }

       Association.isFrequent(supportValues,frequencyLevel); //Dlaczego jeśli modyfikujemy supportValues wewnątrz funkcji to wpływa to na zmienną?
        //Przekazywanie przez referencję a nie przez wartość?
        for(int i = 0; i<dataset.size()-1; i++) {
            System.out.println(supportValues.get(i));
        }

        List<ArrayList<String>> assocValues = Association.calculateAssociationRules(dataset);

        //printing all association rules above certain support- and confidence-level
        List<ArrayList<String>> assocValuesF = Association.getFrequentAndConfident(assocValues, frequencyLevel, confidenceLevel);
        for(int i = 0; i<assocValuesF.size(); i++) {
            System.out.println(assocValuesF.get(i));
        }
    }
}
