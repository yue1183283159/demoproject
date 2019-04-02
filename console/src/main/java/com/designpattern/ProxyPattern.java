package com.designpattern;

/**
 * 代理模式（Proxy Pattern），一个类代表另一个类的功能，属于结构型模式。 何时使用：想在访问一个类时做一些控制。
 * 装饰器模式是为了增强功能，代理模式时为了加以控制。
 * 既能在具体类上实现代理，也能对接口实现代理。
 * 如果接口没有实现类，需要运行时创建接口的代理对象
 **/
public class ProxyPattern {
	public static void main(String[] args) {
		//代理类中对被代理的类做了控制
		Image image=new ProxyImage("test.jpg");
		image.display();//在代理类中，要初始化RealImage类，在初始化的过程中调用loadFromDisk方法。
		System.out.println("");
		image.display();//第一次已经初始化了RealImage,第一次调用方法适合，不需要初始化方法了。
	}
}

interface Image {
	void display();
}

class RealImage implements Image {

	private String fileName;

	public RealImage(String fileName) {
		this.fileName = fileName;
		loadFromDisk(fileName);
	}

	private void loadFromDisk(String fileName) {
		System.out.println("Loading " + fileName);
	}

	@Override
	public void display() {
		System.out.println("Display " + fileName);
	}
}

class ProxyImage implements Image {

	private RealImage realImage;

	private String fileName;

	public ProxyImage(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void display() {
		if (realImage == null) {
			realImage = new RealImage(fileName);
		}

		realImage.display();
	}

}
