package com.dragonsoft.webservices.soap;

import com.dragonsoft.webservices.utils.HttpClientUtils;

import java.util.Map;

/**
 * 基础的Soap响应报文抽象构建者
 * @author ronin
 * @version V1.0
 * @since 2019/8/14 14:32
 */
public class BasicSoapResponseBuilder extends AbstractSoapResponseBuilder{

    /**从泛型工厂中获取单例的全局参数构建者*/
    private static final GlobalParamsBuilder GLOBAL_PARAMS_BUILDER = GenericsFactory.init().getInstance(GlobalParamsBuilder.class);

    /**
     * 此步骤操作后会获取到请求报文,但是并不会替换对应的参数
     */
    @Override
    public void buildSoapRequestMessage() {
        final String requestSource = GLOBAL_PARAMS_BUILDER.getRequestSource();
        final String methodName = GLOBAL_PARAMS_BUILDER.getMethodName();
        try {
            //使用享元工厂的客户端，获取到享元，每一个享元中的内部对象就是wsdl文档包含的信息
            final WsdlOperationFlyweightFactory wsdlOperationFlyweightFactory = GenericsFactory.init().getInstance(WsdlOperationFlyweightFactory.class);
            Flyweight wsdlOperation = wsdlOperationFlyweightFactory.getWsdlOperation(requestSource,methodName);
            //更新全局参数构建者中的soapMessage参数的值，使其的值为替换为参数的请求报文
            GLOBAL_PARAMS_BUILDER.soapMessage(wsdlOperation.getSoapRequestMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("根据参数wsdlUrl:"+requestSource+"和methodName:"+methodName+"从享元池中获取享元对象时失败");
        }
    }

    /**
     * 此步骤操作后会对请求报文做一个处理,会替换请求报文中对应的参数
     */
    @Override
    public void buildDealedSoapRequestMessage() {
        final String methodName = GLOBAL_PARAMS_BUILDER.getMethodName();
        final Map<String, String> requestMethodParams = GLOBAL_PARAMS_BUILDER.getRequestMethodParams();
        final String soapMessage = GLOBAL_PARAMS_BUILDER.getSoapMessage();
        //经过第一步操作之后,更新全局参数构建者中的soapMessage的值就是初始的请求报文
            //核心方法
        XmlUtils.register(soapMessage);
        //调用方法处理该参数,将原始请求报文中的?替换为具体的参数
        GLOBAL_PARAMS_BUILDER.soapMessage(XmlUtils.setMethodParamValue(methodName, requestMethodParams));
    }

    /**
     * 此步骤操作后会构建响应报文
     * @return
     */
    @Override
    public void buildSoapResponseMessage() {
        final String requestSource = GLOBAL_PARAMS_BUILDER.getRequestSource();
        final String soapMessage = GLOBAL_PARAMS_BUILDER.getSoapMessage();
        //根据拼接好的请求报文，发送请求，获得响应报文
        GLOBAL_PARAMS_BUILDER.soapMessage(HttpClientUtils.sendSoapRequest(requestSource, soapMessage));
    }
}
