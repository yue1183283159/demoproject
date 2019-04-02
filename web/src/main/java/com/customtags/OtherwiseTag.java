package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OtherwiseTag extends SimpleTagSupport
{
	@Override
	public void doTag() throws JspException, IOException
	{
		// 如何输出标签体内容
		// 根据test的值（和when相反的）控制标签体内容输出

		// 得到when标签的test的值？？？

		// 得到父标签choose
		ChooseWhenTag parent = (ChooseWhenTag) this.getParent();
		// 从父标签对象得到when标签的test值
		boolean test = parent.isFlag();

		if (!test)
		{
			this.getJspBody().invoke(null);
		}
	}
}
