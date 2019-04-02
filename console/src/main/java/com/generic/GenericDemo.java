package com.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//泛型
public class GenericDemo {

    public static void main(String[] args) {
        TestGeneric<String> tg1 = new TestGeneric<String>("Apple");
        System.out.println(tg1.getInfo());
        TestGeneric<Integer> tg2 = new TestGeneric<Integer>(234);
        System.out.println(tg2.getInfo());

        Integer[] a1 = {1, 2, 3, 4};
        List<Integer> list1 = new ArrayList<>();
        copyArrayToCollection(a1, list1);
        System.out.println(list1);

        String[] sArr = {"a", "b", "c"};
        List<String> sList = new ArrayList<>();
        copyArrayToCollection(sArr, sList);
        System.out.println(sList);

        List<String> sList1 = new ArrayList<String>();
        sList1.add("A");
        sList1.add("B");
        sList1.add("C");
        List<Object> oList = new ArrayList<Object>();
        copyToCollection(sList1, oList);
        System.out.println(oList);

        List<Number> ln = new ArrayList<>();
        List<Integer> li = new ArrayList<>();
        li.add(56);
        Integer last = copy(ln, li);
        System.out.println(last);
    }

    static <T> void copyArrayToCollection(T[] arr, Collection<T> c) {
        for (T t : arr) {
            c.add(t);
        }
    }

    // 使用类型通配符。前一个集合类型必须是后一个类型的子类。设置通配符的上限
    static <T> void copyToCollection(Collection<? extends T> from, Collection<T> to) {
        for (T ele : from) {
            to.add(ele);
        }
    }

    // 设置通配符的下限
    static <T> T copy(Collection<? super T> dest, Collection<T> src) {
        T last = null;
        for (T ele : src) {
            last = ele;
            dest.add(last);
        }
        return last;
    }
}

class TestGeneric<T> {
    private T info;

    public TestGeneric() {
    }

    public TestGeneric(T info) {
        this.info = info;
    }

    public T getInfo() {
        return this.info;
    }
}
