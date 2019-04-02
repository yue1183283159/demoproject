package com.customtags;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ForEachTag extends SimpleTagSupport
{
	private Object items;
	private String var;

	public void setItems(Object items)
	{
		this.items = items;
	}

	public void setVar(String var)
	{
		this.var = var;
	}

	public void doTag() throws JspException, IOException
	{
		JspContext jspContext = this.getJspContext();
		PageContext pageContext = (PageContext) jspContext;
		Collection<?> collection = null;
		if (items instanceof List)
		{
			collection = (List<?>) items;
		}
		if (items instanceof Map)
		{
			Map<?, ?> map = (Map<?, ?>) items;
			collection = map.entrySet();
		}

		for (Object obj : collection)
		{
			pageContext.setAttribute(var, obj);
			this.getJspBody().invoke(null);
		}
	}
}
