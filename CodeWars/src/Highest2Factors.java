/**
 * 6 kyu - Highest number with two prime factors
 *
 * Created by Varq on 2016-06-09.
 */

public class Highest2Factors {

    public static long[] highestBiPrimeFac(long p1, long p2, long n) {

        long p1Counter = 0;
        long p2Counter = 0;
        long bestp1Counter = 0;
        long bestp2Counter = 0;
        long highestPrimeFac = 0;
        for(long firstPrimePart = p1; firstPrimePart<n; firstPrimePart *= p1) {
            p1Counter += 1;
            p2Counter = 0;
            for(long secondPrimePart = p2; secondPrimePart*firstPrimePart<n; secondPrimePart *= p2) {
                p2Counter += 1;
                if(firstPrimePart*secondPrimePart > highestPrimeFac) {
                    highestPrimeFac = firstPrimePart*secondPrimePart;
                    bestp1Counter = p1Counter;
                    bestp2Counter = p2Counter;
                }
            }
        }
        long[] result = {highestPrimeFac, bestp1Counter, bestp2Counter};
        return result;
    }
}


