package cz.cvut.fel.pjv;

import java.sql.Array;

/**
 * Implementation of the {@link Queue} backed by fixed size array.
 */
public class CircularArrayQueue implements Queue {
    private int capacity;
    private String[] queue;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    /**
     * Creates the queue with capacity set to the value of 5.
     */
    public CircularArrayQueue() {
        this.capacity = 5;
        queue = new String[capacity];
    }

    /**
     * Creates the queue with given {@code capacity}. The capacity represents maximal number of elements that the
     * queue is able to store.
     * @param capacity of the queue
     */

    public CircularArrayQueue(int capacity) {
        this.capacity = capacity;
        queue = new String[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        boolean ret = false;
        if(size == 0) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean isFull() {
        boolean ret = false;
        if(size == capacity) {
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean enqueue(String obj) {
        boolean ret = false;
        if(size < capacity) {
            size++;
            queue[rear] = obj;
            rear = (rear + 1) % capacity;
            ret = true;
        }else {
            ret = false;
        }
        return ret;
    }

    @Override
    public String dequeue() {
        String retItem = null;
        if(size > 0) {
            retItem = queue[front];
            front = (front + 1) % capacity;
            size--;
        }
        return retItem;
    }

    @Override
    public void printAllElements() {
        String item = null;
        int idx = 0;
        for(int i = 0; i < size; i++) {
            idx = (front + i) % capacity;
            System.out.println(queue[idx]);
        }
    }
}
