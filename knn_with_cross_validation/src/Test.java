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

        String firstSet = "src/Wine.csv";
        String secondSet = "src/IrisDataAll.csv";

        List<List<String>> dataset = DataReaderCSV.readDataFromCSV(secondSet);

        List<List<String>> dataset2 = DataPreparator.normalizeValues(dataset);

        // printing the dataset in equal width columns:
        for (int j = 0; j<dataset2.get(0).size(); j++) {
            System.out.printf("%-15.13s", dataset2.get(0).get(j));
        }
        System.out.print("\n");
        for (int i = 1; i<dataset2.size(); i++) {
            for (int j = 0; j<dataset2.get(0).size(); j++) {
                System.out.printf("%-15.6s", dataset2.get(i).get(j));
            }
            System.out.print("\n");
        }

        //Printing the list of k-values with guess-correctness percentage:
        ArrayList<List<String>> validK = newCoreKNN.crossValidate(dataset);

        for(int i = 0; i<validK.size(); i++) {
            System.out.println(validK.get(i));
        }

    }
}
