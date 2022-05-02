/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.adt;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This file runs the Dual-Pivot Parallel Quicksort against the standard 
 * Dual-Pivot Quicksort.
 * @author v8002382
 */
public class DPPQuicksort
{
    private static final int NUMBER_OF_TESTS = 100;
    private static final int ARRAY_SIZE = 1000000;
    private static final int ARRAY_RANGE = 100000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        long dppSum = 0, dpSum = 0;
        
        // We run a number of tests to get an average.
        for (int o = 0; o < NUMBER_OF_TESTS; o++) 
        {
            
            // We initialize the empty arrays.
            ADT dppQuicksort = new ADT();
            ADT dpQuicksort = new ADT();
            
            // Random numbers in the decided range are picked, up to the decided 
            // size of array and added to both arrays.
            for (int i = 0; i < ARRAY_SIZE; i++) 
            {
                int randomNum = ThreadLocalRandom.current().nextInt(0, ARRAY_RANGE);
                dppQuicksort.add(randomNum);
                dpQuicksort.add(randomNum);
            }
            
            // A variable holds the start time of the DPPQuicksort on the first array.
            // After the sort is finished, another variable grabs the current time.
            // The time consumed is the difference between the two.
            long tStart = System.currentTimeMillis();
            dppQuicksort.hyperQuickSort();
            long tEnd = System.currentTimeMillis();
            long yDelta = tEnd - tStart;
            
            // The computational time is added to the DPP sum for averaging.
            dppSum += yDelta;
            
            // Same process is applied to the second array, but with the DPQuicksort.
            tStart = System.currentTimeMillis();
            dpQuicksort.optimizedQuickSort();
            tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            dpSum += tDelta;
        }
        
        // After all tests are ran, we display the average computational time of each.
        System.out.println("Dual-Pivot Parallel Quicksort: " + dppSum / NUMBER_OF_TESTS);
        System.out.println("Dual-Pivot Quicksort: " + dpSum / NUMBER_OF_TESTS);
    }
}
