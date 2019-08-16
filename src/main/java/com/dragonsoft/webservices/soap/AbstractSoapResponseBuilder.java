package com.dragonsoft.webservices.soap;

import com.dragonsoft.webservices.utils.GenericsFactory;

import java.util.Map;

/**
 * <pre>
 *  Soap协议信息的抽象构建者，定义构建响应报文的三个主要构建步骤,第二步不是必须的:
 *      1.注册全局参数(注册全局参数的时候会对全局参数做预处理)
 *      2.获取原始的请求报文
 *      3.获取替换过参数的请求报文
 *      4.返回构建好的响应报文
 * <pre/>
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 16:16
 */
public abstract class AbstractSoapResponseBuilder {

    /**从泛型工厂中获取单例的全局参数构建者*/
    private static final GlobalParamsBuilder GLOBAL_PARAMS_BUILDER = GenericsFactory.init().getInstance(GlobalParamsBuilder.class);

    /**
     * 注册全局参数:适用于要调用的目标方法需要参数的情况
     * @param requestSource webservices接口发布方提供的WSDL或者wsdl格式的文本
     * @param targetMethodName webservices服务提供的接口对应目标方法的名称
     * @param requestMethodParams webservices服务提供的接口对应的具体的方法需要的参数，此处为map,map的key是请求的形参的值，value为实参的值,实参值不区分大小写
     */
    public void registerGlobalParams(String requestSource,String targetMethodName,Map<String,String> requestMethodParams){
        GLOBAL_PARAMS_BUILDER.requestSource(requestSource).targetMethodName(targetMethodName).requestMethodParams(requestMethodParams);
    }

    /**
     * {@link AbstractSoapResponseBuilder#registerGlobalParams(String, String, Map)}的重载方法
     * 注册全局参数:适用于要调用的目标方法不需要参数的情况
     * @param requestSource webservices服务发布方提供的WSDL或者wsdl格式的xml文本
     * @param targetMethodName webservices服务发布方提供的接口对应的方法名
     */
    public void registerGlobalParams(String requestSource,String targetMethodName){
        GLOBAL_PARAMS_BUILDER.requestSource(requestSource).targetMethodName(targetMethodName);
    }

    /**
     * 构建原始请求报文
     */
    public abstract void buildSoapRequestMessage();

    /**
     * 将原始请求报文中的占位符替换为实际的值
     */
    public abstract void buildDealedSoapRequestMessage();

    /**
     * 根据请求报文构建响应报文
     */
    public abstract void buildSoapResponseMessage();

    /**
     * 从全局参数构建者中获取构建好的Soap请求响应报文
     *      注意:获取到最终构建结果之后要进行垃圾回收操作，防止内存泄漏和内存溢出
     * @return 返回值为构建好的响应报文
     */
    protected String build(){
        //从全局参数构建者中获取到构建好的响应报文
        return GLOBAL_PARAMS_BUILDER.getSoapMessage();
    }

}
