package com.customtags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AttrTag extends SimpleTagSupport {
    private int num;
    private Boolean upper;

    public void setUpper(Boolean upper) {
        this.upper = upper;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void doTag() throws JspException, IOException {
        String content = "";
        if (upper) {
            JspFragment jspBody = this.getJspBody();
            StringWriter sw = new StringWriter();
            jspBody.invoke(sw);
            content = sw.toString();
            content = content.toUpperCase();
        }

        for (int i = 0; i < num; i++) {
            this.getJspContext().getOut().write(content);
            //this.getJspBody().invoke(null);
        }
    }
}
