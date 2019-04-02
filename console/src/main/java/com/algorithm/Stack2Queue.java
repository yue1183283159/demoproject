package com.algorithm;

/**
*如何使用栈实现队列？
*方案1：定义两个栈为stack1和stack2；
*入队--直接向stack1中入栈；
*出队--将stack1中所有元素出栈，依次入栈到stack2中，然后弹出stack2中栈顶元素，
*接着把stack2中的所有元素出栈依次压入stack1中
*（来回入队，出队比较繁琐）
*方案2：入队都在stack1中进行，stack2用于出对，同时保证所有元素都在一个栈里。
*方案3：入队都在stack1中进行，出队都在stack2中进行，同时遵守一下原则：
*1.入队，直接把元素压入stack1中，2.出队，如果stack2不为空，则直接弹出stack2中元素；
*如果stack2为空，则将stack1中的所有元素倒入stack2中，然后弹出stack2中的栈顶元素
*方案3最优
*/
public class Stack2Queue
{
	private MyStack stack1;
	private MyStack stack2;
	private int maxLength;
	
	public Stack2Queue(int capacity){
		maxLength=capacity;
		stack1=new MyStack(capacity);
		stack2=new MyStack(capacity);
	}
	
	public boolean put(int item){
		if(stack1.isFull() || maxLength==size()){
			return false;
		}
		stack1.push(item);
		return true;
	}
	
	public int poll(){
		if(!stack2.isEmpty()){
			return stack2.pop();
		}else{
			while(!stack1.isEmpty()){
				stack2.push(stack1.pop());
			}
			return stack2.pop();
		}
	}
	
	public int size(){
		return stack1.size()+stack2.size();
	}
	
}
