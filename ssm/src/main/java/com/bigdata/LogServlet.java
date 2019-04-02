package com.bigdata;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class LogServlet
 */
//@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(LogServlet.class);

    public LogServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 对get请求串解码，防止中文乱码
        String qString = URLDecoder.decode(request.getQueryString(), "utf-8");
        // 请求串各指标以&符号分割
        String[] attrs = qString.split("\\&");
        StringBuffer sb = new StringBuffer();
        for (String attr : attrs) {
            // 每个指标以kv形式存在，中间用=分隔
            String[] kv = attr.split("=");
            String key = kv[0];// 指标名称
            String val = kv.length == 2 ? kv[1] : "";// 指标值
            sb.append(val + "|");//指标值以|分隔，移除key，可以减少网络传输压力以及数据库存储压力

        }

        sb.append(request.getRemoteAddr());//增加服务器端IP地址指标
        sb.append("\n"); //在一个flumeData文件中有多条数据，之间必须换行
        String logInfo = sb.toString();
        logger.info(logInfo);

        //记录日志，不需要返回结果
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
