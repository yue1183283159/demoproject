package com.ssm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssm.pojo.Blog;


/**
 * 对应mapper.xml中namespace="com.mybatis.BlogMapper",然后方法名跟id关联
 *
 **/
public interface BlogMapper {
	int saveBlog(Blog blog);

	List<Blog> findAllBlog();
	
	void deleteBlog(Integer id);
	
	//List<Blog> findSortedBlog(OrderCommand orderCommand);
	
	void deleteBlogs(@Param("ids") int[] ids);
	
	void batchSaveBlogs(@Param("blogs") List<Blog> blogs);
}
