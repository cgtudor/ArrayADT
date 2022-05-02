package array.adt;

import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This recursive action class serves to perform the dual-pivot parallel quicksort using ForkJoinPool.
 * @author v8002382
 */
public class ParallelQuickSort extends RecursiveAction{
    final int[] array;
    final int lo, hi;
    static final double THRESHOLD = 1<<13;
    
    /**
     * Constructor for providing starting and final indices.
     * @param array Array to be sorted.
     * @param lo Starting index.
     * @param hi Final index.
     */
    ParallelQuickSort(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    /**
     * Standard constructor.
     * @param array Array to be sorted.
     */
    ParallelQuickSort(int[] array) {
        this(array, 0, array.length);
    }
    
    @Override
    protected void compute() {
        if (lo < hi) {
            
            // If the sequential threshhold is hit, perform sequential quicksort for efficiency.
            if (hi - lo <= THRESHOLD) 
            { 
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

    /**
     * Picks random indices to perform partitioning. 
     * @param a The array to be sorted.
     * @param left Left-limit of the partition.
     * @param right Right-limit of the partition.
     * @return An array containing indices of the pivots after partitioning.
     */
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
    
    /**
     * Performs partitioning for a dual-pivot quicksort.
     * @param a The array to be sorted.
     * @param left Left-limit of the partition.
     * @param right Right-limit of the partition.
     * @return An array containing indices of the pivots after partitioning.
     */
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
    
    /**
     * Swap two elements in an array.
     * @param a Array to swap elements in.
     * @param i Index of the first element.
     * @param j Index of the second element.
     */
    static private void swap(int[] a, int i, int j)
    {
        int x;
        x = a[i];
        a[i] = a[j];
        a[j] = x;
    }
}
