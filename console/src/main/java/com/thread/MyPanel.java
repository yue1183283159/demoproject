package com.thread;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MyPanel extends JPanel
{
	int firstRectHight = 20;
	int secondRectHight = 20;
	int thirdRectHight = 30;
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.red);
		g.drawRect(10, 20, 50, firstRectHight);
		g.drawRect(200, 20, 50, secondRectHight);
		g.drawRect(400, 20, 50, thirdRectHight);
	}
}
