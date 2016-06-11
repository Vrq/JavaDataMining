import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * For test purposes
 * Created by Varq on 2016-05-05.
 */
public class Test {

    public static void main(String[] args) {
        try {

            List<List<String>> dataset = DataReaderCSV.readDataFromCSV("src/IrisDataAll.csv");
            GraphParam AGDS = GraphConverter.convertTableToGraph(dataset);

            Scanner reader = new Scanner(System.in);
            reader.useLocale(Locale.ENGLISH);
            System.out.println("Press enter to begin ");
            String option = reader.nextLine();

            String setName = "IrisDataAll";
            long startTime;
            long stopTime;
            long elapsedTime;
            int datasetSize = dataset.size() - 1;

            while(!(option.equals("q"))) {
                datasetSize = dataset.size() - 1;
                System.out.print("\n");
                System.out.println("#---Dataset: " + setName  + ".csv---#");
                System.out.println("#---Size: " + datasetSize + " records---#");
                System.out.print("\n");
                System.out.println("Choose an option from the menu. Enter 'q' to quit. ");
                System.out.println("1 - Find similar based on given object");
                System.out.println("2 - Find similar based on given values");
                System.out.println("3 - Find objects with exact given values");
                System.out.println("4 - Find objects that have a value within given range");
                System.out.println("5 - Print all values of a parameter sorted");
                System.out.println("6 - Print the column names with indexes");
                System.out.println("7 - Change dataset");

                option = reader.nextLine();

                switch (option) {
                    case "1":
                        //Find similar based on given object
                        String[] userTextArray;
                        int i;
                        int parameterCount = dataset.get(0).size()-1;
                        userTextArray = new String[parameterCount];
                        for(int parameterIndex = 0; parameterIndex<parameterCount; parameterIndex++) {
                            System.out.println("Type: " + dataset.get(0).get(parameterIndex));
                            userTextArray[parameterIndex] = reader.nextLine();
                        }
                        startTime = System.currentTimeMillis();

                        List<ObjectID> winnersList = AGDS.findMostFimilar(userTextArray);
                        List<ObjectID> losersList = AGDS.findLeastFimilar(userTextArray);
                        //winners print
                        System.out.print("\n");
                        System.out.println("Most similar top3: ");
                        System.out.printf("%-5s %-5.5s","ObjID", "Wage");
                        System.out.print("\n");
                        for(i = 0; i<3; i++) {
                            System.out.printf("%-5s %-5.5s",winnersList.get(i).getName(), winnersList.get(i).getObjectWage());
                            System.out.print("\n");
                        }
                        System.out.print("Best guess: " + winnersList.get(0).getName() + " " + winnersList.get(0).getClassName().getName());
                        for(ParamValue val : winnersList.get(0).getValueList()) {
                            System.out.print(" " + val.getValue());
                        }
                        System.out.print("\n");
                        //loosers print
                        System.out.print("\n");
                        System.out.println("Least similar top3: ");
                        System.out.printf("%-5s %-5.5s","ObjID", "Wage");
                        System.out.print("\n");
                        for(i = 0; i<3; i++) {
                            System.out.printf("%-5s %-5.5s",losersList.get(i).getName(), losersList.get(i).getObjectWage());
                            System.out.print("\n");
                        }
                        System.out.print("Worst guess: " + losersList.get(0).getName() + " " + losersList.get(0).getClassName().getName());
                        for(ParamValue val : losersList.get(0).getValueList()) {
                            System.out.print(" " + val.getValue());
                        }
                        System.out.print("\n");

                        stopTime = System.currentTimeMillis();
                        elapsedTime = stopTime - startTime;
                        System.out.println();
                        System.out.println(" results in : " + elapsedTime + "ms");
                        System.out.println("--------------------------------------");
                        break;

                    case "2":
                        //Find similar based on given values
                        int[] paramN;
                        String[] paramV;

                        //user input:
                        System.out.println("Type in the parameters indices (comma separated)");
                        paramV = reader.nextLine().split(",");
                        paramN = new int[paramV.length];
                        i = 0;
                        for(String str : paramV) {
                            paramN[i] = Integer.parseInt(str);
                            i++;
                        }
                        System.out.println("Type in the values (comma separated)");
                        paramV = reader.nextLine().split(",");
                        startTime = System.currentTimeMillis();
                        List<ObjectID> similarValues = AGDS.findWithValues(paramV,paramN);
                        //print results:
                        System.out.print("\n");
                        System.out.println("Most similar top3: ");
                        System.out.printf("%-5s %-5.5s","ObjID", "Wage");
                        System.out.print("\n");
                        for(i = 0; i<3; i++) {
                            System.out.printf("%-5s %-5.5s",similarValues.get(i).getName(), similarValues.get(i).getObjectWage());
                            System.out.print("\n");
                        }
                        System.out.print("Best guess: " + similarValues.get(0).getName() + " " + similarValues.get(0).getClassName().getName());
                        for(ParamValue val : similarValues.get(0).getValueList()) {
                            System.out.print(" " + val.getValue());
                        }
                        System.out.print("\n");
                        stopTime = System.currentTimeMillis();
                        elapsedTime = stopTime - startTime;
                        System.out.println();
                        System.out.println(" results in : " + elapsedTime + "ms");
                        System.out.println("--------------------------------------");
                        break;

                    case "3":
                        //Find objects with exact given values
                        //user input:
                        System.out.println("Type in the parameters indices (comma separated)");
                        paramV = reader.nextLine().split(",");
                        paramN = new int[paramV.length];
                        i = 0;
                        for(String str : paramV) {
                            paramN[i] = Integer.parseInt(str);
                            i++;
                        }
                        System.out.println("Type in the values (comma separated)");
                        paramV = reader.nextLine().split(",");
                        startTime = System.currentTimeMillis();
                        similarValues = AGDS.findExactWithValues(paramV,paramN);
                        //print results:
                        System.out.print("\n");
                        System.out.println("Most similar top3: ");
                        System.out.printf("%-5s %-5.5s","ObjID", "Wage");
                        System.out.print("\n");
                        for(ObjectID obj : similarValues) {
                            System.out.printf("%-5s %-5.5s",obj.getName(), obj.getObjectWage());
                            System.out.print("\n");
                        }
                        if(similarValues.size() != 0) {
                            System.out.print("Best guess: " + similarValues.get(0).getName() + " " + similarValues.get(0).getClassName().getName());
                            for(ParamValue val : similarValues.get(0).getValueList()) {
                                System.out.print(" " + val.getValue());
                            }
                        }

                        System.out.print("\n");
                        stopTime = System.currentTimeMillis();
                        elapsedTime = stopTime - startTime;
                        System.out.println();
                        System.out.println(" results in : " + elapsedTime + "ms");
                        System.out.println("--------------------------------------");
                        break;

                    case "4":
                        //Find objects that have a value within given range
                        String minR;
                        String maxR;
                        int paramInd;
                        System.out.println("Select parameter index:");
                        paramInd = reader.nextInt();
                        reader.nextLine();
                        System.out.println("Type minimal value:");
                        minR = reader.nextLine();
                        System.out.println("Type maximal value:");
                        maxR = reader.nextLine();
                        List<ObjectID> inRangeValues = AGDS.findWithinValuesRange(minR,maxR,paramInd);
                        System.out.println("Objects with " + AGDS.paramList.get(paramInd).getName() + " between " + minR + " and " + maxR);
                        if(inRangeValues.isEmpty()) {
                            System.out.println("No results found");
                        }
                        else {
                            for(ObjectID obj : inRangeValues) {
                                System.out.println(obj.getName());
                            }
                        }
                        break;

                    case "5":
                        //Print all values of a parameter sorted
                        System.out.println("Select parameter index:");
                        int paramIndex2 = reader.nextInt();
                        reader.nextLine();
                        AGDS.paramList.get(paramIndex2).sortValues();
                        System.out.println("Sorted values of: " + AGDS.paramList.get(paramIndex2).getName());
                        for(ParamValue val : AGDS.paramList.get(paramIndex2).getValueList()) {
                            System.out.println(val.getValue());
                        }
                        break;

                    case "6":
                        //Print the parameter label names:
                        for (int paramIndex = 0; paramIndex < AGDS.paramList.size(); paramIndex++) {
                            System.out.println(paramIndex + " - " + AGDS.paramList.get(paramIndex).getName());
                        }
                        break;

                    case "7":
                        System.out.println("1 - IrisDataAll, 2 - YeastShort, 3 - Yeast, 4 - Wine. Type in your choice");
                        String userTable = reader.nextLine();
                        switch(userTable) {
                            case "1":
                                try {
                                    dataset = DataReaderCSV.readDataFromCSV("src/IrisDataAll.csv");
                                    AGDS = GraphConverter.convertTableToGraph(dataset);
                                    setName = "IrisDataAll";
                                } catch (FileNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case "2" :
                                try {
                                    dataset = DataReaderCSV.readDataFromCSV("src/YeastShort.csv");
                                    AGDS = GraphConverter.convertTableToGraph(dataset);
                                    setName = "YeastShort";
                                } catch (FileNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case "3" :
                                try {
                                    dataset = DataReaderCSV.readDataFromCSV("src/Yeast.csv");
                                    AGDS = GraphConverter.convertTableToGraph(dataset);
                                    setName = "Yeast";
                                } catch (FileNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case "4" :
                                try {
                                    dataset = DataReaderCSV.readDataFromCSV("src/Wine.csv");
                                    AGDS = GraphConverter.convertTableToGraph(dataset);
                                    setName = "Wine";
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



        } catch(FileNotFoundException e) {
            System.err.println("FileNotFoundException:" + e.getMessage());
        };




    }
}
