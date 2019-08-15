package com.dragonsoft.webservices.soap;

import com.dragonsoft.webservices.utils.GenericsFactory;

/**
 * 根据文本内容来构造Soap协议请求报文,返回的响应结果就是响应报文的格式，如果此构建者不符合要求，请
 *  AbstractSoapResponseBuilder，添加一个新的构建者
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 16:54
 */
public class TextSoapResponseBuilder extends AbstractSoapResponseBuilder {

    /**从泛型工厂中获取单例的基础构建者*/
    private static final BasicSoapResponseBuilder BASIC_SOAP_RESPONSE_BUILDER = GenericsFactory.init().getInstance(BasicSoapResponseBuilder.class);

    /**从泛型工厂中获取单例的全局参数构建者*/
    private static final GlobalParamsBuilder GLOBAL_PARAMS_BUILDER = GenericsFactory.init().getInstance(GlobalParamsBuilder.class);

    /**
     * 构建原始请求报文
     */
    @Override
    public void buildSoapRequestMessage() {
        GLOBAL_PARAMS_BUILDER.soapMessage(GLOBAL_PARAMS_BUILDER.getRequestSource());
    }

    /**
     * 将原始请求报文中的占位符替换为实际的值
     */
    @Override
    public void buildDealedSoapRequestMessage() {
        //调用基础构建者替换请求报文中参数的方法
        BASIC_SOAP_RESPONSE_BUILDER.buildDealedSoapRequestMessage();
    }

    /**
     * 根据请求报文构建响应报文
     * @return
     */
    @Override
    public void buildSoapResponseMessage() {
        //调用基础构建者根据请求报文发送请求获取响应报文的方法
        BASIC_SOAP_RESPONSE_BUILDER.buildSoapResponseMessage();
    }
}
