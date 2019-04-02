package com.designpattern;

import java.util.HashMap;
/*
 * 原型模式（Prototype Pattern）是用于创建重复的对象，同时又能保证性能
 * 这种模式是实现了一个原型接口，该接口用于创建当前对象的克隆
 */
public class PrototypePattern
{

	public static void main(String[] args)
	{
		// 使用 ShapeCache 类来获取存储在 Hashtable 中的形状的克隆。
		ShapeCache.loadCache();
		Shape clonedShape = (Shape) ShapeCache.getShape("2");
		System.out.println("Shape:" + clonedShape.getType());
		clonedShape.draw();
		
		Shape clonedShape3 = (Shape) ShapeCache.getShape("3");
		System.out.println("Shape : " + clonedShape3.getType());
		clonedShape3.draw();
	}

}

abstract class Shape implements Cloneable
{
	private String id;
	protected String type;

	abstract void draw();

	public String getType()
	{
		return this.type;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Object clone()
	{
		Object clone = null;
		try
		{
			clone = super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return clone;
	}
}

class Rectangle extends Shape
{
	public Rectangle()
	{
		type = "Rectangle";
	}

	@Override
	void draw()
	{
		System.out.println("Inside Rectangle::draw() method.");
	}
}

class Square extends Shape
{
	public Square()
	{
		type = "Square";
	}

	@Override
	void draw()
	{
		System.out.println("Inside Square::draw() method.");
	}

}

class ShapeCache
{
	private static HashMap<String, Shape> shapeMap = new HashMap<>();

	public static Shape getShape(String shapeId)
	{
		Shape cachedShape = shapeMap.get(shapeId);
		return (Shape) cachedShape.clone();
	}

	// 对每种形状都运行数据库查询，并创建该形状
	// shapeMap.put(shapeKey, shape);
	// 例如，我们要添加三种形状
	public static void loadCache()
	{
		Square square = new Square();
		square.setId("2");
		shapeMap.put(square.getId(), square);

		Rectangle rectangle = new Rectangle();
		rectangle.setId("3");
		shapeMap.put(rectangle.getId(), rectangle);
	}

}
