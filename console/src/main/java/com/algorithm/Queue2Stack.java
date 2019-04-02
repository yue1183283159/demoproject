package com.algorithm;

/**
*用队列实现栈？
*定义连个队列为Queue1和Queue2
*方案1：入栈出栈都在queue1中完成，queue2作为中转空间。入栈直接入queue1即可，出栈，把queue1中出最后一个
*元素外的所有元素都移动到queue2中，再将queue1中的元素出队，此时即为出栈，接着将queue2中的所有元素移动到queue1中
*放案2：入栈，两个队列那个不为空，就把元素入队到那个队列中；如果都为空，则任选一个队列入队。出栈：把不为空的队列中
*除最后一个元素外的所有元素移动到另一个队列中，然后出队最后一个元素。
*方案2省去了方案1中最后一步的转回操作
*/
public class Queue2Stack
{
	private ArrayQueue queue1;
	private ArrayQueue queue2;
	private int maxLength;
	
	public Queue2Stack(int capacity){
		maxLength=capacity;
		queue1=new ArrayQueue(capacity);
		queue2=new ArrayQueue(capacity);
	}
	
	public boolean push(int item){
		if(size()==maxLength){
			return false;
		}
		if(queue2.isEmpty()){
			queue1.put(item);
		}else{
			queue2.put(item);
		}
		return true;
	}
	
	public Object pop(){
		if(size()==0){
			throw new IndexOutOfBoundsException("empty stack");
		}else{
			if(queue2.isEmpty()){
				while(queue1.size()>1){
					queue2.put(queue1.poll());
				}
				return queue1.poll();
			}else{
				while(queue2.size()>1){
					queue1.put(queue2.poll());
				}
				return queue2.poll();
			}
		}		
	}
	
	public int size(){
		return queue1.size()+queue2.size();
	}
}
