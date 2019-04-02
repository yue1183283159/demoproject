package com.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssm.pojo.Blog;
import com.ssm.service.BlogService;

@Controller
@RequestMapping("blog")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@RequestMapping("index")
	public String index() {
		return "blog";
	}

	@RequestMapping("saveblog")
	@ResponseBody
	public String saveBlog() {
		Blog blog = new Blog();
		blog.setTitle("SSM Test Data .");
		blog.setAuthor("Lucy");
		blog.setBrief("Only for test.");
		blog.setContent("It is test data.");
		blog.setReadCount(10);
		blogService.saveBlog(blog);
		return "OK";
	}

	@RequestMapping("getallblogs")
	@ResponseBody
	public List<Blog> getAllBlogs() {
		List<Blog> blogs = blogService.findAllBlogs();
		return blogs;
	}

	// 从博客园爬取blog数据
	@RequestMapping("crawlblog")
	@ResponseBody
	public String crawlBlog() {
		blogService.crawlBlog();
		return "ok";
	}

	@RequestMapping("detete")
	@ResponseBody
	public String deleteBlogs(String ids) {
		if (ids != null && ids.length() > 0) {
			String[] idStrArr = ids.split(",");
			int[] idArr = new int[idStrArr.length];
			for (int i = 0; i < idStrArr.length; i++) {
				idArr[i] = Integer.valueOf(idStrArr[i]);
			}
			
			blogService.deleteBlogs(idArr);
		}
		return "ok";
	}
}
