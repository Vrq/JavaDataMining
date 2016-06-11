import java.util.*;

/**
 * ParamName contains the label of the parameter and the references to nodes with its values
 * Created by Varq on 2016-05-05.
 */
public class ParamName {

    private final String name;
    private List<ParamValue> valueList;
    private ParamValue minValue;
    private ParamValue maxValue;

    //constructor:
    public ParamName(String name) {
        this.name = name;
        this.valueList = new ArrayList<>();
    }

    //getters:
    public String getName() {
        return name;
    }
    public List<ParamValue> getValueList() {
        return valueList;
    }
    public ParamValue getMinValue() {
        return minValue;
    }
    public ParamValue getMaxValue() {
        return maxValue;
    }

    //setter:
    public void addValue(ParamValue newValueNode) {
        // 1st - check if the value already exists
        for(ParamValue valObj : valueList) {
            if(valObj.getValue().equals(newValueNode.getValue())) {
                valObj.getObjectsList().addAll(newValueNode.getObjectsList()); //if yes - add the ObjectID to the existing value
                return;
            }
        }
        valueList.add(newValueNode);
    }


    public void sortValues() {
        Collections.sort(valueList);
        assignMinValue();
        assignMaxValue();
    }

    public void assignMinValue() {
        minValue = valueList.get(0);
    }

    public void assignMaxValue() {
        maxValue = valueList.get(valueList.size() - 1);
    }

    //calculate the wages for the values and add them up to records:
    public void calculateNeighbourWages(String testValue, HashSet<ObjectID> bestObjects) {
        sortValues();
        double maxVal = Double.parseDouble(testValue) > Double.parseDouble(getMaxValue().getValue()) ? Double.parseDouble(testValue) : Double.parseDouble(getMaxValue().getValue());
        double minVal = Double.parseDouble(testValue) < Double.parseDouble(getMinValue().getValue()) ? Double.parseDouble(testValue) : Double.parseDouble(getMinValue().getValue());
        double valueRange = maxVal - minVal;

        for(ParamValue valueNode : valueList) {
            double wageValue = 1 - Math.abs(Double.parseDouble(valueNode.getValue()) - Double.parseDouble(testValue))/valueRange;
            valueNode.giveWageToObject(wageValue,bestObjects);
        }
    }
}
