package com.thread;

import javax.swing.JFrame;

public class AppMain
{

	public static void main(String[] args)
	{
		JFrame jFrame = new JFrame("Thread Demo");
		jFrame.setSize(600, 800);
		jFrame.setVisible(true);
		MyPanel myPanel = new MyPanel();
		jFrame.add(myPanel);

		FirstRunnable firstRunnable = new FirstRunnable(myPanel);
		Thread thread = new Thread(firstRunnable);
		thread.start();

		SecondRunnable secondRunnable = new SecondRunnable(myPanel);
		Thread thread2 = new Thread(secondRunnable);
		thread2.start();

		ThirdRunnable thirdRunnable = new ThirdRunnable(myPanel);
		Thread thread3 = new Thread(thirdRunnable);
		thread3.start();
		
		
	}

}
