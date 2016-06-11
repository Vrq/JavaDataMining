import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Varq on 2016-06-04.
 */
public class SOMLattice {



    private int xSize;
    private int ySize;
    private int inputDim;
    public List<List<SOMNode>> boardMap;


    public SOMLattice(int x, int y, int inDim) {
        setxSize(x);
        setySize(y);
        setInputDim(inDim);
        //building the lattice:
        boardMap = new ArrayList<>();
        for(int rowsCount = 0; rowsCount < x; rowsCount++) {
            List<SOMNode> nodesRow = new ArrayList<> ();
            for(int columnsCount = 0; columnsCount < y; columnsCount++) {
                double[] weights = new double[inDim];
                //initializing each weights vector with random (0.0, 1.0)
                for(int weightCount = 0; weightCount < inDim; weightCount++) {
                    weights[weightCount] = Math.random();
                }
                SOMNode newNode = new SOMNode(rowsCount,columnsCount, weights);
                nodesRow.add(newNode);
            }
            boardMap.add(nodesRow);
        }
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public int getInputDim() {
        return inputDim;
    }

    public void setInputDim(int inputDim) {
        this.inputDim = inputDim;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }


    public void teachTheSOM(List<List<String>> dataset) {
        //init: take a radius of ~80% of x
        double neighboorRadiusInit = 0.8*getxSize();
        int alfa = 1000;
        double initWageAdaptation = 1;

        //random permutation of the rows:
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 1; i < dataset.size(); i++) {
            list.add(i);
        }
        java.util.Collections.shuffle(list);

        //take a random row from dataset
        for(int rowCount = 0; rowCount<dataset.size()-1; rowCount++) {
            int randRow = list.get(rowCount);

            //find the best matching node:
            double minDistance = 100000;
            double minX = -1000;
            double minY = -1000;
            for(List<SOMNode> latticeRow : boardMap) {
                for(SOMNode node : latticeRow) {
                    double distance = 0;
                    for(int weightNum = 0; weightNum < inputDim; weightNum++) {
                        double nodeWeight = node.getNodeWeight()[weightNum];
                        double testRowWeight = Double.parseDouble(dataset.get(randRow).get(weightNum));
                        distance += (nodeWeight - testRowWeight)*(nodeWeight - testRowWeight);
                    }
                    if(distance < minDistance) {
                        minDistance = distance;
                        minX = node.getxPos();
                        minY = node.getyPos();
                    }
                    distance = 0;
                }
            }

            //adjust the wages of the nodes in the neighboorhood:
            double currentNeighboorRadius = neighboorRadiusInit*Math.exp(-rowCount/alfa); //smaller with each iteration
            for(List<SOMNode> latticeRow : boardMap) {
                for(SOMNode node : latticeRow) {
                    double distanceFromBestMatch = Math.sqrt((node.getxPos() - minX)*(node.getxPos() - minX) + (node.getyPos() - minY)*(node.getyPos() - minY));
                    if(distanceFromBestMatch < currentNeighboorRadius) {
                        double wageAdaptation = initWageAdaptation*Math.exp(-rowCount/alfa); //smaller with each iteration
                        double distanceAdjustment = Math.exp(-distanceFromBestMatch*distanceFromBestMatch/(2*currentNeighboorRadius*currentNeighboorRadius)); //smaller with the distance and iteration

                        for(int weightNum = 0; weightNum < inputDim; weightNum++) {
                            double currWeight = node.getNodeWeight()[weightNum];
                            node.getNodeWeight()[weightNum] = currWeight + wageAdaptation*distanceAdjustment*(Double.parseDouble(dataset.get(randRow).get(weightNum)) - currWeight);
                        }
                    }
                }
            }


        }

    }
}

