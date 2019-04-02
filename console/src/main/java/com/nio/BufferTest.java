package com.nio;

import java.nio.CharBuffer;

public class BufferTest {
	public static void main(String[] args) {
		// 创建buffer
		CharBuffer buff = CharBuffer.allocate(8);// 容量是8个字符
		System.out.println("Capacity:" + buff.capacity());
		System.out.println("Limit:" + buff.limit());
		System.out.println("Position:" + buff.position());
		
		buff.put('a');
		buff.put('b');
		buff.put('c');
		System.out.println("加入三个元素后，position的值为："+buff.position());
		
		buff.flip();
		//调用Buffer的flip()方法，该方法将limit设置为position所在位置，并将position设为0
		System.out.println("执行flip()方法后：limit的值："+buff.limit());//执行flip()方法后：limit的值：3
		System.out.println("执行flip()方法后：position的值："+buff.position());
		
		//按相对位置取出元素
		System.out.println("按相对位置取出的第一个元素(position=0)"+buff.get());//第一个元素(position=0)a
		System.out.println("按相对位置取出第一个元素后，position的值："+buff.position());//取出第一个元素后，position的值：1
		System.out.println("按相对位置取出第一个元素后，limit的值："+buff.limit());//取出第一个元素后，limit的值：3

		//按绝对位置取出元素
		System.out.println("按绝对位置的第一个元素(position=0)"+buff.get(0));//第一个元素(position=0)a
		System.out.println("按绝对位置取出第一个元素后，position的值："+buff.position());//取出第一个元素后，position的值：1
		System.out.println("按绝对位置取出第一个元素后，limit的值："+buff.limit());//取出第一个元素后，limit的值：3

		//调用clear()方法
		buff.clear();
		//Buffer调用clear()方法，clear()方法不是清空Buffer的数据，它仅仅将position置为0，将limit置为capacity,这样为再次向Buffer中装入数据做好准备
		System.out.println("调用clear()方法后，limit的值："+buff.limit());//调用clear()方法后，limit的值：8
		System.out.println("调用clear()方法后，position的值："+buff.position());//调用clear()方法后，position的值：0
		System.out.println("第二个元素(position=0)"+buff.get(1));//第二个元素(position=0)b
		//因为上面代码采用的是根据索引来取值的方式，所以该方法不会影响Buffer的position
		System.out.println("取出第二个元素后，position的值："+buff.position());//取出第二个元素后，position的值：0
		System.out.println("取出第二个元素后，position的值："+buff.limit());//取出第二个元素后，position的值：8
		
	}

}
