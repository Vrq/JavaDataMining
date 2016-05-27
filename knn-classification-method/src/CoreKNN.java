import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;


public class CoreKNN {
	
	public static ArrayList<List<String>> sortDataset (double [] newElement, ArrayList<List<String>> dataset) {
		// returns a matrix with objects (flowers) in each row with distance value added

		//sortedDataset is a 2d matrix with distance added and sorted by it
		ArrayList<List<String>> sortedDataset = new ArrayList ();

		for(int i = 1; i<dataset.get(0).size(); i++) {
				
			double[] datasetElement = new double[dataset.size()-1];  // for parameter values from each row of the dataset
			
			//get parameter values from a row  - one flower
			for(int j = 0; j<dataset.size()-1;j++) {
			datasetElement[j] = Double.parseDouble(dataset.get(j).get(i));	
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
			flowerWithDistance.add(dataset.get(dataset.size()-1).get(i));

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

		final int INDEX_OF_LABEL = 5; //in sortedDataset flower name is on the last position - index = 5
		List<String> kFirstElements = new ArrayList();
		for(int i = 0; i<k; i++) {
			kFirstElements.add(sortedDataset.get(i).get(INDEX_OF_LABEL));
		}
		
		//Using HashMap to remove the duplicates
		Map<String, Integer> elementCount = new HashMap();
		for(int i = 0; i<k; i++) {
			elementCount.put(kFirstElements.get(i), Collections.frequency(kFirstElements, kFirstElements.get(i)));
		}
		
		//Using ArrayList to get rows: "Flower class"->"Frequency" and sort it by the frequency value
		ArrayList<List<String>> sortedFrequencyList = new ArrayList();
		
		for (Map.Entry<String, Integer> entry : elementCount.entrySet()) {
			List<String> eachRow = new ArrayList<String>();
		    eachRow.add(entry.getKey());
		    eachRow.add(Integer.toString(entry.getValue()));
		    
		    sortedFrequencyList.add(eachRow);
		}
		
		//Sorting the ArrayList descending:
		Collections.sort(sortedFrequencyList, new Comparator <List<String>>() {
			@Override
			public int compare(List<String> o1, List<String> o2) {
				return Integer.valueOf(o2.get(1)).compareTo(Integer.valueOf(o1.get(1)));
			}
		});
		
	
		for (Entry<String, Integer> entry : elementCount.entrySet()) {
		     System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
		}

		 System.out.println("--------");
		
		
		
		return sortedFrequencyList; // name of the most frequent flower
	}





	public static void main (String[] args) throws FileNotFoundException {
		ArrayList<List<String>> dataset = DataReaderCSV.readDataFromCSV();
		double[] tested = {10.1, 3.5, 2.4, 0.6};
		ArrayList<List<String>> distanceArray = CoreKNN.sortDataset(tested, dataset);
		int k = 20;
		
		System.out.println(dataset.get(0).size());

		ArrayList<List<String>> sortedBest = CoreKNN.findBestClass(distanceArray, k);
		
		System.out.println("The: " + k + " nearest neighbours of the tested element are:");
		for(List object: sortedBest) {
			System.out.println(object);
		}
		
		System.out.println("Best match: " + sortedBest.get(0).get(0));


	}

}
