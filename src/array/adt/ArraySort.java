package array.adt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This static class serves as a helper for the various sorting methods implemented.
 * @author v8002382
 */
public class ArraySort {
    public static int count = 0;
    
    /**
     * Optimized quicksort using three pivots.
     * @param a The array to sort.
     * @param left The initial starting index.
     * @param right The initial final index.
     */
    static public void optimized3QuickSort(int[] a, int left, int right)
    {
        if(left < right)
        {
            
            // An initial swap of one element is performed to avoid worst case scenarios.
            // If the array is sorted after the swap, we avoid having to run the sort again.
            for (int i = left; i < right; i++) 
            {
                if (a[i] > a[i + 1]) 
                {
                    swap(a, i, i + 1);
                }
            }
            boolean sorted = true;
            for (int i = left; i < right; i++) 
            {
                if (a[i] > a[i + 1]) 
                {
                    sorted = false;
                }
            }
            if (sorted) 
            {
                return;
            }
            
            // If the array is too small, we make use of insertion sort as it is more efficient.
            if(right - left <= 27)
            {
                insertionSort(a,left,right);               
            }
            else
            {
                int[] pi = random3Partition(a,left,right);
                optimized3QuickSort(a,left,pi[0]-1);
                optimized3QuickSort(a,pi[0]+1,pi[1]-1);
                optimized3QuickSort(a,pi[1]+1,pi[2]-1);
                optimized3QuickSort(a,pi[2]+1,right);
            }
        }
    }
    
    /**
     * Pick three random pivots and swap them to the right places before performing the partitioning.
     * @param a Array to sort.
     * @param left Left-limit of the partition.
     * @param right Right-limit of the partition.
     * @return An array containing the final picked pivots.
     */
    static private int[] random3Partition(int[] a, int left, int right)
    {
        int randomNum = ThreadLocalRandom.current().nextInt(left, right + 1);
        swap(a,randomNum,right);
        int randomNum2 = ThreadLocalRandom.current().nextInt(left, right);
        swap(a,randomNum2,left);
        int randomNum3 = ThreadLocalRandom.current().nextInt(left+1, right);
        swap(a,randomNum3,left + 1);
        if (a[left+1] < a[left]) 
        { 
            int t = a[left+1]; 
            a[left+1] = a[left]; 
            a[left] = t; 
        }
        if (a[right] < a[left+1]) 
        { 
            int t = a[right]; 
            a[right] = a[left+1]; 
            a[left+1] = t;
            if (t < a[left]) 
            { 
                a[left+1] = a[left]; 
                a[left] = t; 
            }
        }
        return basic3PivotPartition(a,left,right);
    }
    
    /**
     * Performs the partition, swapping the elements between the three pivots previously randomized.
     * @param A Array to sort.
     * @param left Left-limit of the partition.
     * @param right right Right-limit of the partition.
     * @return An array containing the final pivot indices after partitioning.
     */
     static private int[] basic3PivotPartition(int[] A, int left, int right)
    {
        int p = A[left];
        int q = A[left+1];
        int r = A[right];
        int a = left + 2;
	int b = left + 2;
	int c = right - 1;
	int d = right - 1;
				
	while (b <= c) {
	    while (A[b] < q && b <= c) {
		if (A[b] < p) {
		    swap(A, a, b);
		    a++;
		}
		b++;
	    }
	    while (A[c] > q && b <= c) {
		if (A[c] > r) {
		    swap(A, c, d);
		    d--;
		}
		c--;
	    }
	    if (b <= c) {
		if (A[b] > r) {
		    if (A[c] < p) {
			swap(A, b, a); swap(A, a, c);
			a++;
		    } else {
			swap(A, b, c);
		    }
		    swap(A, c, d);
		    b++; c--; d--;
		} else {
		    if (A[c] < p) {
			swap(A, b, a); swap(A, a, c);
			a++;
		    } else {
			swap(A, b, c);
		    }
		    b++; c--;
		}
	    }
	}
	// swap the pivots to their correct positions
	a--; b--; c++; d++;
	swap(A, left + 1, a); 
        swap(A, a, b);
	a--;
	swap(A, left, a);
	swap(A, right, d);
        int[] pivots = new int[3];
        pivots[0] = a;
        pivots[1] = b;
        pivots[2] = d;
        return pivots;
    }
     
