import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * For test purposes
 * Created by Varq on 2016-05-05.
 */
public class Test {

   public static void main(String[] args) {

       List<List<String>> dataset = DataReaderDB.readFromDB("appearances");
       GraphParam AGDS = GraphConverter.convertTableToGraph(dataset);

       Scanner reader = new Scanner(System.in);
       reader.useLocale(Locale.ENGLISH);
       System.out.println("Press enter to begin ");
       String option = reader.nextLine();

       long startTime;
       long stopTime;
       long elapsedTime;
       int datasetSize = dataset.size() - 1;

       while(!(option.equals("q"))) {
           datasetSize = dataset.size() - 1;
           System.out.print("\n");
           System.out.println("#---Dataset: Baseball players statistics---#");
           System.out.println("#---Size: " + datasetSize + " records---#");
           System.out.print("\n");
           System.out.println("Choose an option from the menu. Enter 'q' to quit. ");
           System.out.println("1 - SELECT * WHERE x AND y");
           System.out.println("2 - SELECT * WHERE x OR y");
           System.out.println("3 - Print the column names with indexes");
           System.out.println("4 - Change table");
           System.out.println("5 - Add record");

           option = reader.nextLine();

           switch (option) {
               case "1":
                   //SELECT * WHERE x AND y"
                   int[] paramN;
                   String[] paramV;
                   int resultsNum;

                   System.out.println("Type in the column indexes (comma separated)");
                   paramV = reader.nextLine().split(",");
                   paramN = new int[paramV.length];
                   int i = 0;
                   for(String str : paramV) {
                       paramN[i] = Integer.parseInt(str);
                       i++;
                   }
                   System.out.println("Type in the search values (comma separated)");
                   paramV = reader.nextLine().split(",");
                   startTime = System.currentTimeMillis();
                   List<ObjectID> testingQuery = AGDS.selectWhereAND(paramN,paramV); // QUERY
                   //Print results:
                   for(ObjectID obj : testingQuery) {
                       System.out.println();
                       for(ParamValue val : obj.getValueList()) {
                           System.out.print(val.getValue() + "\t");
                       }
                   }
                   resultsNum = testingQuery.size();
                   stopTime = System.currentTimeMillis();
                   elapsedTime = stopTime - startTime;
                   System.out.println();
                   System.out.println(resultsNum + " results in : " + elapsedTime + "ms");
                   System.out.println("--------------------------------------");
                   break;

               case "2":
                   //SELECT * WHERE x OR y"
                   System.out.println("Type in the column indexes (comma separated)");
                   paramV = reader.nextLine().split(",");
                   paramN = new int[paramV.length];
                   i = 0;
                   for(String str : paramV) {
                       paramN[i] = Integer.parseInt(str);
                       i++;
                   }
                   System.out.println("Type in the search values (comma separated)");
                   paramV = reader.nextLine().split(",");
                   startTime = System.currentTimeMillis();
                   testingQuery = AGDS.selectWhereOR(paramN,paramV); // QUERY
                   //Print results:
                   for(ObjectID obj : testingQuery) {
                       System.out.println();
                       for(ParamValue val : obj.getValueList()) {
                           System.out.print(val.getValue() + "\t");
                       }
                   }
                   resultsNum = testingQuery.size();
                   stopTime = System.currentTimeMillis();
                   elapsedTime = stopTime - startTime;
                   System.out.println();
                   System.out.println(resultsNum + " results in : " + elapsedTime + "ms");
                   System.out.println("--------------------------------------");
                   break;

               case "3":
                   //Print the parameter label names:
                   for(int paramIndex = 0; paramIndex<AGDS.paramList.size(); paramIndex++) {
                       System.out.println(paramIndex + " - " + AGDS.paramList.get(paramIndex).getName());
                   }
                   break;

               case "4":
                   System.out.println("1 - allstarfull, 2 - appearances. Type in your choice");
                   String userTable = reader.nextLine();
                   if(userTable.equals("1")) {
                       dataset = DataReaderDB.readFromDB("allstarfull");
                       AGDS = GraphConverter.convertTableToGraph(dataset);
                   }
                   else {
                       if(userTable.equals('2')) {
                           dataset = DataReaderDB.readFromDB("appearances");
                           AGDS = GraphConverter.convertTableToGraph(dataset);
                       }
                       else {
                           System.out.println("No such table");
                       }
                   }
                   break;

               case "5":
                   //Add a record
                   String[] userTextArray;

                   int parameterCount = dataset.get(0).size();
                   userTextArray = new String[parameterCount + 1]; // +1 because of the name
                   System.out.println("Record name (not class): ");
                   userTextArray[0] = reader.nextLine();
                   for(int parameterIndex = 1; parameterIndex<parameterCount; parameterIndex++) {
                       System.out.println("Type: " + dataset.get(0).get(parameterIndex-1));
                       userTextArray[parameterIndex] = reader.nextLine();
                   }
                   AGDS.addRecord(userTextArray,AGDS);
                   System.out.println("Record added");
                   break;
               default:
                   break;
           }

       }
   }
}
