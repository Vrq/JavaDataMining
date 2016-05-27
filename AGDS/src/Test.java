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

            /*
            //Print the parameter label names:
            for(int paramIndex = 0; paramIndex<AGDS.paramList.size(); paramIndex++) {
                System.out.println(AGDS.paramList.get(paramIndex).getName());
            }


            //Print the values of the object label parameter:
            for(Map.Entry<String, ClassLabelValue> label : AGDS.labelList.entrySet()) {
                System.out.println(label.getKey());
            }

            //Print a random record name:
            System.out.println(AGDS.paramList.get(0).getValueList().get(10).getObjectsList().get(0).getName());
            */
            //Sort the values of a parameter and print them:
            AGDS.paramList.get(0).sortValues();

            for(int valueIndex = 0; valueIndex<AGDS.paramList.get(0).getValueList().size(); valueIndex++) {
                System.out.println(AGDS.paramList.get(0).getValueList().get(valueIndex).getValue());
            }

            //Print the MIN and MAX values:
                System.out.println("MAX " + AGDS.paramList.get(0).getMaxValue().getValue());
                System.out.println("MIN " + AGDS.paramList.get(0).getMinValue().getValue());







            String[] testedElement = {"6.2", "3.4", "5.4", "2.3"};
            List<ObjectID> winnersList = AGDS.findMostFimilar(testedElement);
            List<ObjectID> losersList = AGDS.findLeastFimilar(testedElement);

            for(int i = 0; i<3; i++) {
                System.out.println(winnersList.get(i).getName() + " " + winnersList.get(i).getObjectWage());
            }
            System.out.println("Least similar top3: ");
            for(int i = 0; i<3; i++) {
                System.out.println(losersList.get(i).getName() + " " + losersList.get(i).getObjectWage());
            }

            String[] values = {"3.5","0.2"};
            int[] paramIndices = {1,3};
            List<ObjectID> similarValues = AGDS.findWithValues(values,paramIndices);
            System.out.println("Objects with 3.5 leaf-width and 0.2 petal-width:");
            for(int i = 0; i<10; i++) {
                System.out.println(similarValues.get(i).getName() + " " + similarValues.get(i).getObjectWage());
            }

            List<ObjectID> exactValues = AGDS.findExactWithValues(values,paramIndices);
            System.out.println("Objects with exact 3.5 leaf-width and 0.2 petal-width:");
            for(int i = 0; i<exactValues.size(); i++) {
                System.out.println(exactValues.get(i).getName() + " " + exactValues.get(i).getObjectWage());
            }

            String minR = "4.6";
            String maxR = "4.8";
            int paramInd = 0;

            List<ObjectID> inRangeValues = AGDS.findWithinValuesRange(minR,maxR,paramInd);
            for(ObjectID obj : inRangeValues) {
                System.out.println(obj.getName());
            }

        } catch(FileNotFoundException e) {
            System.err.println("FileNotFoundException:" + e.getMessage());
        };




    }
}
