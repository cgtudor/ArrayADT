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
public class QueueADT {
    private int[] data;
    private int currentSize;
    private int increaseValue;
    public QueueADT(int size)
    {
        data = new int[size];
        currentSize = 0;
        increaseValue = 512;
    }
    public QueueADT(int size, int outOfBoundsValue)
    {
        data = new int[size];
        currentSize = 0;
        increaseValue = outOfBoundsValue;
    }
    public boolean isFull()
    {
        return currentSize == data.length;
    }
    public boolean isEmpty()
    {
        return currentSize == 0;
    }
    private void automaticIncreaseSize()
    {
        increaseSize(currentSize + increaseValue);
    }
    public void enqueue(int x)
    {
        if(isFull())
            automaticIncreaseSize();
        currentSize++;
        for(int i = currentSize-1; i > 0;i--)
            data[i] = data[i-1];
        data[0] = x;
    }
    public void dequeue()
    {
        data[currentSize-1] = 0;
        currentSize--;
    }
    public void display()
    {
        for(int i = 0; i < currentSize; i++)
        {
            System.out.print(data[i]+" ");
        }
    }
    public void increaseSize(int newSize)
    {
        int[] dataCopy =  Arrays.copyOf(data, data.length);
        data = Arrays.copyOf(dataCopy, newSize);
    }
}
