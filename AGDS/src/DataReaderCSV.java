/**
 * Class DataReaderCSV reads a file in csv format and stores the data in ArrayLists dataset
 *
 * Created by Varq on 2016-04-07.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class DataReaderCSV {
    private static final String DELIMITER = ";";
    //the file needs to by in .CSV format with semicolon separation and dot notation for numbers
    public static List<List<String>> readDataFromCSV (String dataFileName)  throws FileNotFoundException {


        //open file
        Scanner scanner = new Scanner(new File(dataFileName));
        scanner.useDelimiter(DELIMITER);

        // dataset variable stores the data got from the CSV file: 1st row - labels, each next - transaction
        List<List<String>> dataset = new ArrayList();

        //Reading the data line by line and adding to dataset:
        while(scanner.hasNext()){
            String dataLine = scanner.nextLine();
            String[] dataLineArray = dataLine.split(DELIMITER,-1);

            dataset.add(Arrays.asList(dataLineArray));
        }
        scanner.close();

        return dataset;
    }
}

