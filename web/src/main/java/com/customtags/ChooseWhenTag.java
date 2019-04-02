package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ChooseWhenTag extends SimpleTagSupport
{
	//用于存储test的值
		private boolean flag;
		
		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		@Override
		public void doTag() throws JspException, IOException {
			//输出标签体内容即可
			this.getJspBody().invoke(null);
		}
	
	
}
