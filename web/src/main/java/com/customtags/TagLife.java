package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagLife extends SimpleTagSupport
{
	private JspContext jspContext;

	public TagLife()
	{
		System.out.println("(1)construnctor function");

	}

	@Override
	public void setJspContext(JspContext jspContext)
	{
		System.out.println("(2)setJspContext");
		this.jspContext = jspContext;
	}

	@Override
	public void setParent(JspTag tag)
	{
		System.out.println("(3)setParent");
	}

	@Override
	public void setJspBody(JspFragment jspFragment)
	{
		System.out.println("(4)setJspBody");
	}

	@Override
	public void doTag() throws IOException
	{
		System.out.println("(5)doTag");
		PageContext pageContext = (PageContext) this.jspContext;
		JspWriter out = pageContext.getOut();
		out.write("<h3>Tag Life</h3>");
	}
}
