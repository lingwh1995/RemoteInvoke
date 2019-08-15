package com.dragonsoft.webservices.utils;

import com.dragonsoft.webservices.soap.XmlUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

/**
 * 基于HttpClient发送Soap请求的工具类
 * @author ronin
 * @version V1.0
 * @since 2019/8/13 10:53
 */
public class HttpClientUtils {

    /**私有化工具类的构造方法*/
    private HttpClientUtils() {
        throw new UnsupportedOperationException();
    }

    /**默认的Socket请求超时默认timeout*/
    private static final Integer DEFAULT_SOCKET_TIMEOUT = 30000;
    /**从连接池获取连接的默认timeout*/
    private static final Integer DEFAULT_CONNETION_REQUEST_TIMEOUT = 30000;
    /**默认的连超超时请求默认timeout*/
    private static final Integer DEFAULT_CONNETION_TIMEOUT = 30000;

    /**
     * 使用HttpClient发送Soap请求
     * @param targetUrl webservices服务方提供的wsdlurl
     * @param soapMessage 原始的请求报文
     * @return 返回soap协议格式的响应报文
     */
    public static String sendSoapRequest(String targetUrl, String soapMessage) {
        //soap请求响应报文
        String soapResponseMessage = "";
        //创建HttpClientBuilder,并是利用HttpClientBuilder构建HttpClient对象
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(targetUrl);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_CONNETION_REQUEST_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNETION_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            StringEntity data = new StringEntity(soapMessage,Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                soapResponseMessage = EntityUtils.toString(httpEntity, "UTF-8");
            }
            response.close();
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return XmlUtils.transformXmlToFormatText(soapResponseMessage);
    }

}
