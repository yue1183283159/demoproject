package com.common.sso;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;

public class UserServiceImpl implements IUserService {
	RedisTemplate redisTemplate = new RedisTemplate<>();
	/**
	 * 1.验证用户名和密码是否正确
	 *  2.需要将用户的明文加密为密文 
	 *  3.如果用户信息校验通过.准备redis缓存数据
	 *   4.生成ticket=* md5（“JT_TICKET_” + System.currentTime + username） 
	 * 5.将User对象转化为JSON数据
	 * 6.将ticket和UserJSON数据存入redis 
	 * 7.return 返回ticket
	 * 
	 * 密码在从其他客户端传入的时候，就是经过加密的，不允许明文传输密码。
	 * 前台页面传输密码，可以使用base64加密，到后台再解密。然后经过加密之后再通过httpclient传递到验证中心。 ticket传输也要经过加密处理
	 */
	@Override
	public String login(String username, String password) {
		boolean result = checkUserInfo(username, password);
		if (result) {
			// 准备ticket信息
			String ticket = "xx_tickent_" + System.currentTimeMillis() + username;
			// 加密ticket。
			String md5Ticket = DigestUtils.md5Hex(ticket);
			// 将ticket数据存入redis中
			
			redisTemplate.opsForSet().add(md5Ticket, username);
			return md5Ticket;
		}

		return null;
	}

	private boolean checkUserInfo(String username, String password) {
		// 从数据库中查询
		if (username.equals("admin") && password.equals("123456")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkLogin(String ticket) {
		//如果ticket在redis中，证明是登录用户
		if( redisTemplate.hasKey(ticket)) {
			return true;
		}
		return false;
	}
	
	//用户登出，客户端要通知认证中心从ticket中移除ticket
	public void removeTicket(String ticket) {
		redisTemplate.delete(ticket);
		
	}

}
