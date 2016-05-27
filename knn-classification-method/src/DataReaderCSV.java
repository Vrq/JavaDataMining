/**
 * Class DataReaderCSV reads a file in csv format, stores the data in ArrayLists and displays it in equal width columns
 *
 * Created by Varq on 2016-04-07.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//readDataFromCSV ()
public class DataReaderCSV {

    public static ArrayList<List<String>> readDataFromCSV ()  throws FileNotFoundException {

        //open file
        Scanner scanner = new Scanner(new File("src/IrisDataAll.csv"));
        scanner.useDelimiter(";");


        //ArrayLists for each column (in this case flower property and flower class)
        ArrayList dataLeafLength  = new ArrayList();
        ArrayList dataLeafWidth  = new ArrayList();
        ArrayList dataPetalLength  = new ArrayList();
        ArrayList dataPetalWidth  = new ArrayList();
        ArrayList dataFlowerClass  = new ArrayList();

        //Reading the data line by line and adding to specific ArrayLists:
        while(scanner.hasNext()){
          //  System.out.print(scanner.nextLine()+"|");
            String dataLine = scanner.nextLine();

            String[] dataLineArray = dataLine.split(";",-1);

            dataLeafLength.add(dataLineArray[0]);
            dataLeafWidth.add(dataLineArray[1]);
            dataPetalLength.add(dataLineArray[2]);
            dataPetalWidth.add(dataLineArray[3]);
            dataFlowerClass.add(dataLineArray[4]);

        }
        scanner.close();

        //Displaying data in columns with equal width:
        /*
        for(int i = 0; i<dataFlowerClass.size(); i++) {
            System.out.println(String.format("%-20s  %-20s %-20s %-20s %-20s" , dataLeafLength.get(i), dataLeafWidth.get(i), dataPetalLength.get(i), dataPetalWidth.get(i), dataFlowerClass.get(i) ));
        }
        */
        //Returning the data as ArrayList<List<String>> 
        ArrayList<List<String>> dataset = new ArrayList(5);
        dataset.add(dataLeafLength);
        dataset.add(dataLeafWidth);
        dataset.add(dataPetalLength);
        dataset.add(dataPetalWidth);
        dataset.add(dataFlowerClass);
        
        return dataset;


    }
}
