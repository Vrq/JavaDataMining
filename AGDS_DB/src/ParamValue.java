import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * ParamValue contains the value of the parameter and the reference to the parameter name and the object ID
 * Created by Varq on 2016-05-05.
 */
public class ParamValue implements Comparable<ParamValue> {

    private final String value;
    private double wageFactor;
    //objects that it points out to:
    private List<ObjectID> objectsList;

    //constructor:
    public ParamValue(String value) {
        this.value = value;
        this.objectsList = new ArrayList<>();
    }

    //getters:
    public String getValue() {
        return value;
    }
    public List<ObjectID> getObjectsList(){
        return objectsList;
    }

    //setters:
    public void addObject(ObjectID newObject) {
        //check if the object was not already added:
        for(ObjectID obj : objectsList) {
            if(obj.getName().equals(newObject.getName())) {
                return;
            }
        }
        objectsList.add(newObject);
    }

    public void setWageFactor(double wageFactor) {
        this.wageFactor = wageFactor;
    }

    //Add wage to connected objects:
    public void giveWageToObject(double wage, HashSet<ObjectID> bestObjects) {
        setWageFactor(wage);
        for(ObjectID obj : objectsList) {
            obj.addToObjectWage(wage);
            bestObjects.add(obj);
        }
    }

    //override compare for sorting:
    @Override
    public int compareTo(ParamValue o1) {
        return value.compareTo(o1.getValue());
    }


}
