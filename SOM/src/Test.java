import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


/**
 * Created by Varq on 2016-06-04.
 */
public class Test {

    public static void main (String[] args) throws FileNotFoundException{


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
            SOMLattice board = new SOMLattice(4,4,dataset.get(0).size()-1);
            System.out.print("\n");
            System.out.println("#---Dataset: " + userSet + ".csv---#");
            System.out.println("#---Size: " + datasetSize + " records---#");
            System.out.print("\n");
            System.out.println("Choose an option from the menu. Enter 'q' to quit. ");
            System.out.println("1 - Print the whole dataset - original");
            System.out.println("2 - Print the SOM before learning (one dimension)");
            System.out.println("3 - Print the SOM after learning (all dimensions)");
            System.out.println("4 - Change dataset");

            option = reader.nextLine();

            switch (option) {
                case "1":
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
                case "2":
                    for(List<SOMNode> row : board.boardMap) {
                        for (SOMNode node : row) {
                            System.out.printf("w[0]: " + "%-5.5s" + " ", node.getNodeWeight()[0]);
                        }
                        System.out.println();
                    }
                    System.out.println("--------------------------------------");
                    break;

                case "3":
                    board.teachTheSOM(dataset);
                    for(int i = 0; i<board.getInputDim(); i++) {
                        for(List<SOMNode> row : board.boardMap) {
                            for (SOMNode node : row) {
                                System.out.printf("w["+ i +"]: " + "%-5.5s" + " ", node.getNodeWeight()[i]);
                            }
                            System.out.println();
                        }
                        System.out.println("--------------------------------------");
                    }

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
