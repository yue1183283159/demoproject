package com.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

public class CollectionUnitTest {
    @Test
    public void testLinkedHashMap() {
        // LinkedHashMap底层使用链表实现的hashmap结构
        // 按put的顺序存放数据
        // 实现了LRU（最近最少使用算法）算法，通常用来缓存数据，将最近最少使用的对象从缓存中移除，减少内存占用
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>(4, 0.75f, true);
        linkedHashMap.put("A", 100);
        linkedHashMap.put("B", 300);
        linkedHashMap.put("C", 300);
        linkedHashMap.put("D", 500);
        System.out.println(linkedHashMap);
        linkedHashMap.get("B");
        linkedHashMap.get("D");
        //访问之后，数据在linkedhashmap中会重新排序，使用次数最少的放在链表头部
        System.out.println(linkedHashMap);
    }

    // @Test
    public void testCollection() {
        // System.out.println("测试LinkedList和ArrayList的效率");
        // // 想arraylist中添加数据
        // long startTime = System.currentTimeMillis();
        // ArrayList<Integer> intList = new ArrayList<>();
        // for (int i = 0; i < 100000; i++)
        // {
        // intList.add(50 + i);
        // }
        // long endTime = System.currentTimeMillis();
        // System.out.println("ArrayList add time:" + (endTime - startTime));
        //
        // long startTime1 = System.currentTimeMillis();
        // LinkedList<Integer> intLinkedList = new LinkedList<Integer>();
        // for (int i = 0; i < 100000; i++)
        // {
        // intLinkedList.add(50 + i);
        // }
        // long endTime1 = System.currentTimeMillis();
        // System.out.println("LinkedList add time:" + (endTime1 - startTime1));
        //
        // long startTime2 = System.currentTimeMillis();
        // for (Integer n : intList)
        // {
        //
        // // System.out.print(n);
        // }
        // long endTime2 = System.currentTimeMillis();
        // System.out.println("遍历ArrayList的时间：" + (endTime2 - startTime2));
        //
        // long startTime3 = System.currentTimeMillis();
        // for (int n : intLinkedList)
        // {
        // // System.out.print(n);
        // }
        // long endTime3 = System.currentTimeMillis();
        // System.out.println("遍历LinkedList的时间:" + (endTime3 - startTime3));
        //
        // // 使用迭代器遍历集合，效率高
        //
        // long startTime4 = System.currentTimeMillis();
        // Iterator<Integer> linkedIt = intLinkedList.iterator();
        // while (linkedIt.hasNext())
        // {
        // int n = linkedIt.next();
        // }
        // long endTime4 = System.currentTimeMillis();
        // System.out.println("使用迭代器遍历linkedlist的时间：" + (endTime4 - startTime4));

        ArrayList<String> chatList = new ArrayList<>();
        chatList.add("sa");
        chatList.add("saa");
        // for (int i = 0; i < chatList.size(); i++)
        // {
        // chatList.remove(i);
        // }
        Iterator<String> chatIt = chatList.iterator();
        while (chatIt.hasNext()) {
            chatIt.next();
            chatIt.remove();
        }
        System.out.println(chatList);

        // foreach增强for循环，

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        Collection<String> c = new ArrayList<String>();
        c.add("a1");
        c.add("a2");
        System.out.println(c.contains("a1"));
        System.out.println(c.size());

        Collection<String> c1 = new ArrayList<String>();
        c1.add("a1");
        c1.add("a2");
        c1.add("c3");
        System.out.println(c1.containsAll(c));

        Collection<User> cu = new ArrayList<User>();
        for (int i = 0; i < 6; i++) {
            User u = new User(i, "NameOf" + i, i * 2 + 20);
            cu.add(u);
        }

        cu.add(new User(10, "James", 23));
        System.out.println(cu.size());
        // cu.clear();
        System.out.println(cu.isEmpty());

        Iterator<User> cuIter = cu.iterator();
        while (cuIter.hasNext()) {
            User u = cuIter.next();
            // cuIter.remove();
        }

        for (User u : cu) {
            // System.out.println(u);
        }

        System.out.println();
        System.out.println();
        // LinkedList
        //
        LinkedList<String> lList = new LinkedList<String>();
        lList.add("aaa");
        lList.add("ggg");
        lList.add("ttt");
        lList.add("sss");
        lList.add("yyy");
        lList.add("uuu");
        lList.add("aaa");
        lList.add("bbb");
        System.out.println(lList.size());
        System.out.println(lList);
        System.out.println(lList.get(0));
        System.out.println(lList.get(lList.size() - 1));
        System.out.println(lList.remove("aaa"));
        System.out.println(lList);
        lList.addFirst("***");
        System.out.println(lList);
        System.out.println(lList.removeFirst());
        System.out.println(lList);

        System.out.println();
        System.out.println();
        System.out.println();

        List<String> cList = new ArrayList<String>();
        cList.add("a");
        cList.add("b");
        cList.add("c");
        System.out.println(cList);
        cList.add(1, "d");// 在指定位置插入一个值，集合长度变长
        System.out.println(cList);
        System.out.println(cList.get(1));
        cList.set(2, "f");

        System.out.println();
        System.out.println();
        // 将集合转为数组
        String[] ary = new String[0];
        String[] strs = cList.toArray(ary);
        System.out.println(Arrays.toString(strs));

        List<User> uList = new ArrayList<User>();
        uList.add(new User(1, "zhangsan", 23));
        uList.add(new User(2, "lisi", 33));
        uList.add(new User(3, "wagnwu", 22));
        uList.add(new User(4, "xiaoliu", 22));
        uList.add(new User(5, "qige", 26));
        System.out.println(uList);
        Collections.sort(uList);
        System.out.println(uList);

        // 通过匿名内部类实现重写排序方法。
        Collections.sort(uList, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareTo(u2.getName());
            }
        });

        System.out.println(uList);

        System.out.println();
        System.out.println();
        System.out.println();

        // Queue
        Queue<String> queue = new LinkedList<String>();
        queue.offer("a");
        queue.offer("b");
        System.out.println(queue);
        String first = queue.poll();
        System.out.println(first);
        System.out.println(queue);

        queue.add("c");
        queue.add("d");
        queue.add("e");
        System.out.println(queue);
        while (queue.size() != 0) {
            System.out.println(queue.poll());
        }

        System.out.println();
        System.out.println();
        System.out.println();

        // Deque双端队列
        Deque<String> deque = new LinkedList<>();
        deque.offer("aa");
        deque.offer("bb");
        deque.offer("cc");
        System.out.println(deque);
        deque.offerFirst("first");
        System.out.println(deque);
        deque.offerLast("last");
        System.out.println(deque.peek());
        System.out.println(deque.peekFirst());
        System.out.println(deque);
        System.out.println(deque.pollFirst());
        System.out.println(deque);
        System.out.println(deque.pollLast());
        System.out.println(deque);

        System.out.println();
        System.out.println();
        System.out.println();

        // Stack,FILO->First Input Last Output
        Deque<String> stack = new LinkedList<String>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        String re = stack.poll();
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);

        System.out.println();
        System.out.println();
        System.out.println();

        // Set/ HashMap
        Map<String, Double> scoreMap = new HashMap<>();
        scoreMap.put("Chinese", 90.0);
        scoreMap.put("Math", 88.5);
        System.out.println(scoreMap);
        System.out.println(scoreMap.get("Math"));
        scoreMap.put("Chinese", 89.0);
        System.out.println(scoreMap);
        System.out.println(scoreMap.containsKey("English"));
        scoreMap.put("English", 78.5);
        Set<String> scoreKey = scoreMap.keySet();
        for (String key : scoreKey) {
            System.out.println(key + ":" + scoreMap.get(key));
        }

        System.out.println();
        System.out.println();
        System.out.println();

        Set<Entry<String, Double>> entrySet = scoreMap.entrySet();
        for (Entry<String, Double> entry : entrySet) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        HashMap<String, Object> hMap = new HashMap<String, Object>();
        hMap.put("1001", "order1");
        hMap.get("1001");

        HashMap<Integer, TeduOrder> orderMap = new HashMap<Integer, TeduOrder>();
        TeduOrder order1 = new TeduOrder();
        order1.orderId = 1001;
        order1.payment = 3000;
        orderMap.put(order1.orderId, order1);
        orderMap.get(1001);

        // hashCode可以认为是对象在内存中的地址
        System.out.println(orderMap.hashCode());
        Integer id = new Integer(1000);
        System.out.println(id.hashCode());

        ArrayList<TeduOrder> oList = new ArrayList<TeduOrder>();
        for (int i = 0; i < 100000; i++) {
            TeduOrder order = new TeduOrder();
            order.orderId = 50 + i;
            oList.add(order);
        }

        HashMap<Integer, TeduOrder> oMap = new HashMap<Integer, TeduOrder>();
        for (int i = 0; i < 100000; i++) {
            TeduOrder order = new TeduOrder();
            order.orderId = 50 + i;
            oMap.put(order.orderId, order);
        }

        long stime = System.currentTimeMillis();
        TeduOrder tOrder = oMap.get(90000);
        long etime = System.currentTimeMillis();
        System.out.println(etime - stime);

        stime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            TeduOrder torder = oList.get(i);
            if (torder.orderId == 90000) {
                break;
            }
        }
        etime = System.currentTimeMillis();
        System.out.println(etime - stime);

    }
}

class TeduOrder {
    int orderId;
    double payment;

}

// 实现Comparable接口，重写compareTo方法，设置对象是可以比较的
class User implements Comparable<User> {
    private int id;
    private String name;
    private int age;

    public User(int id, String name, int age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(User u) {
        // 在类里面，可以直接使用类的私有属性
        return age - u.age;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + age + "\n";
    }
}
