/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.adt;

import static array.adt.ArraySort.count;
import static java.lang.Math.pow;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author crist
 */
public class ParallelMergeSort extends RecursiveAction {

    final int[] array;
    final int lo, hi;
    static final double THRESHOLD = 1<<13;
    ParallelMergeSort(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    ParallelMergeSort(int[] array) {
        this(array, 0, array.length);
    }
    
    @Override
    protected void compute() {
        if (lo < hi) {
            if (hi - lo <= THRESHOLD) 
            { // Sequential implementation
                ArraySort.mergeSort(array, lo, hi);
            } 
            else 
            { // Parallel implementation
                final int middle = (lo + hi) / 2;
                final ParallelMergeSort left
                        = new ParallelMergeSort(array, lo, middle);
                final ParallelMergeSort right
                        = new ParallelMergeSort(array, middle + 1, hi);
                invokeAll(left, right);
                merge(array, lo, middle, hi);
            }
        }
    }    

    private void merge(int[] a, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for(int i = 0; i < n1; i++)
            L[i] = a[l+i];
        for(int i = 0; i < n2; i++)
            R[i] = a[m+1+i];
        int i = 0, j = 0;
        int k = l;
        while(i < n1 && j < n2)
        {
            count++;
            if(L[i] <= R[j])
            {
                a[k] = L[i];
                i++;
            }
            else
            {
                a[k] = R[j];
                j++;
            }
            k++;
        }
        while(i < n1)
        {
            a[k] = L[i];
            i++;
            k++;
        }
        while(j < n2)
        {
            a[k] = R[j];
            k++;
            j++;
        }
    }
}
