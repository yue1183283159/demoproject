package com.algorithm;

/**
 * 循环队列，规定队列的长度为数组长度减1，有一个位置不放元素，用来区分队满和空队。
 * 当head=tail是空队，当head=(tail+1)%length时为满队
 */
public class ArrayQueue {
    private final Object[] items;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int capacity) {
        this.items = new Object[capacity];
    }

    public boolean put(Object item) {
        if (head == (tail + 1) % items.length) {
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % items.length;//tail标记向后移动一位
        return true;
    }

    //获取队列元素，不出队
    public Object peek() {
        if (head == tail) {//队列是空的
            return null;
        }
        return items[head];
    }

    //出队
    public Object poll() {
        if (head == tail) {
            return null;
        }
        Object item = items[head];
        items[head] = null;//把没用的元素赋空值，不设置也行，标记移动了，之后会被覆盖
        head = (head + 1) % items.length;
        return item;

    }

    public boolean isFull() {
        return head == (tail + 1) % items.length;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        if (tail >= head) {
            return tail - head;
        } else {
            return tail + items.length - head;
        }
    }
}
