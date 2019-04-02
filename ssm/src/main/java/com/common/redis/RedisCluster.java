package com.common.redis;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisCluster implements FactoryBean<JedisCluster>, InitializingBean {

	private Resource addressConfig;
	private String addressKeyPrefix;
	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig poolConfig;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	//通过HOSTAndPort文件将redis的节点信息写入Set集合中
	@Override
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		jedisCluster = new JedisCluster(haps, timeout, maxRedirections, poolConfig);
	}

	@Override
	public JedisCluster getObject() throws Exception {
		// 该对象交给Spring容器管理,可以在后台通过依赖注入的方式进入注入
		return jedisCluster;
	}

	@Override
	public Class<? extends JedisCluster> getObjectType() {

		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}

	@Override
	public boolean isSingleton() {

		return true;
	}

	private Set<HostAndPort> parseHostAndPort() {
		try {
			Properties prop = new Properties();
			prop.load(this.addressConfig.getInputStream());
			Set<HostAndPort> haps = new HashSet<>();
			for (Object key : prop.keySet()) {
				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}

				String val = (String) prop.get(key);
				boolean isIpPort = p.matcher(val).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip or port is invalid");
				}

				String[] ipAndPort = val.split(":");

				HostAndPort hostAndPort = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hostAndPort);
			}
			return haps;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
