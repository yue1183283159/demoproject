package com.ssm.service;

import java.util.List;

import com.ssm.pojo.Blog;

public interface BlogService {
	void saveBlog(Blog blog);
	
	List<Blog> findAllBlogs();
	
	void crawlBlog();
	
	void deleteBlogs(int[] ids);

}
