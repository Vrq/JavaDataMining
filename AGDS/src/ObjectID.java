import java.util.ArrayList;
import java.util.List;

/**
 * Contains object IDs (an object can be for example a flower name with its physical parameters) and the list of values
 * Created by Varq on 2016-05-05.
 */
public class ObjectID implements Comparable<ObjectID> {

    private final String name;
    private List<ParamValue> valueList;
    private ClassLabelValue className;
    private Double objectWage = 0.0d;

    //constructor:
    public ObjectID(String name) {
        this.name = name;
        this.valueList = new ArrayList<>();
    }

    //getters:
    public String getName() {
        return name;
    }
    public List<ParamValue> getValueList(){
        return valueList;
    }
    public double getObjectWage() {
        return objectWage;
    }
    public ClassLabelValue getClassName() { return className;}

    //setters:
    public void setClassName(ClassLabelValue newClassLabel) {
        this.className = newClassLabel;
    }

    public void setValueList(List<ParamValue> newValueList) {
        this.valueList = newValueList;
    }

    //Add to wage the objectWage sum:
    public void addToObjectWage(double wage) {
        this.objectWage+= wage;
    }

    public void resetObjectWage() {
        this.objectWage = 0.0d;
    }

    //Override compareTo for sorting:
    @Override
    public int compareTo(ObjectID obj2) {
        return objectWage.compareTo(obj2.getObjectWage());
    }
}
