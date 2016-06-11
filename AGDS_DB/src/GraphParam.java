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
    //SQL: SELECT * FROM current_table WHERE paramNumber[i] = paramValue[i] AND paramNumber[i+1] = paramValue[i+1]
    public List<ObjectID> selectWhereAND (int[] paramNumber, String[] paramValue) {
        if(paramNumber.length != paramValue.length) {
            System.out.println("Input arguments must have the same length");
            return null;
        }
        List<ObjectID> queryResult = new ArrayList<>();
        List<ObjectID> tempList = new ArrayList<>();
        //get Objects that match the paramValue[0]:
        for(ParamValue dValue : paramList.get(paramNumber[0]).getValueList()) {
            if(dValue.getValue().equals(paramValue[0])) {
                queryResult = new ArrayList<>(dValue.getObjectsList());
            }
        }

        //if the queryResult is null:
        if(queryResult.size() == 0) {
            System.out.println("No results found");
            return queryResult;
        }

        //check AND with each other parameter:
        for(int paramNumIndex = 1; paramNumIndex<paramNumber.length; paramNumIndex++) {
            for(ObjectID obj : queryResult) {
                if(obj.getValueList().get(paramNumber[paramNumIndex]).getValue().equals(paramValue[paramNumIndex])) {
                    tempList.add(obj);
                }
            }
            queryResult = new ArrayList<>(tempList);
            tempList = new ArrayList<>();

            //if the queryResult is null:
            if(queryResult.size() == 0) {
                System.out.println("No results found");
                return queryResult;
            }

        }

        return queryResult;
    }

    //SQL: SELECT * FROM current_table WHERE paramNumber[i] = paramValue[i] OR paramNumber[i+1] = paramValue[i+1]
    public List<ObjectID> selectWhereOR (int[] paramNumber, String[] paramValue) {
        if(paramNumber.length != paramValue.length) {
            System.out.println("Input arguments must have the same length");
            return null;
        }
        Set<ObjectID> tempSet = new HashSet<>();
        Set<ObjectID> resultSet = new HashSet<>();
        List<ObjectID> queryResult = new ArrayList<>();

        //check OR with each parameter:
        for(int paramNumIndex = 0; paramNumIndex<paramNumber.length; paramNumIndex++) {
            for(ParamValue val : paramList.get(paramNumber[paramNumIndex]).getValueList()) {
                if(val.getValue().equals(paramValue[paramNumIndex])) {
                    tempSet = new HashSet<>(val.getObjectsList());
                    //tutaj jeszcze mozna break albo continue dodac
                }
            }
            resultSet.addAll(tempSet);
        }
        queryResult = new ArrayList<>(resultSet);
        //if the queryResult is null:
        if(queryResult.size() == 0) {
            System.out.println("No results found");
        }
        return queryResult;
    }

    public static void addRecord(String[] objValues, GraphParam AGDS) {
        String newClassName = objValues[objValues.length-1];
        //add new ClassLabelValue node if there is no:
        if(!AGDS.labelList.containsKey(newClassName)) {
            ClassLabelValue newClassLabel = new ClassLabelValue(newClassName);
            AGDS.labelList.put(newClassName,newClassLabel);
        }
        ClassLabelValue currentClassLabel = AGDS.labelList.get(newClassName);
        //add new ObjectID record node:
        ObjectID newObject = new ObjectID("U" + objValues[0]);
        newObject.setClassName(currentClassLabel);
        currentClassLabel.addObject(newObject);

        //add the parameter values to ParamValue nodes
        List<ParamValue> newValueList = new ArrayList<>();
        for(int paramIndex = 1; paramIndex<objValues.length-1; paramIndex++){
            ParamValue newValue = new ParamValue(objValues[paramIndex]);
            newValue.addObject(newObject);
            AGDS.paramList.get(paramIndex-1).addValue(newValue);
            newValueList.add(newValue);
        }
        newObject.setValueList(newValueList);
        return;
    }
}