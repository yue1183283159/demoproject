package com.ssm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ssm.pojo.RequestLog;

public interface RequestLogMapper {

	@Insert("insert into request_log(url,data,elapsed_time) values(#{url},#{data},#{elapsedTime})")
	void saveRequestLog(RequestLog log);

	@Select("select id, url,data,elapsed_time,addon from request_log where flag_delete=0")
	@Results({
		@Result(property="elapsedTime",column="elapsed_time")
	})
	List<RequestLog> findRequestLog();
}
