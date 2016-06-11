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
		
	


		 System.out.println("--------");
		
		
		
		return sortedFrequencyList; // name of the most frequent flower
	}





	public static void main (String[] args) throws FileNotFoundException {

		Scanner reader = new Scanner(System.in);
		reader.useLocale(Locale.ENGLISH);
		System.out.println("Press enter to begin ");
		String option = reader.nextLine();
		int userVal;
		double[] userTestRecord = new double[4];
		ArrayList<List<String>> dataset = DataReaderCSV.readDataFromCSV();
		int datasetSize = dataset.get(0).size() - 1;
		while(!(option.equals("q"))) {

			System.out.print("\n");
			System.out.println("#---Dataset: IrisDataAll.csv---#");
			System.out.println("#---Size: " + datasetSize + " records---#");
			System.out.print("\n");
			System.out.println("Choose an option from the menu. Enter 'q' to quit. ");
			System.out.println("1 - Print the whole dataset");
			System.out.println("2 - Print choosen record");
			System.out.println("3 - Calculate the best fit for a test record");

			option = reader.nextLine();

			switch (option) {
				case "1":
					// printing the dataset in equal width columns:
					for (int i = 0; i < dataset.get(0).size(); i++) {
						for (int j = 0; j < dataset.size(); j++) {
							System.out.printf("%-10s", dataset.get(j).get(i));
						}
						System.out.print("\n");
					}
					System.out.print("\n");
					System.out.print("\n");
					break;
				case "2":
					//printing the record with the number choosen by the user:
					System.out.println("Please type in the record number (between 1 and " + datasetSize + ")");
					userVal = reader.nextInt();
					reader.nextLine();
					System.out.println("Record with index: " + userVal);
					for(int paramIndex = 0; paramIndex<dataset.size(); paramIndex++) {
						System.out.printf("%-10s", dataset.get(paramIndex).get(userVal));
					}
					System.out.print("\n");
					System.out.print("\n");
					break;
				case "3":
					System.out.println("Please type in the values of the test record: ");
					System.out.println(dataset.get(0).get(0));
					userTestRecord[0] = reader.nextDouble();
					System.out.println(dataset.get(1).get(0));
					userTestRecord[1] = reader.nextDouble();
					System.out.println(dataset.get(2).get(0));
					userTestRecord[2] = reader.nextDouble();
					System.out.println(dataset.get(3).get(0));
					userTestRecord[3] = reader.nextDouble();
					System.out.println("Choose the k for the kNN method:");
					userVal = reader.nextInt();
					reader.nextLine();
					ArrayList<List<String>> distanceArray = CoreKNN.sortDataset(userTestRecord, dataset);
					ArrayList<List<String>> sortedBest = CoreKNN.findBestClass(distanceArray, userVal);
					System.out.println("The: " + userVal + " nearest neighbours of the tested element are:");
					for(List object: sortedBest) {
						System.out.println(object);
					}

					System.out.println("Best match: " + sortedBest.get(0).get(0));
					System.out.print("\n");
					System.out.print("\n");
					break;
				default:
					break;
			}

		}




	}

}
