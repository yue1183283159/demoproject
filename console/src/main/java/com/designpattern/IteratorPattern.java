package com.designpattern;

/**
 * 迭代器模式（Iterator Pattern）是 Java 和 .Net 编程环境中非常常用的设计模式。
 * 这种模式用于顺序访问集合对象的元素，不需要知道集合对象的底层表示。 
 * 意图：提供一种方法顺序访问一个聚合对象中各个元素, 而又无须暴露该对象的内部表示。
 */
public class IteratorPattern
{
	public static void main(String[] args)
	{
		NameRespository nameRespository = new NameRespository();
		Iterator iterator = nameRespository.getIterator();
		while (iterator.hasNext())
		{
			String name = (String) iterator.next();
			System.out.println(name);
		}
	}
}

interface Iterator
{
	boolean hasNext();

	Object next();
}

interface Container
{
	Iterator getIterator();
}

class NameRespository implements Container
{
	public String names[] = { "Robert", "Julie", "John", "Lora" };

	@Override
	public Iterator getIterator()
	{
		return new NameIterator();
	}

	private class NameIterator implements Iterator
	{
		int index;

		@Override
		public boolean hasNext()
		{
			if (index < names.length)
			{
				return true;
			}
			return false;
		}

		@Override
		public Object next()
		{
			if (this.hasNext())
			{
				return names[index++];
			}
			return null;
		}

	}
}