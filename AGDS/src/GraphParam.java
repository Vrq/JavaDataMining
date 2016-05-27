import java.util.*;

/**
 * GraphParam is a node with references to object parameter names and methods for data analysis
 * Created by Varq on 2016-05-05.
 */
public class GraphParam {

    List<ParamName> paramList;
    HashMap<String, ClassLabelValue> labelList;

    //Constructor:
    public GraphParam() {
        this.paramList = new ArrayList<>();
        this.labelList = new HashMap<>();
    }
    //setter:
    public void addParam(ParamName newParamNode) {
        paramList.add(newParamNode);
    }


    //reset object wages:

    public void resetObjectWages() {
        for(Map.Entry<String, ClassLabelValue> classNode : labelList.entrySet()) {
            for(ObjectID obj : classNode.getValue().getObjectsList()) {
                obj.resetObjectWage();
            }
        }
    }
    //find most similar element - returns a sorted list with the winner element first:
    public List<ObjectID> findMostFimilar (String[] testedElement) {
        resetObjectWages();
        HashSet<ObjectID> winnerSet = new HashSet<>();
        for(int paramIndex = 0; paramIndex<paramList.size(); paramIndex++) {
            paramList.get(paramIndex).calculateNeighbourWages(testedElement[paramIndex],winnerSet);
        }
        List<ObjectID> winnerList = new ArrayList<>(winnerSet);
        Collections.sort(winnerList,Collections.<ObjectID>reverseOrder());

        return winnerList;
    }

    //find least similar element - returns a sorted list with the least similar element first:
    public List<ObjectID> findLeastFimilar (String[] testedElement) {
        resetObjectWages();
        HashSet<ObjectID> winnerSet = new HashSet<>();
        for(int paramIndex = 0; paramIndex<paramList.size(); paramIndex++) {
            paramList.get(paramIndex).calculateNeighbourWages(testedElement[paramIndex],winnerSet);
        }
        List<ObjectID> winnerList = new ArrayList<>(winnerSet);
        Collections.sort(winnerList);

        return winnerList;
    }

    //returns objects with similar givenValue of parameter paramIndex
    public List<ObjectID> findWithValues(String[] givenValue, int[] paramIndex) {
        resetObjectWages();
        HashSet<ObjectID> winnerSet = new HashSet<>();
        for(int valueIndex = 0; valueIndex<givenValue.length; valueIndex++) {
            paramList.get(paramIndex[valueIndex]).calculateNeighbourWages(givenValue[valueIndex],winnerSet);
        }

        List<ObjectID> winnerList = new ArrayList<>(winnerSet);
        Collections.sort(winnerList,Collections.<ObjectID>reverseOrder());

        return winnerList;
    }

    //returns objects with exact givenValue of parameter paramIndex
    public List<ObjectID> findExactWithValues(String[] givenValue, int[] paramIndex) {
        resetObjectWages();
        HashSet<ObjectID> winnerSet = new HashSet<>();
        for(int valueIndex = 0; valueIndex<givenValue.length; valueIndex++) {
            paramList.get(paramIndex[valueIndex]).calculateNeighbourWages(givenValue[valueIndex],winnerSet);
        }

        List<ObjectID> winnerList = new ArrayList<>(winnerSet);
        List<ObjectID> exactWinnerList = new ArrayList<>();
        Collections.sort(winnerList,Collections.<ObjectID>reverseOrder());

        for(int objIndex = 0; objIndex<10; objIndex++) {

            Double doubleVal = new Double(winnerList.get(objIndex).getObjectWage());

            if(doubleVal.intValue() == paramIndex.length ) {
                exactWinnerList.add(winnerList.get(objIndex));
            }
        }
        return exactWinnerList;
    }

    public List<ObjectID> findWithinValuesRange (String minValue, String maxValue, int paramIndex) {
        HashSet<ObjectID> winnerSet = new HashSet<>();


        for(ParamValue val : paramList.get(paramIndex).getValueList()) {
            if(Double.parseDouble(val.getValue()) >= Double.parseDouble(minValue) && Double.parseDouble(val.getValue()) <= Double.parseDouble(maxValue)) {
                for(ObjectID obj : val.getObjectsList())
                winnerSet.add(obj);
            }
        }
        List<ObjectID> winnerList = new ArrayList<>(winnerSet);
        Collections.sort(winnerList);

        return winnerList;
    }
}