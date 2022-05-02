/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.adt;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This file runs the Three-Pivot Parallel Quicksort against the 
 * Dual-Pivot Parallel Quicksort.
 * @author v8002382
 */
public class TPPQuicksort
{
    private static final int NUMBER_OF_TESTS = 100;
    private static final int ARRAY_SIZE = 5000000;
    private static final int ARRAY_RANGE = 10000;

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
            
            // A variable holds the start time of the TPPQuicksort on the first array.
            // After the sort is finished, another variable grabs the current time.
            // The time consumed is the difference between the two.
            long tStart = System.currentTimeMillis();
            dppQuicksort.experimentalHyperQuickSort();
            long tEnd = System.currentTimeMillis();
            long yDelta = tEnd - tStart;
            
            // The computational time is added to the TPP sum for averaging.
            dppSum += yDelta;
            
            // Same process is applied to the second array, but with the DPPQuicksort.
            tStart = System.currentTimeMillis();
            dpQuicksort.hyperQuickSort();
            tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            dpSum += tDelta;
        }
        
        // After all tests are ran, we display the average computational time of each.
        System.out.println("Three-Pivot Parallel Quicksort: " + dppSum / NUMBER_OF_TESTS);
        System.out.println("Dual-Pivot Parallel Quicksort: " + dpSum / NUMBER_OF_TESTS);
    }
}
