package com.dragonsoft.webservices.soap;

import java.util.Map;

/**
 * 基础构建行为的指挥者，此指挥者指挥基础构建者完成构建行为
 * @author ronin
 * @version V1.0
 * @since 2019/8/13 13:17
 */
public class SoapResponseDirector {
    /**持有一个具体构建者的引用*/
    private AbstractSoapResponseBuilder concreteSoapResponseBuilder;

    public SoapResponseDirector(AbstractSoapResponseBuilder soapResponseBuilder) {
        this.concreteSoapResponseBuilder = soapResponseBuilder;
    }

    /**
     * 构建响应报由指挥者决定:当方法有参数时调用这个构建行为
     * @param requestSource webservice接口发布方提供的wsdlUrl或者wsdl格式的文本
     * @param targetMethodName webservice服务提供的接口对应的目标的方法名称
     * @param requestMethodParams webservice服务提供的接口对应的具体的方法需要的参数，此处为map,map的key是请求的形参的值，value为实参的值,实参值不区分大小写
     * @return
     */
    public String buildSoapResponseMessage(String requestSource,String targetMethodName,Map<String,String> requestMethodParams){
        //第一步:注册并预处理参数
        concreteSoapResponseBuilder.registerGlobalParams(requestSource,targetMethodName,requestMethodParams);
        //第二步:获取请求报文
        concreteSoapResponseBuilder.buildSoapRequestMessage();
        //第三步:将请求报文中的?替换为实际传入的参数
        concreteSoapResponseBuilder.buildDealedSoapRequestMessage();
        //第四步:根据前两步操作获取到的请求参数报文，发送soap请求，从而得到响应报文
        concreteSoapResponseBuilder.buildSoapResponseMessage();
        return concreteSoapResponseBuilder.build();
    }

    /**
     * 构建响应报由指挥者决定:当方法没有参数时调用这个构建行为
     * @param requestSource webservice接口发布方提供的wsdlUrl或者wsdl格式的文本
     * @param targetMethodName webservice服务提供的接口对应的目标的方法名称
     * @return
     */
    public String buildSoapResponseMessage(String requestSource,String targetMethodName){
        //第一步:注册并预处理参数
        concreteSoapResponseBuilder.registerGlobalParams(requestSource,targetMethodName);
        //第二步:获取请求报文
        concreteSoapResponseBuilder.buildSoapRequestMessage();
        //第三步:根据前两步操作获取到的请求参数报文，发送soap请求，从而得到响应报文
        concreteSoapResponseBuilder.buildSoapResponseMessage();
        return concreteSoapResponseBuilder.build();
    }
}
