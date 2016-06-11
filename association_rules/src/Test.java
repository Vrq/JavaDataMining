import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Test environment for the project: association_rules
 *
 * Created by Varq on 2016-04-24.
 */
public class Test {

    public static void main (String[] args) throws FileNotFoundException {

        List<List<String>> dataset = DataReaderCSV.readDataFromCSV();
        List<ArrayList<String>> supportValues = Association.calculateSupport(dataset);
        List<ArrayList<String>> assocValues = Association.calculateAssociationRules(dataset);

        Scanner reader = new Scanner(System.in);
        reader.useLocale(Locale.ENGLISH);
        System.out.println("Press enter to begin ");
        String option = reader.nextLine();
        double userVal;
        double userConf;

        while(!(option.equals("q"))) {

            System.out.print("\n");
            System.out.println("Choose an option from the menu. Enter 'q' to quit. ");
            System.out.println("1 - Print the dataset ");
            System.out.println("2 - Print support values for all products");
            System.out.println("3 - Rate the frequency of the products");
            System.out.println("4 - Get all associations above choosen support and confidence");
            option = reader.nextLine();

            switch(option) {
                case "1":
                    // printing the transaction list in equal width columns:
                    for (int i = 0; i < dataset.size(); i++) {
                        for (int j = 0; j < dataset.get(0).size(); j++) {
                            System.out.printf("%-10s", dataset.get(i).get(j));
                        }
                        System.out.print("\n");
                    }
                    break;
                case "2":
                    //printing all support values:
                    for(int i = 0; i<dataset.size()-1; i++) {
                        System.out.println(supportValues.get(i));
                    }
                    break;
                case "3":
                    //printing frequency rates:
                    System.out.println("Type frequency level:");
                    userVal = reader.nextDouble();
                    reader.nextLine();
                    Association.isFrequent(supportValues,userVal);
                    System.out.println("[Product1, Product2, Support]");
                    for(int i = 0; i<dataset.size()-1; i++) {
                        System.out.println(supportValues.get(i));
                    }
                    break;
                case "4":
                    System.out.println("Type frequency level:");
                    userVal = reader.nextDouble();
                    System.out.println("Type confidence level:");
                    userConf = reader.nextDouble();
                    reader.nextLine();
                    //printing all association rules above certain support- and confidence-level
                    List<ArrayList<String>> assocValuesF = Association.getFrequentAndConfident(assocValues, userVal, userConf);
                    System.out.println("[Product1, Product2, Support, Confidence]");
                    for(int i = 0; i<assocValuesF.size(); i++) {
                        System.out.println(assocValuesF.get(i));
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
