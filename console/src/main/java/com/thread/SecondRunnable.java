package com.thread;

public class SecondRunnable implements Runnable
{
	private MyPanel mypanel;

	public SecondRunnable(MyPanel myPanel)
	{
		this.mypanel = myPanel;
	}

	@Override
	public void run()
	{
		while (true)
		{
			mypanel.secondRectHight += 20;
			if (mypanel.secondRectHight > 600)
			{
				mypanel.secondRectHight = 20;
			}
			mypanel.repaint();
			try
			{
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

}
