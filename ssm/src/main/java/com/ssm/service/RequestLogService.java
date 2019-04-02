package com.ssm.service;

import java.util.List;

import com.ssm.pojo.RequestLog;

public interface RequestLogService {
	void saveRequestLog(RequestLog log);

	List<RequestLog> findRequestLogs();
}
