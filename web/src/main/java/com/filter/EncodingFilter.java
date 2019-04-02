package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter {
    public EncodingFilter() {

    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // 对于post提交的参数，直接使用setCharacterEncoding，但是对于get提交的
            // 参数，则需要对每个参数进行转码
            /**
             * 使用装饰者模式将request进行包装，重写getParameter方法，进行转码
             */
            request.setCharacterEncoding("utf-8");// post提交的参数

            response.setContentType("text/html;charset=utf-8");
            chain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

class MyHttpRequest extends HttpServletRequestWrapper {

    /*
     * 声明一个被装饰者类型的成员变量
     */
    private HttpServletRequest request;

    public MyHttpRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    /*
     * 重写getParameter方法
     */
    public String getParameter(String name) {
        try {
            String value = request.getParameter(name);
            //手动解码
            if ("GET".equals(request.getMethod().toUpperCase())) {
                value = new String(value.getBytes("iso-8859-1"), "utf-8");
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
