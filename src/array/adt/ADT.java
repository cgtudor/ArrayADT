package array.adt;

import java.util.Arrays;

/**
 * This is a custom array wrapper meant to simplify array basic actions.
 * @author v8002382
 */
public class ADT {
    private final int STANDARD_STARTING_SIZE = 10;
    private int[] data;
    private int currentSize;
    
    /**
     * Standard constructor. Initializes ADT with the standard initial size.
     */
    public ADT()
    {
        data = new int[STANDARD_STARTING_SIZE];
        currentSize = 0;
    }
    
    /**
     * Constructor for creating an ADT with a specific initial size.
     * @param size Size of the newly created ADT.
     */
    public ADT(int size)
    {
        data = new int[size];
        currentSize = 0;
    }
    
    /**
     * Constructor if an array is provided for wrapping.
     * @param size Size of the newly created ADT.
     * @param array Array to wrap in the new ADT.
     */
    public ADT(int size, int[] array)
    {
        data = new int[size];
        data = Arrays.copyOf(array, size);
        currentSize = 0;
        for(int i = 0; i < data.length; i++)
        {
            if(data[i]!=0)
                currentSize++;
        }
    }
    
    /**
     * Checks whether the ADT is full.
     * @return A boolean that represents the fullness of the array.
     */
    public boolean isFull()
    {
        return currentSize == data.length;
    }
    
    /**
     * Checks whether the ADT is empty.
     * @return A boolean that represents the emptiness of the array.
     */
    public boolean isEmpty()
    {
        return currentSize == 0;
    }
    
    /**
     * Add an element to the ADT.
     * When adding an element, we increase the size of the array before adding if it is full.
     * @param x Element to add.
     * @return True if the element was added successfully.
     */
    public boolean add(int x)
    {
        if(!isFull())
        {
            currentSize++;
            data[currentSize-1] = x;
            //System.out.println(data[currentSize]);
            return true;
        }
        else
        {
            increaseSize(currentSize * currentSize);
            currentSize++;
            data[currentSize-1] = x;
            //System.out.println(data[currentSize]);
            return true;
        }
    }
    
    /**
     * Insert an element at a specific position
     * @param x Element to insert.
     * @param j Position to insert at.
     * @return True if the element was inserted successfully, false if not.
     */
    public boolean insert(int x,int j)
    {
        if(!isFull() && j <= currentSize)
        {
            for(int i = currentSize-1; i >= j; i--)
            {
                data[i+1] = data[i];
            }
            data[j] = x;
            currentSize++;
            return true;
        }
        return false;
    }
    
    /**
     * Delete an element at a specific position.
     * @param j Position at which the element to be deleted resides.
     */
    public void delete(int j)
    {
        for(int i = j; i < currentSize; i++)
        {
            data[i] = data[i+1];
        }
        currentSize--;
    }
    
    /**
     * Get element from index.
     * @param j Index at which the element requested resides.
     * @return The element requested.
     */
    public int getFromIndex(int j)
    {
        return data[j];
    }
    
    /**
     * Clear the ADT of all data, replacing it with zeroes.
     */
    public void clear()
    {
        for(int i = 0; i < data.length; i++)
        {
            data[i] = 0;
        }
    }
    
    /**
     * Get the current size of the ADT.
     * @return An integer representing the size of the ADT.
     */
    public int getSize()
    {
        return currentSize;
    }
    
    /**
     * Get a copy of the ADT in the form of an array.
     * @return An array copy of the ADT.
     */
    public int[] getArray()
    {
        return Arrays.copyOf(data, currentSize);
    }
    
    /**
     * Print the ADT to the console.
     */
    public void printArray()
    {
        for(int i = 0; i < currentSize; i++)
        {
            System.out.print(data[i]+" ");
        }
    }
    
    /**
     * Format the contents of the ADT to a string.
     * @return A string with the contents of the ADT.
     */
    public String printToString()
    {
        String content = "";
        for(int i = 0; i < currentSize; i++)
            content += data[i] + " ";
        return String.valueOf(data.length)+"\n"+String.valueOf(currentSize)+"\n"+content;
    }
    
    /**
     * Increase the size of the ADT.
     * @param newSize new size of the ADT.
     */
    public void increaseSize(int newSize)
    {
        int[] dataCopy =  Arrays.copyOf(data, data.length);
        data = Arrays.copyOf(dataCopy, newSize);
    }
    
    /**
     * Sort the ADT using bubble sort.
     * @param asc Whether the sort should be ascending or not.
     */
    public void bubbleSortAscending(boolean asc)
    {
        ArraySort.bubbleSort(data, currentSize, asc);
    }
    
    /**
     * Sort the ADT using WeirdSort.
     */
    public void weirdSort()
    {
        ArraySort.weirdSort(data,currentSize);
    }
    
    /**
     * Sort the ADT using insertion sort.
     */
    public void insertionSort()
    {
        ArraySort.insertionSort(data, 0, currentSize-1);
    }
    
    /**
     * Sort the ADT using the JAVA implemented (DP) sort.
     */
    public void javaSort()
    {
        Arrays.sort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using Quicksort.
     */
    public void quickSort()
    {
        ArraySort.quickSort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using HyperQuicksort (DPP).
     */
    public void hyperQuickSort()
    {
        ArraySort.hyperQuickSort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using experimental HyperQuicksort (Three-Pivot Parallel).
     */
    public void experimentalHyperQuickSort()
    {
        ArraySort.experimentalHyperQuickSort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using an optimized Quicksort.
     */
    public void optimizedQuickSort()
    {
        ArraySort.optimizedQuickSort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using an optimized Three-Pivot Quicksort.
     */
    public void optimized3QuickSort()
    {
        ArraySort.optimized3QuickSort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using Mergesort.
     */
    public void mergeSort()
    {
        ArraySort.mergeSort(data,0,currentSize-1);
    }
    
    /**
     * Sort the ADT using CocktailSort.
     */
    public void cocktailSort()
    {
        ArraySort.cocktailShaker(data, currentSize);
    }
    
    /**
     * Sort the ADT using ShuttleSort.
     */
    public void shuttleSort()
    {
        ArraySort.shuttlesort(data, currentSize);
    }
    
    /**
     * Sort the ADT using Selection Sort.
     */
    public void selectionSort()
    {
        ArraySort.selectionSort(data,currentSize);
    }
    
    /**
     * Sort the ADT using bucket sort.
     */
    public void bucketSort()
    {
        int max = data[0], min = data[0];
        for(int i = 1; i < currentSize; i++)
        {
            if(data[i] > max)
                max = data[i];
            if(data[i] < min)
                min = data[i];
        }
        ArraySort.bucketSort(data, max, min, currentSize);
    }
    
    /**
     * Sort the ADT using Parallel Mergesort.
     */
    public void parallelMergeSort()
    {
        ArraySort.parallelMergeSort(data, 0, currentSize-1);
    }
}
