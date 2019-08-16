package com.dragonsoft.webservices.soap;

import com.dragonsoft.webservices.utils.GenericsFactory;
import com.dragonsoft.webservices.utils.HttpClientUtils;

import java.util.Map;

/**
 * <pre>
 *  基础的Soap协议格式的响应报文构建者:
 *      此构建者主要完成三个操作:
 *          1.根据webervices服务发布方提供的WSDL构建请求报文 {@link BasicSoapResponseBuilder#buildSoapRequestMessage()}
 *          2.替换请求报文中的参数 {@link BasicSoapResponseBuilder#buildDealedSoapRequestMessage()}
 *          3.根据请求报文构建响应报文 {@link BasicSoapResponseBuilder#buildSoapResponseMessage()}
 *      作用:
 *          基础响应报文构建者中提供的三个方法，在其他构建者适用的情况下可以直接调用此构建者中方法完成相关操作
 *          1.调用基础构建者中构建请求报文的方法
 *              eg:{@link UrlSoapResponseBuilder#buildSoapRequestMessage()}
 *          2.调用基础构建者中替换请求报文中的参数的方法
 *              eg:{@link UrlSoapResponseBuilder#buildDealedSoapRequestMessage()}
 *          3.调用基础构建者中根据请求报文构建响应报文方法
 *              eg:{@link UrlSoapResponseBuilder#buildSoapResponseMessage()}
 * <pre/>
 *
 * @author ronin
 * @version V1.0
 * @since 2019/8/14 14:32
 */
public class BasicSoapResponseBuilder extends AbstractSoapResponseBuilder{

    /**从泛型工厂中获取单例的全局参数构建者*/
    private static final GlobalParamsBuilder GLOBAL_PARAMS_BUILDER = GenericsFactory.init().getInstance(GlobalParamsBuilder.class);

    /**
     * 构建原始请求报文
     */
    @Override
    public void buildSoapRequestMessage() {
        //从全局参数构建者获取请求源，此处为webservices服务发布方提供的WSDL
        final String requestSource = GLOBAL_PARAMS_BUILDER.getRequestSource();
        //从全局参数构建者中获取到要调用的目标方法的名称
        final String methodName = GLOBAL_PARAMS_BUILDER.getTargetMethodName();
        try {
            //从泛型工厂中获取单例的WsdlOperation享元工厂
            WsdlOperationFlyweightFactory wsdlOperationFlyweightFactory = GenericsFactory.init().getInstance(WsdlOperationFlyweightFactory.class);
            //从享元工厂中获取享元对象,每一个享元中的内部对象就代表webservices服务发布方提供的一个具体方法
            Flyweight wsdlOperation = wsdlOperationFlyweightFactory.getWsdlOperation(requestSource,methodName);
            //更新全局参数构建者中的soapMessage参数的值，使其的值为替换为参数的请求报文
            GLOBAL_PARAMS_BUILDER.soapMessage(wsdlOperation.getSoapRequestMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("根据参数wsdlUrl:"+requestSource+"和methodName:"+methodName+"从享元池中获取享元对象时失败");
        }
    }

    /**
     * 将原始请求报文中的占位符替换为实际的值
     */
    @Override
    public void buildDealedSoapRequestMessage() {
        //从全局参数构建者中获取到要调用的目标方法的名称
        final String targetMethodName = GLOBAL_PARAMS_BUILDER.getTargetMethodName();
        //从全局参数构建者中获取到要调用的目标方法需要的参数
        final Map<String, String> requestMethodParams = GLOBAL_PARAMS_BUILDER.getRequestMethodParams();
        //从全局参数构建者中获取到原始的请求报文
        final String soapMessage = GLOBAL_PARAMS_BUILDER.getSoapMessage();
        //处理该参数,将原始请求报文中的?替换为具体的参数
        GLOBAL_PARAMS_BUILDER.soapMessage(XmlUtils.setMethodParamValue(soapMessage,targetMethodName, requestMethodParams));
    }

    /**
     * 根据请求报文构建响应报文
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
