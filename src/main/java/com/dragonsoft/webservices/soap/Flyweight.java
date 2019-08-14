package com.dragonsoft.webservices.soap;

/**
 * 享元接口
 * author ronin
 * @version V1.0
 * @since 2019/8/12 13:22
 */
public interface Flyweight {

    /**
     * 获取soap协议格式的请求报文
     * @return
     */
    String getSoapRequestMessage();
}
