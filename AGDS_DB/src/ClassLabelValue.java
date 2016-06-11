import java.util.ArrayList;
import java.util.List;

/**
 * Contains the label name from the dataset and list of object IDs which match it.
 * Created by Varq on 2016-05-06.
 *
 */
public class ClassLabelValue {

    private final String name;
    private List<ObjectID> objectsList;

    //constructor:
    public ClassLabelValue(String name) {
        this.name = name;
        this.objectsList = new ArrayList<>();
    }

    //getters:
    public String getName() {
        return name;
    }
    public List<ObjectID> getObjectsList() {
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
}
