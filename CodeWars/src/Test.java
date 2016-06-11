/**
 * Created by Varq on 2016-06-09.
 */
public class Test {
    public static void main(String[] args) {

        long[] test1 = Highest2Factors.highestBiPrimeFac(2,3,50);
        long[] test2 = Highest2Factors.highestBiPrimeFac(5,11,1000);
        long[] test3 = Highest2Factors.highestBiPrimeFac(3,13,5000);

        System.out.println(test1[0] +" "+test1[1] +" "+ test1[2]);
        System.out.println(test2[0] +" "+test2[1] +" "+ test2[2]);
        System.out.println(test3[0] +" "+test3[1] +" "+ test3[2]);
    }

}
