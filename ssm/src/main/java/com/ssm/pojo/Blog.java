package com.ssm.pojo;

import java.util.Date;

/**
 * 定义model的时候，属性类型一定要是包装类型，因为 如果使用基本类型，会有默认值。
 * 比如：如果使用int定义ID，那么ID没有赋值的时候，会使用默认值0。
 **/
public class Blog {
	private Integer id;
	private String title;
	private String brief;
	private String author;
	private String content;
	private Integer readCount = 0;
	private String href;
	private Date addon;
	private short flagDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddon() {
		return addon;
	}

	public void setAddon(Date addon) {
		this.addon = addon;
	}

	public short getFlagDelete() {
		return flagDelete;
	}

	public void setFlagDelete(short flagDelete) {
		this.flagDelete = flagDelete;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHref() {
		return href;
	}

	@Override
	public String toString() {

		return "Blog{id=" + id + ",title=" + title + ", author=" + author + ",content=" + content + ",readCount="
				+ readCount + ",addon=" + addon + "}";
	}

}
