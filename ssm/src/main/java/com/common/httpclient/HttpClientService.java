package com.common.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpClientService {
    @Autowired(required = false)
    private CloseableHttpClient httpClient;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * 编辑工具类的思路
     * 1.编辑Get()工具类
     * 1.1 get请求如何添加参数??? www.jt.com/addUser?id=1&name=tom
     * 1.2	get请求如何解决获取参数后的乱码问题 设定字符集
     * 1.3 应该重构多个get方法满足不同的需求
     * 2.编辑POST()工具类
     * 2.1 POST请求如何传递参数 表单提交时采用POST请求
     * 2.2 POST乱码相对而言比较好解决
     * 2.3 满足不同的post需求
     *
     * @throws Exception
     */

    public String doGet(String uri, Map<String, String> params, String encode) throws Exception {
        if (params != null) {
            //定义拼接参数的工具类
            URIBuilder builder = new URIBuilder(uri);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }

            //www.jt.com/addUser?id=1&name=tom
            uri = builder.build().toString();

        }

        //定义字符集编码
        if (null == encode) {
            encode = "UTF-8";
        }
        //定义get请求
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(requestConfig);

        //准备发送请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), encode);
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String doGet(String uri, Map<String, String> params) throws Exception {
        return doGet(uri, params, null);
    }

    public String doGet(String uri) throws Exception {
        return doGet(uri, null, null);
    }

    //定义post提交，post提交不能拼接字符串
    public String doPost(String uri, Map<String, String> params, String encode) throws Exception {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);

        //如果有提交参数则进行处理
        if (params != null) {
            //定义数据封装的集合
            List<NameValuePair> pairs = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            if (null == encode) {
                encode = "utf-8";
            }

            //定义form表单对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairs, encode);
            //将form表单对象添加到post对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity(), encode);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String doPost(String uri, Map<String, String> params) throws Exception {
        return doPost(uri, params, null);
    }

    public String doPost(String uri) throws Exception {
        return doPost(uri, null, null);
    }

}
