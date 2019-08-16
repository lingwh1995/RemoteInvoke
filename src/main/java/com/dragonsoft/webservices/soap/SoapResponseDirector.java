package com.dragonsoft.webservices.soap;

import com.dragonsoft.webservices.utils.GenericsFactory;

import java.util.Map;

/**
 * 指挥者:指挥具体的构建者完成构建
 * @author ronin
 * @version V1.0
 * @since 2019/8/13 13:17
 */
public class SoapResponseDirector {

    /**从泛型工厂中获取单例的全局参数构建者*/
    private static final GlobalParamsBuilder GLOBAL_PARAMS_BUILDER = GenericsFactory.init().getInstance(GlobalParamsBuilder.class);

    /**私有化构造需要使用时从工厂中获取此类实例*/
    private SoapResponseDirector(){
        throw new UnsupportedOperationException();
    }

    /**持有一个具体构建者的引用*/
    private AbstractSoapResponseBuilder concreteSoapResponseBuilder;

    /**
     * 有参构造
     * @param soapResponseBuilder 具体的构建者
     */
    public SoapResponseDirector(AbstractSoapResponseBuilder soapResponseBuilder) {
        this.concreteSoapResponseBuilder = soapResponseBuilder;
    }

    /**
     * 当webservices服务提供方发布目标方法有参数时调用这个构建行为
     * @param requestSource webservices接口发布方提供的WSDL或者wsdl格式的文本
     * @param targetMethodName webservices服务提供的接口对应的目标的方法名称
     * @param requestMethodParams webservices服务提供的接口对应的具体的方法需要的参数，此处为map,map的key是请求的形参的值，value为实参的值,实参值不区分大小写
     * @return 返回值为构建好的请求报文
     */
    public String buildSoapResponseMessage(String requestSource,String targetMethodName,Map<String,String> requestMethodParams){
        String soapResponseMessage = "";
        try {
            //第一步:注册并预处理参数
            concreteSoapResponseBuilder.registerGlobalParams(requestSource,targetMethodName,requestMethodParams);
            //第二步:获取请求报文
            concreteSoapResponseBuilder.buildSoapRequestMessage();
            //第三步:将请求报文中的?替换为实际传入的参数
            concreteSoapResponseBuilder.buildDealedSoapRequestMessage();
            //第四步:根据前两步操作获取到的请求参数报文，发送soap请求，从而得到响应报文
            concreteSoapResponseBuilder.buildSoapResponseMessage();
            soapResponseMessage = concreteSoapResponseBuilder.build();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //回收ThreadLocal占用的内存
            GLOBAL_PARAMS_BUILDER.garbageCollection();
        }
        return soapResponseMessage;
    }

    /**
     * {@link SoapResponseDirector#buildSoapResponseMessage(String, String, Map)}的重载方法
     * 当webservices服务发布方提供方发布目标方法不需要参数时调用这个构建行为
     * @param requestSource webservices服务发布方提供的WSDL或者wsdl格式的文本
     * @param targetMethodName webservices服务发布方提供的接口对应的方法
     * @return 返回值为构建好的请求报文
     */
    public String buildSoapResponseMessage(String requestSource,String targetMethodName){
        String soapResponseMessage = "";
        try {
            //第一步:注册并预处理参数
            concreteSoapResponseBuilder.registerGlobalParams(requestSource,targetMethodName);
            //第二步:获取请求报文
            concreteSoapResponseBuilder.buildSoapRequestMessage();
            //第三步:根据前两步操作获取到的请求参数报文，发送soap请求，从而得到响应报文
            concreteSoapResponseBuilder.buildSoapResponseMessage();
            soapResponseMessage = concreteSoapResponseBuilder.build();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //回收ThreadLocal占用的内存
            GLOBAL_PARAMS_BUILDER.garbageCollection();
        }
        return soapResponseMessage;
    }
}
