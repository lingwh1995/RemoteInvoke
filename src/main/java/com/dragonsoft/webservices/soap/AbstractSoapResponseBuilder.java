package com.dragonsoft.webservices.soap;

import java.util.Map;

/**
 * <pre>
 * Soap协议信息的抽象构建者，定义构建响应报文的三个主要构建步骤,第二步不是必须的:
 *      1.注册全局参数(注册全局参数的时候会对全局参数做预处理)
 *      2.获取原始的请求报文
 *      3.获取替换过参数的请求报文
 *      4.获取响应报文
 *      5.垃圾回收
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
     * @param requestSource webservice接口发布方提供的wsdlUrl或者wsdl格式的文本
     * @param methodName webservice服务提供的接口对应的具体的方法名称
     * @param requestMethodParams webservice服务提供的接口对应的具体的方法需要的参数，此处为map,map的key是请求的形参的值，value为实参的值,实参值不区分大小写
     */
    public void registerGlobalParams(String requestSource,String methodName,Map<String,String> requestMethodParams){
        GLOBAL_PARAMS_BUILDER.requestSource(requestSource).targetMethodName(methodName).requestMethodParams(requestMethodParams);
    }

    /**
     * {@link AbstractSoapResponseBuilder#registerGlobalParams}的重载方法
     * 注册全局参数:适用于要调用的目标方法不需要参数的情况
     * @param requestSource webservice接口发布方提供的wsdlUrl或者wsdl格式的文本
     * @param methodName webservice服务提供的接口对应的具体的方法名称
     */
    public void registerGlobalParams(String requestSource,String methodName){
        GLOBAL_PARAMS_BUILDER.requestSource(requestSource).targetMethodName(methodName);
    }

    /**
     * 此步骤操作后会获取到请求报文,但是并不会替换对应的参数
     */
    public abstract void buildSoapRequestMessage();

    /**
     * 此步骤操作后会对请求报文做一个处理,会替换请求报文中对应的参数
     */
    public abstract void buildDealedSoapRequestMessage();

    /**
     * 此步骤操作后会构建响应报文
     */
    public abstract void buildSoapResponseMessage();

    /**
     * 从全局参数构建者中获取Soap请求响应报文
     *      注意:获取到最终构建结果之后要进行垃圾回收操作，防止内存泄漏和内存益处
     * @return 返回值为构建好的响应报文
     */
    protected String build(){
        final String soapMessage = GLOBAL_PARAMS_BUILDER.getSoapMessage();
        //回收ThreadLocal占用的内存
        GLOBAL_PARAMS_BUILDER.garbageCollection();
        return soapMessage;
    }

}
