package com.cui.java.study.dataStructure;

import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;

    private T[] elementData;
    private int size;

    public MyArrayList(int capacity) {
        this.size = 0;
        elementData = (T[])new Object[capacity];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return elementData.length;
    }

    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return elementData[index];
    }

    public boolean add(T element) {
        add(size(), element);
        return true;
    }

    private void add(int size, T element) {
        if (size == capacity()) {
            ensureCapacity();
        }
        elementData[size++] = element;
    }

    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T old = elementData[index];
        elementData[index] = element;
        return old;
    }

    public boolean remove(int index) {
        // TODO: 2020/2/14
        return false;
    }

    private void ensureCapacity() {
        int newCapacity = capacity() + capacity() >> 1;
        T[] newArr = Arrays.copyOf(elementData, newCapacity);
        elementData = newArr;
    }

    @Override public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    class MyIterator<T> implements Iterator<T> {

        @Override public boolean hasNext() {
            return false;
        }

        @Override public T next() {
            return null;
        }

        @Override public void remove() {

        }
    }
}
