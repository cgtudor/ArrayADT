/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package array.adt;

import java.util.Arrays;

/**
 *
 * @author v8002382
 */
public class ADT {
    private int[] data;
    private int currentSize;
    public ADT(int size)
    {
        data = new int[size];
        currentSize = 0;
    }
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
    public boolean isFull()
    {
        return currentSize == data.length;
    }
    public boolean isEmpty()
    {
        return currentSize == 0;
    }
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
    public void delete(int j)
    {
        for(int i = j; i < currentSize; i++)
        {
            data[i] = data[i+1];
        }
        currentSize--;
    }
    public int getFromIndex(int j)
    {
        return data[j];
    }
    public void clear()
    {
        for(int i = 0; i < data.length; i++)
        {
            data[i] = 0;
        }
    }
    public int getSize()
    {
        return currentSize;
    }
    public int[] getArray()
    {
        return Arrays.copyOf(data, currentSize);
    }
    public void printArray()
    {
        for(int i = 0; i < currentSize; i++)
        {
            System.out.print(data[i]+" ");
        }
    }
    public String printToString()
    {
        String content = "";
        for(int i = 0; i < currentSize; i++)
            content += data[i] + " ";
        return String.valueOf(data.length)+"\n"+String.valueOf(currentSize)+"\n"+content;
    }
    public void increaseSize(int newSize)
    {
        int[] dataCopy =  Arrays.copyOf(data, data.length);
        data = Arrays.copyOf(dataCopy, newSize);
    }
    public void bubbleSortAscending(boolean asc)
    {
        ArraySort.bubbleSort(data, currentSize, asc);
    }
    public void weirdSort()
    {
        ArraySort.weirdSort(data,currentSize);
    }
    public void insertionSort()
    {
        ArraySort.insertionSort(data, 0, currentSize-1);
    }
    public void javaSort()
    {
        Arrays.sort(data,0,currentSize-1);
    }
    public void quickSort()
    {
        ArraySort.quickSort(data,0,currentSize-1);
    }
    public void hyperQuickSort()
    {
        ArraySort.hyperQuickSort(data,0,currentSize-1);
    }
    public void experimentalHyperQuickSort()
    {
        ArraySort.experimentalHyperQuickSort(data,0,currentSize-1);
    }
    public void optimizedQuickSort()
    {
        ArraySort.optimizedQuickSort(data,0,currentSize-1);
    }
    public void optimized3QuickSort()
    {
        ArraySort.optimized3QuickSort(data,0,currentSize-1);
    }
    public void mergeSort()
    {
        ArraySort.mergeSort(data,0,currentSize-1);
    }
    public void cocktailSort()
    {
        ArraySort.cocktailShaker(data, currentSize);
    }
    public void shuttleSort()
    {
        ArraySort.shuttlesort(data, currentSize);
    }
    public void selectionSort()
    {
        ArraySort.selectionSort(data,currentSize);
    }
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
    public void parallelMergeSort()
    {
        ArraySort.parallelMergeSort(data, 0, currentSize-1);
    }
}
