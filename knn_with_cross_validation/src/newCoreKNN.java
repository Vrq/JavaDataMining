import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;


public class newCoreKNN {

    public static ArrayList<List<String>> sortDataset (double [] newElement, List<List<String>> dataset) {
        // returns a matrix with objects (flowers) in each row with distance value added

        //sortedDataset is a 2d matrix with distance added and sorted by it
        ArrayList<List<String>> sortedDataset = new ArrayList ();

        for(int i = 1; i<dataset.size(); i++) {

            double[] datasetElement = new double[dataset.get(0).size()-1];  // for parameter values from each row of the dataset

            //get parameter values from a row  - one flower
            for(int j = 0; j<dataset.get(0).size()-1;j++) {
                datasetElement[j] = Double.parseDouble(dataset.get(i).get(j));
            }

            //calculate the distance from current flower
            double currentDistance = (DistanceCalculator.calculateEuclides(newElement, datasetElement));

            //create new row with distance added
            List<String> flowerWithDistance = new ArrayList<String>();

            //add distance
            flowerWithDistance.add(Double.toString(currentDistance));

            //add parameters
            for(int k = 0; k<datasetElement.length; k++) {
                flowerWithDistance.add(Double.toString(datasetElement[k]));
            }
            //add flower name
            flowerWithDistance.add(dataset.get(i).get(dataset.get(0).size()-1));

            //add the flower to the new dataset:
            sortedDataset.add(flowerWithDistance);
        }

        //sort the dataset basing on the distance (therefore a custom Comparator)
        Collections.sort(sortedDataset, new Comparator <List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return o1.get(0).compareTo(o2.get(0));
            }
        });

        return sortedDataset;
    }


    public static ArrayList<List<String>> findBestClass (ArrayList<List<String>> sortedDataset, int k) {
        //returns a matrix with object name (flower class) and frequency of it in the 'k' neighbourhood, sorted desc

        int labelIndex = sortedDataset.get(0).size()-1; //in sortedDataset flower name is on the last position - index = 5
        List<String> kFirstElements = new ArrayList();
        for(int i = 0; i<k; i++) {
            kFirstElements.add(sortedDataset.get(i).get(labelIndex));
        }

        //Using HashMap to remove the duplicates
        Map<String, Integer> elementCount = new HashMap();
        for(int i = 0; i<k; i++) {
            elementCount.put(kFirstElements.get(i), Collections.frequency(kFirstElements, kFirstElements.get(i)));
        }

        // for each flower class calculate the value of a normalized vote: classVote = (sum of distances for a class)/(number of flowers of a class in kNN)
        double classVote = 0;
        double distanceSum = 0;
        ArrayList<List<String>> sortedVoteList = new ArrayList(); // each row = [class name, class frequency in kNN, normalized vote]
        for (Map.Entry<String, Integer> entry : elementCount.entrySet()) {
            for(int flowerIndex = 0; flowerIndex<k; flowerIndex++) {
                if(entry.getKey().equals(sortedDataset.get(flowerIndex).get(labelIndex))) {
                    distanceSum += Double.parseDouble(sortedDataset.get(flowerIndex).get(0));

                }

            }

            classVote = distanceSum/entry.getValue();
            List<String> eachRow = new ArrayList<String>();
            eachRow.add(entry.getKey());
            eachRow.add(Integer.toString(entry.getValue()));
            eachRow.add(Double.toString(classVote));
            sortedVoteList.add(eachRow);

            distanceSum = 0;
        }

        //Sorting the vote list ascending:
        Collections.sort(sortedVoteList, new Comparator <List<String>>() {
            @Override
            public int compare(List<String> o1, List<String> o2) {
                return Double.valueOf(o1.get(2)).compareTo(Double.valueOf(o2.get(2)));
            }
        });

        return sortedVoteList; // name of the most frequent flower
    }

    public static ArrayList<List<String>> getWinnersForEveryK(double[] testElement, List<List<String>> dataset) {

        ArrayList<List<String>> sortedDataset = sortDataset(testElement, dataset);
        ArrayList<List<String>> winnerList = new ArrayList();
        for(int kNumber = 1; kNumber<dataset.size(); kNumber++) {
            //call the findBestClass function to get the winner for a specific k
            ArrayList<List<String>> sortedVoteList = new ArrayList(findBestClass(sortedDataset, kNumber));
            List<String> eachRow = new ArrayList<String>();
            eachRow.add(Integer.toString(kNumber)); //add the k-value
            eachRow.add(sortedVoteList.get(0).get(0)); //add the winner for this k
            winnerList.add(eachRow);
        }

        return winnerList;
    }

    //performs the leave one out cross-validation, in which each single element of the dataset is used for validation, while the others for the model learning
    public static ArrayList<List<String>> crossValidate(List<List<String>> dataset) {

        ArrayList<List<String>> winnerList = new ArrayList();
        int[] correctForEachK = new int[dataset.size()-1];
        ArrayList<List<String>> validatedK = new ArrayList();
        int labelIndex = dataset.get(0).size()-1;

        //take each flower:
        for(int flowerIndex = 1; flowerIndex<dataset.size(); flowerIndex++) {

            //parameters of the validated element in double[]:
            double[] flowerParams = new double[dataset.get(0).size()-1];
            for(int paramIndex = 0; paramIndex<dataset.get(0).size()-1; paramIndex++) {
                flowerParams[paramIndex] = Double.parseDouble(dataset.get(flowerIndex).get(paramIndex));
            }

            //create a new dataset without the element that is validated:
            ArrayList<List<String>> learningDataset = new ArrayList(dataset);
            learningDataset.remove(flowerIndex);

            //tutaj nam sie zlozonosc n^2 robi i widac zaczyna zamulać - dla zbioru 10000+ byloby ciezko
            winnerList = getWinnersForEveryK(flowerParams, dataset);

            //if the classified item class matches the validator then increment the correct answers count for k value
            for(int kValue=0; kValue<winnerList.size(); kValue++) {
                if(dataset.get(flowerIndex).get(labelIndex).equals(winnerList.get(kValue).get(1))) {
                    correctForEachK[kValue] += 1;
                }
            }
        }
        //add the k-values with correct percentage guesses to the return variable:
        for(int kValue = 1; kValue<dataset.size(); kValue++) {
            List<String> eachRow = new ArrayList<String>();
            eachRow.add(Integer.toString(kValue));
            double correctPercentage = 1.0*correctForEachK[kValue-1]/(dataset.size()-1);
            eachRow.add(Double.toString(correctPercentage));

            validatedK.add(eachRow);
        }

        return validatedK;
    }


}