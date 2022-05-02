package array.adt;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This recursive action class serves to perform the three-pivot parallel quicksort using ForkJoinPool.
 * @author v8002382
 */
public class ParallelMultiQuickSort extends RecursiveAction{
    final int[] array;
    final int lo, hi;
    static final double THRESHOLD = 1<<12;
    
    /**
     * Constructor for providing starting and final indices.
     * @param array Array to be sorted.
     * @param lo Starting index.
     * @param hi Final index.
     */
    ParallelMultiQuickSort(int[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    /**
     * Standard constructor.
     * @param array Array to be sorted.
     */
    ParallelMultiQuickSort(int[] array) {
        this(array, 0, array.length);
    }
    
    @Override
    protected void compute() {
        if (lo < hi) {
            
            // If the sequential threshhold is hit, perform sequential quicksort for efficiency.
            if (hi - lo <= THRESHOLD) 
            { 
                ArraySort.optimized3QuickSort(array, lo, hi);
            } 
            else 
            {
                for (int i = lo; i < hi; i++) 
            {
                if (array[i] > array[i + 1]) 
                {
                    swap(array, i, i + 1);
                }
            }
            boolean sorted = true;
            for (int i = lo; i < hi; i++) 
            {
                if (array[i] > array[i + 1]) 
                {
                    sorted = false;
                }
            }
            if (sorted) 
            {
                return;
            }
                int[] pi = random3Partition(array,lo,hi);
                final ParallelMultiQuickSort left
                        = new ParallelMultiQuickSort(array,lo,pi[0]-1);
                final ParallelMultiQuickSort middle
                        = new ParallelMultiQuickSort(array,pi[0]+1,pi[1]-1);
                final ParallelMultiQuickSort middle2
                        = new ParallelMultiQuickSort(array,pi[1]+1,pi[2]-1);
                final ParallelMultiQuickSort right
                        = new ParallelMultiQuickSort(array,pi[2]+1,hi);
                invokeAll(left, middle, middle2, right);
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
     * Performs partitioning for a dual-pivot quicksort.
     * @param a The array to be sorted.
     * @param left Left-limit of the partition.
     * @param right Right-limit of the partition.
     * @return An array containing indices of the pivots after partitioning.
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
