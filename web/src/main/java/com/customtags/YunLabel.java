package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class YunLabel extends SimpleTagSupport
{
	private String captionId;

	public void setCaptionId(String value)
	{
		this.captionId = value;
	}

	@Override
	public void doTag() throws IOException
	{
		PageContext pageContext = (PageContext) this.getJspContext();
		JspWriter out = pageContext.getOut();
		String defaultVal = "N/A";
		if (this.captionId != null)
		{
			defaultVal = "YunLabel" + this.captionId;
		}
		out.write("<div><label for=\"id\">" + defaultVal + "</label</div>");
	}
}
