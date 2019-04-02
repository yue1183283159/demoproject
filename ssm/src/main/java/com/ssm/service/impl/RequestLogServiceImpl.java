package com.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.RequestLogMapper;
import com.ssm.pojo.RequestLog;
import com.ssm.service.RequestLogService;

@Service
public class RequestLogServiceImpl implements RequestLogService {

	@Autowired
	private RequestLogMapper requestLogMapper;

	@Override
	public void saveRequestLog(RequestLog log) {
		requestLogMapper.saveRequestLog(log);

	}

	@Override
	public List<RequestLog> findRequestLogs() {

		return null;
	}

}
