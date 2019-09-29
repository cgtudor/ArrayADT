/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.adt;

import static array.adt.ArraySort.count;
import static array.adt.ArraySort.optimizedQuickSort;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author crist
 */
public class ParallelQuickSort extends RecursiveAction{
    final int[] array;
    final int lo, hi;
    static final double THRESHOLD = 1<<13;
    ParallelQuickSort(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    ParallelQuickSort(int[] array) {
        this(array, 0, array.length);
    }
    
    @Override
    protected void compute() {
        if (lo < hi) {
            if (hi - lo <= THRESHOLD) 
            { // Sequential implementation
                ArraySort.optimizedQuickSort(array, lo, hi);
            } 
            else 
            {
                int[] pi = randomDualPartition(array,lo,hi);
                 final ParallelQuickSort left
                        = new ParallelQuickSort(array,lo,pi[0]-1);
                final ParallelQuickSort middle
                        = new ParallelQuickSort(array,pi[0]+1,pi[1]-1);
                final ParallelQuickSort right
                        = new ParallelQuickSort(array,pi[1]+1,hi);
                invokeAll(left, middle, right);
            }
        }
    }    

    static private int[] randomDualPartition(int[] a, int left, int right)
    {
        int randomNum = ThreadLocalRandom.current().nextInt(left, right + 1);
        swap(a,randomNum,right);
        int randomNum2 = ThreadLocalRandom.current().nextInt(left, right + 1);
        while(randomNum2 == randomNum)
            randomNum2 = ThreadLocalRandom.current().nextInt(left, right + 1);
        swap(a,randomNum2,left);
        return dualPivotPartition(a,left,right);
    }
     static private int[] dualPivotPartition(int[] a, int left, int right)
    {
        int[] pivots = new int[2];
        if(a[left] > a[right])
            swap(a,left,right);
        int pivotR = a[right];
        int pivotL = a[left];
        int less = left + 1;
        int great = right - 1;
        for (int k = less; k <= great; k++) {
            if (a[k] < pivotL) {
                swap(a, k, less++);
            } else if (a[k] > pivotR) {
                while (k < great && a[great] > pivotR) {
                    great--;
                }
                swap(a, k, great--);
                if (a[k] < pivotL) {
                    swap(a, k, less++);
                }
            }
        }
        swap(a, less - 1, left);
        swap(a, great + 1, right);
        pivots[0] = less-1;
        pivots[1] = great+1;
        return pivots;
    }
     static private void swap(int[] a, int i, int j)
    {
        int x;
        x = a[i];
        a[i] = a[j];
        a[j] = x;
    }
}