     /**
      * Performs an optimized dual-pivot quicksort.
      * @param a The array to be sorted.
      * @param left First index of the array.
      * @param right Last index of the array.
      */
    static public void optimizedQuickSort(int[] a, int left, int right)
    {
        if(left < right)
        {
            
            // If the array is too small, we make use of insertion sort as it is more efficient.
            if(right - left <= 27)
            {
                insertionSort(a,left,right);
            }
            else
            {
                int[] pi = randomDualPartition(a,left,right);
                optimizedQuickSort(a,left,pi[0]-1);
                optimizedQuickSort(a,pi[0]+1,pi[1]-1);
                optimizedQuickSort(a,pi[1]+1,right);
            }
        }
    }
    
    /**
     * Performs an optimized standard quicksort.
     * @param a The array to be sorted.
     * @param left First index of the array.
     * @param right Last index of the array.
     */
    static public void quickSort(int[] a, int left, int right)
    {
        if(left < right)
        {
            if(right - left <= 27)
            {
                // If the array is too small, we make use of insertion sort as it is more efficient.
                insertionSort(a,left,right);
            }
            else {
                int pi = randomPartition(a,left,right);
                quickSort(a,left,pi-1);
                quickSort(a,pi+1,right);
            }
        }
    }
    
    /**
     * Picks a random index to perform partitioning. 
     * @param a The array to be sorted.
     * @param left Left-limit of the partition.
     * @param right Right-limit of the partition.
     * @return Index of the pivot after partitioning.
     */
    static private int randomPartition(int[] a, int left, int right)
    {
        int randomNum = ThreadLocalRandom.current().nextInt(left, right + 1);
        swap(a,randomNum,right);
        return partition(a,left,right);
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
     * Performs partitioning for a standard quicksort.
     * @param a The array to be sorted.
     * @param left Left-limit of the partition.
     * @param right Right-limit of the partition.
     * @return Index of the pivot after partitioning.
     */
    static private int partition(int[] a, int left, int right)
    {
        int pivot = a[right];
        int i = left-1;
        for(int j = left; j < right; j++)
        {
            count++;
            if(a[j] < pivot)
            {
                i++;
                swap(a,i,j);
            }
        }
        swap(a,i+1,right);
        return i+1;
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
     * Performs a parallel merge sort on the array.
     * @param a Array to sort.
     * @param l Starting index.
     * @param r Final index.
     */
    static public void parallelMergeSort(int[] a, int l, int r)
    {
        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        forkJoinPool.invoke(new ParallelMergeSort(a, l, r));
    }
    
    /**
     * Performs a parallel dual-pivot quicksort on the given array.
     * @param a Array to sort.
     * @param l Starting index.
     * @param r Final index.
     */
    static public void hyperQuickSort(int[] a, int l, int r)
    {
        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        forkJoinPool.invoke(new ParallelQuickSort(a, l, r));
    }
    
    /**
     * Performs a dual-pivot parallel quicksort on the given array.
     * @param a Array to sort.
     * @param l Starting index.
     * @param r Final index.
     */
    static public void experimentalHyperQuickSort(int[] a, int l, int r)
    {
        final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        forkJoinPool.invoke(new ParallelMultiQuickSort(a, l, r));
    }
    
    /**
     * Performs a mergesort on the given array.
     * @param a Array to sort.
     * @param l Starting index.
     * @param r Final index.
     */
    static public void mergeSort(int[] a, int l, int r)
    {
        if(l < r)
        {
            int m = (l+r)/2;
            mergeSort(a, l, m);
            mergeSort(a,m+1,r);
            merge(a,l,m,r);
        }
    }
    
    /**
     * Merges the two sides split.
     * @param a Array to sort.
     * @param l Left-limit index.
     * @param m Middle split index.
     * @param r Right-limit index.
     */
    static private void merge(int[] a, int l, int m, int r)
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
    
    /**
     * Performs insertion-sort on the array.
     * @param arr Array to be sorted.
     * @param left Starting index.
     * @param right Final index.
     */
    static public void insertionSort(int[] arr, int left, int right) 
    { 
        for (int i=left+1; i<=right; i++) 
        { 
            int key = arr[i]; 
            int j = i-1; 
            while (j>=0 && arr[j] > key) 
            { 
                arr[j+1] = arr[j]; 
                j = j-1; 
            } 
            arr[j+1] = key; 
        } 
    } 
    
    /**
     * Performs Weirdsort on the array.
     * @param data Array to sort.
     * @param currentSize Size of the array.
     */
    public static void weirdSort(int[] data, int currentSize)
    {
        for(int i = 1; i < currentSize; i++)
        {
            if(data[i] < data[i-1])
            {
                for(int j = i; j >= 1; j--)
                {
                    if(data[j] < data[j-1])
                    {
                        swap(data,j,j-1);
                    }
                    else
                        break;
                }
            }
        }
    }
    
    /**
     * Performs bubble sort on the array.
     * @param data Array to sort.
     * @param currentSize Size of the array.
     * @param asc Whether to sort in ascending order or not
     */
    public static void bubbleSort(int[] data, int currentSize, boolean asc)
    {
        int ok, n = currentSize;
        if(asc)
        {
            do
            {
                ok = 0;
                for(int i = 0; i < n-1; i++)
                {
                    count++;
                    if(data[i] > data[i+1])
                    {
                        swap(data,i,i+1);
                        ok = 1;
                    }
                }
                n--;
            }while(ok==1);
        }
        else
        {
            do
            {
                ok = 0;
                for(int i = 0; i < n-1; i++)
                {
                    if(data[i] < data[i+1])
                    {
                        swap(data,i,i+1);
                        ok = 1;
                    }
                }
                n--;
            }while(ok==1);
        }
    }
    
    /**
     * Performs Cocktailsort on the array.
     * @param array Array to sort.
     * @param currentSize Size of the array.
     */
    public static void cocktailShaker(int[] array, int currentSize) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i <= currentSize - 2; i++) {
                if (array[i] > array[i + 1]) {
                    swap(array,i,i+1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
            swapped = false;
            for (int i = currentSize - 2; i >= 0; i--) {
                if (array[i] > array[i + 1]) {
                    swap(array,i,i+1);
                    swapped = true;
                }
            }
        } while (swapped == false);
    }
    
    /**
     * Performs Shuttlesort on the array.
     * @param a Array to sort.
     * @param n Size of the array.
     */
    public static void shuttlesort(int[] a, int n) 
    {
        int temp, j;
        for (int i = 0; i < n - 1; i++) 
        {
            if (a[i] > a[i + 1]) 
            {
                j = i - 1;
                temp = a[i + 1];	
                a[i + 1] = a[i];
                while (j >= 0 && a[j] > temp) 
                {
                    a[j + 1] = a[j];	
                    j = j - 1;
                }	
                a[j + 1] = temp;	
            }		
        }	
    }
    
    /**
     * Performs Selection sort on the array.
     * @param a Array to sort.
     * @param n Size of the array.
     */
    public static void selectionSort(int[] a, int n)
    {
        for(int i = 0; i < n-1; i++)
        {
            int mindex = i;
            for(int j = i+1; j < n; j++)
                if(a[j] < a[mindex])
                    mindex = j;
            swap(a,i,mindex);
        }
    }
    
    /**
     * Performs bucketsort on the array.
     * @param a Array to sort.
     * @param max Max size of the buckets.
     * @param min Min size of the buckets.
     * @param n Size of the array.
     */
    public static void bucketSort(int[] a, int max, int min, int n)
    {
        int M = max - min;
        int bucketsLength = n;
        List<List<Integer>> buckets = new ArrayList<>(bucketsLength);
        for(int i = 0; i < bucketsLength; i++) 
            buckets.add(new ArrayList<>());
        //placing each element in a bucket
        for (int i = 0; i < n; i++) 
        {
            //bi is bucket index
            int bi = a[i] / M;
            List<Integer> bucket = buckets.get(bi);
            bucket.add(a[i]);
        }
         // Sort buckets and concatinate results
        int j = 0;
        for (int bi = 0; bi < bucketsLength; bi++) 
        {
            List<Integer> bucket = buckets.get(bi);
            if (bucket != null) 
            {
                Collections.sort(bucket);
                for (int k = 0; k < bucket.size(); k++) 
                {
                    a[j++] = bucket.get(k);
                }
            }
        }
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
