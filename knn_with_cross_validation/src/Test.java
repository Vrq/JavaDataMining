import java.io.FileNotFoundException;
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

        Scanner reader = new Scanner(System.in);
        reader.useLocale(Locale.ENGLISH);
        System.out.println("Press enter to begin ");
        String option = reader.nextLine();
        List<List<String>> dataset = DataReaderCSV.readDataFromCSV("src/Wine.csv");
        String userSet = "Wine";
        int datasetSize;
        long startTime;
        long stopTime;
        long elapsedTime;

        while(!(option.equals("q"))) {
            datasetSize = dataset.size() - 1;
            System.out.print("\n");
            System.out.println("#---Dataset: " + userSet + ".csv---#");
            System.out.println("#---Size: " + datasetSize + " records---#");
            System.out.print("\n");
            System.out.println("Choose an option from the menu. Enter 'q' to quit. ");
            System.out.println("1 - Print the whole dataset (parameters normalized)");
            System.out.println("2 - Print the whole dataset - original");
            System.out.println("3 - Perform cross-validation");
            System.out.println("4 - Change dataset");

            option = reader.nextLine();

            switch (option) {
                case "1":
                    //Print the whole dataset (parameters normalized
                    List<List<String>> dataset2 = DataPreparator.normalizeValues(dataset);
                    // printing the dataset in equal width columns:
                    for (int j = 0; j<dataset2.get(0).size(); j++) {
                        System.out.printf("%-15.13s", dataset2.get(0).get(j));
                    }
                    System.out.print("\n");
                    for (int i = 1; i<dataset2.size(); i++) {
                        for (int j = 0; j<dataset2.get(0).size()-1; j++) {
                            System.out.printf("%-15.6s", dataset2.get(i).get(j));
                        }
                        System.out.print(dataset2.get(i).get(dataset2.get(0).size()-1));
                        System.out.print("\n");

                    }
                    break;

                case "2":
                    //Print the whole dataset - original
                    for (int j = 0; j<dataset.get(0).size(); j++) {
                        System.out.printf("%-15.13s", dataset.get(0).get(j));
                    }
                    System.out.print("\n");
                    for (int i = 1; i<dataset.size(); i++) {
                        for (int j = 0; j<dataset.get(0).size()-1; j++) {
                            System.out.printf("%-15.6s", dataset.get(i).get(j));
                        }
                        System.out.print(dataset.get(i).get(dataset.get(0).size()-1));
                        System.out.print("\n");
                    }
                    break;
                case "3":
                    //Perform cross-validation
                    startTime = System.currentTimeMillis();
                    //Printing the list of k-values with guess-correctness percentage:
                    ArrayList<List<String>> validK = newCoreKNN.crossValidate(dataset);
                    System.out.printf("%-5s %-5.8s","k", "correct");
                    System.out.print("\n");
                    for(int i = 0; i<validK.size(); i++) {
                        System.out.printf("%-5s %-5.5s",validK.get(i).get(0), validK.get(i).get(1));
                        System.out.print("\n");
                    }
                    stopTime = System.currentTimeMillis();
                    elapsedTime = stopTime - startTime;
                    System.out.println();
                    System.out.println(" results in : " + elapsedTime + "ms");
                    System.out.println("--------------------------------------");
                    break;

                case "4":
                    System.out.println("1 - IrisDataAll, 2 - YeastShort, 3 - Yeast, 4 - Wine. Type in your choice");
                    String userTable = reader.nextLine();
                    switch(userTable) {
                        case "1":
                            try {
                                dataset = DataReaderCSV.readDataFromCSV("src/IrisDataAll.csv");
                                userSet = "IrisDataAll";
                            } catch (FileNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "2" :
                            try {
                                dataset = DataReaderCSV.readDataFromCSV("src/YeastShort.csv");
                                userSet = "YeastShort";
                            } catch (FileNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "3" :
                            try {
                                dataset = DataReaderCSV.readDataFromCSV("src/Yeast.csv");
                                userSet = "Yeast";
                            } catch (FileNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "4" :
                            try {
                                dataset = DataReaderCSV.readDataFromCSV("src/Wine.csv");
                                userSet = "Wine";
                            } catch (FileNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        default:
                            break;
                    }

                default:
                    break;
            }

        }




    }
}
