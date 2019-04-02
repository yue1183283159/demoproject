package com.algorithm;

public class Link
{
	private int size = 0;
	private Node first;
	private Node last;

	public Link()
	{
	}

	public void addLast(int data)
	{
		if (size == 0)
		{
			fillStart(data);
		} else
		{
			Node node = new Node();
			node.setData(data);
			last.setNext(node);
			last = node;// 把最后插入的元素设置为链表尾的元素
		}
		size++;

	}

	public void addFirst(int data)
	{
		if (size == 0)
		{
			fillStart(data);
		} else
		{
			Node node = new Node();
			node.setData(data);
			node.setNext(first);// 把元素的下一个位置的指针指向头元素
			first = node;// 把刚插入的元素设置为链表头元素
		}
		size++;
	}

	public void add(int data, int index)
	{
		if (size > index)
		{
			if (size == 0)
			{
				fillStart(data);
				size++;
			} else if (index == 0)
			{
				addFirst(data);
			} else if (size == index + 1)
			{
				addLast(data);
			} else
			{
				Node temp = get(index);
				Node node = new Node();
				node.setData(data);
				node.setNext(temp.getNext());
				temp.setNext(node);
				size++;
			}
		} else
		{
			throw new IndexOutOfBoundsException("Link length limit.");
		}
	}

	public void removeFirst()
	{
		if (size == 0)
		{
			throw new IndexOutOfBoundsException("Link is empty.");
		} else if (size == 1)
		{
			// 只剩一个元素，需要清除first和last
			clear();
		} else
		{
			Node temp = first;
			first = temp.getNext();
			temp = null;// 空间回收
			size--;
		}

	}

	public void removeLast()
	{
		if (size == 0)
		{
			throw new IndexOutOfBoundsException("Link is empty.");
		} else if (size == 1)
		{
			clear();
		} else
		{
			Node temp = get(size - 2);// 获取最后一个元素之前的一个元素
			temp.setNext(null);
			size--;
		}
	}

	public void removeMiddle(int index)
	{
		if (size == 0)
		{
			throw new IndexOutOfBoundsException("Link is empty.");
		} else if (size == 1)
		{
			clear();
		} else
		{
			Node temp = get(index - 1);
			Node next = temp.getNext();
			temp.setNext(next.getNext());
			next = null;
			size--;
		}
	}

	public Node get(int index)
	{
		Node temp = first;
		for (int i = 0; i < index; i++)
		{
			temp = temp.getNext();
		}
		return temp;
	}

	public void printAll()
	{
		Node temp = first;
		System.out.println(temp.getData());
		for (int i = 0; i < size - 1; i++)
		{
			temp = temp.getNext();
			System.out.println(temp.getData());
		}
	}

	public int size()
	{
		return size;
	}

	public void fillStart(int data)
	{
		first = new Node();
		first.setData(data);
		last = first;
	}

	private void clear()
	{
		first = null;
		last = null;
		size = 0;
	}

	// 反转链表
	public void reverse()
	{
		Node temp = first;
		last = temp;
		Node next = first.getNext();
		for (int i = 0; i < size - 1; i++)
		{
			Node nextNext = next.getNext();// 下下个
			next.setNext(temp);
			temp = next;
			next = nextNext;
		}
		last.setNext(null);
		first = temp;
	}

}

class Node
{
	private int data;
	private Node next;

	public int getData()
	{
		return data;
	}

	public void setData(int data)
	{
		this.data = data;
	}

	public Node getNext()
	{
		return next;
	}

	public void setNext(Node next)
	{
		this.next = next;
	}
}
