package com.thread;

public class FirstRunnable implements Runnable
{
	private MyPanel mypanel;

	public FirstRunnable(MyPanel myPanel)
	{
		this.mypanel = myPanel;
	}

	@Override
	public void run()
	{
		while (true)
		{
			mypanel.firstRectHight += 20;
			if (mypanel.firstRectHight > 600)
			{
				mypanel.firstRectHight = 20;
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
