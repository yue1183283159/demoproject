package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class WhenTag extends SimpleTagSupport
{
	private boolean test;

	public void setTest(boolean test)
	{
		this.test = test;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		// 根据test的值输出标签体内容
		if (test)
		{
			this.getJspBody().invoke(null);

			// 得到父标签choose
			ChooseWhenTag parent = (ChooseWhenTag) this.getParent();
			parent.setFlag(test);
		}
	}
}
