package com.common.redis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class ArticleVoteRedis {
	private RedisTemplate<String, Object> redisTemplate;

	public ArticleVoteRedis(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	// 一周的过期时间，单位秒
	private final static long ONE_WEEK_IN_SECONDS = 7 * 24 * 60 * 60;
	private final static long VOTE_SCORE = 10;
	private final static int ARTICLES_PER_PAGE = 25;

	/**
	 * 一个用户发布一个新文章。将用户id放入文章投票集合中，并设置过期时间
	 */
	public String postArticle(String userId, String title, String link) {
		RedisUtils rUtils = new RedisUtils(this.redisTemplate);
		String newId = String.valueOf(rUtils.hincr("article", "article_id", 1));

		String votedKey = "voted:" + newId;
		rUtils.sSet(votedKey, userId);// 使用set,每个用户只能在一篇稳重投票一次，使用set存储
		rUtils.expire(votedKey, ONE_WEEK_IN_SECONDS);

		String articleId = "article:" + newId;
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// String now=sdf.format(new Date());
		long now = new Date().getTime();
		Map<String, Article> objMap = new HashMap<String, Article>();
		// 一篇文章使用id作为key，然后存储文章具体信息的对象，使用hash存储。key不能重复，保证文章唯一。
		Article article=new Article();
		article.setId(newId);
		article.setTitle(title);
		article.setLink(link);
		article.setPoster(userId);
		article.setTime(now);
		article.setVotes(1);
		objMap.put(articleId, article);
		//rUtils.hmset(articleId, objMap);
		redisTemplate.opsForHash().putAll(articleId, objMap);
//		objMap.put("title", title);
//		objMap.put("link", link);
//		objMap.put("poster", userId);
//		objMap.put("time", now);
//		objMap.put("votes", 1);
		//rUtils.hmset(articleId, objMap);

		// 将文章添加到根据发布时间排序的有序集合和根据评分排序的有序集合中
		rUtils.zsSet("score:", articleId, VOTE_SCORE + now);
		rUtils.zsSet("time:", articleId, now);

		// 输出所有排序的值和得分。
		// System.out.println(rUtils.zsGet("time:", 0, now + 100L));
		// Set<TypedTuple<Object>> scoreResult = rUtils.zsGetWithScores("time:", 0, now
		// + 100L);
		// for (TypedTuple<Object> typedTuple : scoreResult) {

		// System.out.println(typedTuple.getValue() + "|" + typedTuple.getScore());
		// }

		return articleId;
	}

	// 获取分页的文章
	public void getArticles(int page, String order) {
		order = "score:";
		RedisUtils rUtils = new RedisUtils(this.redisTemplate);
		int start = (page - 1) * ARTICLES_PER_PAGE;
		int end = start + ARTICLES_PER_PAGE - 1;
		// 设置获取文章的
		Integer id = (Integer) rUtils.hget("article", "article_id");
		System.out.println(id);

		Set<String> keys = redisTemplate.keys("article:*");
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String articleId = iterator.next();
			Map<Object, Object> article = redisTemplate.opsForHash().entries(articleId);
			System.out.println(article);
		}

	}

	public void voteArticle(int userId, String articleId) {
		// 检测是否可以对文章进行投票
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// String date= sdf.format(new Date().getTime()-ONE_WEEK_IN_SECONDS);
		// System.out.println(date);
		long cutOff = new Date().getTime() - ONE_WEEK_IN_SECONDS;
		Map<Object, Object> articleObj = redisTemplate.opsForHash().entries(articleId);
		System.out.println(articleObj);
		Article article=	(Article)articleObj.get(articleId);
		System.out.println(article);
		System.out.println(article.getId());
		System.out.println(article.getTitle());
		System.out.println(article.getTime());
	}

}

class Article {
	private String id;
	private String title;
	private String link;
	private String poster;
	private Long time;
	private Integer votes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
}
