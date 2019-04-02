package com.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.BlogMapper;
import com.ssm.pojo.Blog;
import com.ssm.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogMapper blogMapper;

	@Override
	public void saveBlog(Blog blog) {
		blogMapper.saveBlog(blog);
	}

	@Override
	public List<Blog> findAllBlogs() {
		List<Blog> blogs = blogMapper.findAllBlog();
		return blogs;
	}

	/**
	 * 使用线程，异步执行爬取任务。使用线程池，管理线程。
	 * 
	 * <div class="post_item_body">
			<h3>
				<a class="titlelnk" href="https://www.cnblogs.com/safety/p/9895107.html" target="_blank">
					Java&amp;eclipse 使用log4j
				</a>
			</h3>               	
    		<p class="post_item_summary">
				<a href="https://www.cnblogs.com/safety/" target="_blank"><img width="48" height="48" class="pfs" src="//pic.cnblogs.com/face/1400657/20181101183621.png" alt=""></a>    (一) 下载jar包 GitHub链接：https://github.com/1160700306/tools/blob/master/log4j-1.2.17.jar (现在官网上说要使用&nbsp;log4j-api-2.x 和 the log4j-core-2.x jar, 但是我试了不行, 所以还是用 ...
    		</p>              
     		<div class="post_item_foot">                    
    			<a href="https://www.cnblogs.com/safety/" class="lightblue">我不会干将</a> 
    			发布于 2018-11-02 11:06 
    			<span class="article_comment">
    				<a href="https://www.cnblogs.com/safety/p/9895107.html#commentform" title="" class="gray">
        			评论(0)
        			</a>
        		</span>
        		<span class="article_view">
        			<a href="https://www.cnblogs.com/safety/p/9895107.html" class="gray">阅读(2)</a>
        		</span>
       		</div>
	 </div>
	 * 
	 * 
	 **/
	@Override
	public void crawlBlog() {
		try {
			ExecutorService threadPool = Executors.newFixedThreadPool(3);// 创建有3个线程对象的线程池
			threadPool.execute(new Thread() {

				@Override
				public void run() {
					System.out.println("spider crawl blog started...");
					//https://www.cnblogs.com/cate/java/ 首页
					//https://www.cnblogs.com/cate/java/#p5 第六页
					try {
						//每一页20条,批量插入数据库是每次100条,每页爬取完都插入一次。
						for(int i=1;i<=5;i++) {
							String url="https://www.cnblogs.com/cate/java/";
							if(i>1) {
								url=url+"#p"+i;
							}
							
							Document document = Jsoup.connect(url).timeout(3000).get();
							if (document != null) {
								// System.out.println(document.title());
								// System.out.println(document.html());
								
								List<Blog> blogs=null;							
								Blog blog=null;
								Elements postItemElements = document.getElementsByClass("post_item");
								if (postItemElements != null && postItemElements.size() > 0) {
									blogs=new ArrayList<>();
									
									for (Element postItem : postItemElements) {
										Element postItemBody = postItem.select("div.post_item_body").first();

										Element titleEle = postItemBody.select("a.titlelnk").first();
										String href = titleEle.attr("href");
										String title = titleEle.html();

										Element summaryEle = postItemBody.select("p.post_item_summary").first();
										String summary = summaryEle.text();
										
										Element postItemFoot = postItemBody.select("div.post_item_foot").first();
										String author = postItemFoot.child(0).text();//第一个子节点，
										
										int readCount=0;
										String viewInfo = postItemFoot.select("span.article_view").first().text();//阅读(2)
										String numberpattern="\\d+";
										Pattern numPattern=Pattern.compile(numberpattern);
										Matcher matcher=numPattern.matcher(viewInfo);
										if(matcher.find()) {
											readCount=Integer.valueOf(matcher.group(0));
										}
										 
										blog=new Blog();
										blog.setTitle(title);
										blog.setBrief(summary);
										blog.setHref(href);
										blog.setAuthor(author);
										blog.setReadCount(readCount);
										blogs.add(blog);
									}
									
									blogMapper.batchSaveBlogs(blogs);
								}
							}
							
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					System.out.println();
					System.out.println("spider crawl blog end...");
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteBlogs(int[] ids) {
		blogMapper.deleteBlogs(ids);
		
	}

}
