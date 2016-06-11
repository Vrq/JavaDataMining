/**
 * Created by Varq on 2016-06-04.
 */
public class SOMNode {
    private double[] nodeWeight;
    private int xPos;
    private int yPos;


    public SOMNode(int xVal, int yVal, double[] weights) {
        setxPos(xVal);
        setyPos(yVal);
        setNodeWeight(weights);
    }
    public double[] getNodeWeight() {
        return nodeWeight;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setNodeWeight(double[] newWeights) {

        nodeWeight = newWeights;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}
