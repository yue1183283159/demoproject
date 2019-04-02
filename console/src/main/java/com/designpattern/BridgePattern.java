package com.designpattern;

/*
 * 桥接（Bridge）是用于把抽象化与实现化解耦，使得二者可以独立变化
 * 这种模式涉及到一个作为桥接的接口，使得实体类的功能独立于接口实现类。这两种类型的类可被结构化改变而互不影响
 */
public class BridgePattern
{

	public static void main(String[] args)
	{
		// 使用 CircleShape 和 DrawAPI 类画出不同颜色的圆。
		CircleShape redCircle = new Circle(100, 100, 10, new RedCircle());
		redCircle.draw();

		CircleShape greenCircle = new Circle(200, 200, 20, new GreenCircle());
		greenCircle.draw();
	}
}

interface DrawAPI
{
	public void drawCircle(int radius, int x, int y);
}

class RedCircle implements DrawAPI
{
	@Override
	public void drawCircle(int radius, int x, int y)
	{
		System.out.println("Drawing Circle[ color: red, radius: " + radius + ", x: " + x + ", " + y + "]");
	}
}

class GreenCircle implements DrawAPI
{
	@Override
	public void drawCircle(int radius, int x, int y)
	{
		System.out.println("Drawing Circle[ color: green, radius: " + radius + ", x: " + x + ", " + y + "]");
	}
}

abstract class CircleShape
{
	protected DrawAPI drawAPI;

	protected CircleShape(DrawAPI drawAPI)
	{
		this.drawAPI = drawAPI;
	}

	public abstract void draw();
}

class Circle extends CircleShape
{
	private int x, y, radius;

	protected Circle(int x, int y, int radius, DrawAPI drawAPI)
	{
		super(drawAPI);
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	@Override
	public void draw()
	{
		drawAPI.drawCircle(radius, x, y);

	}

}
