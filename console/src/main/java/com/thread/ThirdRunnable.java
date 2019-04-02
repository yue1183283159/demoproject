package com.thread;

public class ThirdRunnable implements Runnable
{
	private MyPanel mypanel;

	public ThirdRunnable(MyPanel myPanel)
	{
		this.mypanel = myPanel;
	}

	@Override
	public void run()
	{
		while (true)
		{
			mypanel.thirdRectHight += 20;
			if (mypanel.thirdRectHight > 600)
			{
				mypanel.thirdRectHight = 20;
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
