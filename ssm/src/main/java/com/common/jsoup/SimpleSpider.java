package com.common.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimpleSpider {
	public static void main(String[] args) {
		// https://www.cnblogs.com/cate/java/ 爬取博客园中Java栏目下的博客数据
		try {
			String url = "https://www.cnblogs.com/cate/java/";
			Document document = Jsoup.connect(url).get();
			if (document != null) {
				// System.out.println(document.title());
				 //System.out.println(document.html());

				Elements postItemElements = document.getElementsByClass("post_item");
				if (postItemElements != null && postItemElements.size() > 0) {
					for (Element postItem : postItemElements) {
						Elements postItemBody = postItem.select("div.post_item_body");
						
						Element titleEle = postItemBody.select("a.titlelnk").first();
						String href = titleEle.attr("href");
						String title = titleEle.html();
						
						Element summaryEle = postItemBody.select("p.post_item_summary").first();
						String summary = summaryEle.text();

						System.out.println(title);
						System.out.println(href);
						System.out.println(summary);
						System.out.println();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}