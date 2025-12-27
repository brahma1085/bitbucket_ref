package edu;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class MyCustomTag implements Tag {
	Tag parent;
	int count;
	PageContext context;

	public void setCount(int c) {
		count = c;
	}

	public int doEndTag() throws JspException {
		try {
			context.getOut().println("<h1>end executed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (count > 0)
			return EVAL_PAGE;
		return SKIP_PAGE;
	}

	public int doStartTag() throws JspException {
		if (count == 0)
			return SKIP_BODY;
		else {
			JspWriter out = context.getOut();
			try {
				out.println("<h1>start executed");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return EVAL_BODY_INCLUDE;
		}
	}

	public Tag getParent() {
		return parent;
	}

	public void release() {
	}

	public void setPageContext(PageContext arg0) {
		context = arg0;
	}

	public void setParent(Tag arg0) {
		parent = arg0;
	}
}
