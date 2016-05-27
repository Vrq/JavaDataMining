import java.util.ArrayList;
import java.util.List;

/**GraphConverter contains methods which convert a dataset in table to a graph (AGDS) form
 * Created by Varq on 2016-05-05.
 */
public class GraphConverter {

    public static GraphParam convertTableToGraph(List<List<String>> dataset){
        GraphParam graphDS = new GraphParam();
        final int OBJECT_ROW_SIZE = dataset.get(0).size() - 1;

        //adding the parameter names nodes to the graph:
        for(int labelIndex = 0; labelIndex<OBJECT_ROW_SIZE; labelIndex++){
            ParamName newParamName = new ParamName(dataset.get(0).get(labelIndex));
            graphDS.addParam(newParamName);
        }

        for(int objectIndex = 1; objectIndex<dataset.size(); objectIndex++){
            List<String> objectRow = dataset.get(objectIndex);
            String newClassName = objectRow.get(OBJECT_ROW_SIZE);

            //add new ClassLabelValue node if there is no:
            if(!graphDS.labelList.containsKey(newClassName)) {
                ClassLabelValue newClassLabel = new ClassLabelValue(newClassName);
                graphDS.labelList.put(newClassName,newClassLabel);
            }

            ClassLabelValue currentClassLabel = graphDS.labelList.get(newClassName);
            //add new ObjectID record node:
            ObjectID newObject = new ObjectID("R"+objectIndex);
            newObject.setClassName(currentClassLabel);
            currentClassLabel.addObject(newObject);

            //add the parameter values to ParamValue nodes
            List<ParamValue> newValueList = new ArrayList<>();
            for(int paramIndex = 0; paramIndex<OBJECT_ROW_SIZE; paramIndex++){
                ParamValue newValue = new ParamValue(objectRow.get(paramIndex));
                newValue.addObject(newObject);
                graphDS.paramList.get(paramIndex).addValue(newValue);
                newValueList.add(newValue);
            }

            newObject.setValueList(newValueList);
        }

        return graphDS;
    }

}
