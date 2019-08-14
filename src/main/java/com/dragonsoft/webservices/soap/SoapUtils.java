package com.dragonsoft.webservices.soap;

import java.util.Map;

/**
 * @author ronin
 * @version V1.0
 * @since 2019/8/13 16:20
 */
public class SoapUtils {

    /**私有化工具类的构造方法*/
    private SoapUtils() {
        throw new UnsupportedOperationException();
    }
    private static final UrlSoapResponseBuilder URL_SOAP_RESPONSE_BUILDER = GenericsFactory.init().getInstance(UrlSoapResponseBuilder.class);
    private static final TextSoapResponseBuilder TEXT_SOAP_RESPONSE_BUILDER = GenericsFactory.init().getInstance(TextSoapResponseBuilder.class);

    /**
     * 根据wsdl的url,方法名称以及命名参数通过调用webservice发布的服务,针对于方法不需要参数的情况
     * @param wsdlUrl webservice发布方提供的wsdl
     * @param targetMethodName webservice服务提供的接口对应的目标的方法名称
     * @return 返回值为根据上述条件获取到的请求报文
     */
    public static String getSoapResponseMessageByUrl(String wsdlUrl,String targetMethodName){
        //指挥者:SoapResponseDirector,具体的构建者是:UrlSoapResponseBuilder
        SoapResponseDirector soapResponseDirector = new SoapResponseDirector(URL_SOAP_RESPONSE_BUILDER);
        //注意:如果此种构建者无法满足需求，请继承AbstractSoapResponseBuilder,来进行扩展
        return soapResponseDirector.buildSoapResponseMessage(wsdlUrl, targetMethodName);
    }

    /**
     * 重载方法
     * 根据wsdl的url,方法名称通过调用webservice发布的服务,针对于方法需要参数的情况
     * @param wsdlUrl webservice发布方提供的wsdl
     * @param targetMethodName webservice服务提供的接口对应的目标的方法名称
     * @param buildSoapRequestMessage 命名参数,即webservice服务提供的接口对应的具体的方法需要的参数，此处为map,map的key是请求的形参的值，value为实参的值,实参值不区分大小写
     * @return 返回值为根据上述条件获取到的请求报文
     */
    public static String getSoapResponseMessageByUrl(String wsdlUrl,String targetMethodName,Map<String,String> buildSoapRequestMessage){
        //指挥者:SoapResponseDirector,具体的构建者是:UrlSoapResponseBuilder
        SoapResponseDirector soapResponseDirector = new SoapResponseDirector(URL_SOAP_RESPONSE_BUILDER);
        //注意:如果此种构建者无法满足需求，请继承AbstractSoapResponseBuilder,来进行扩展
        return soapResponseDirector.buildSoapResponseMessage(wsdlUrl, targetMethodName,buildSoapRequestMessage);
    }

    /**
     * 根据wsdl文本,方法名称通过调用webservice发布的服务,针对于方法需要参数的情况
     * @param wsdlText 自己构造的wsdl文本
     * @param targetMethodName webservice服务提供的接口对应的目标的方法名称
     * @param buildSoapRequestMessage 命名参数,即webservice服务提供的接口对应的具体的方法需要的参数，此处为map,map的key是请求的形参的值，value为实参的值,实参值不区分大小写
     * @return 返回值为根据上述条件获取到的请求报文
     */
    public static String getSoapResponseMessageByText(String wsdlText,String targetMethodName,Map<String,String> buildSoapRequestMessage){
        //指挥者:SoapResponseDirector,具体的构建者是:TextSoapResponseBuilder
        SoapResponseDirector soapResponseDirector = new SoapResponseDirector(TEXT_SOAP_RESPONSE_BUILDER);
        //注意:如果此种构建者无法满足需求，请继承AbstractSoapResponseBuilder,来进行扩展
        return soapResponseDirector.buildSoapResponseMessage(wsdlText, targetMethodName,buildSoapRequestMessage);
    }

    /**
     * 重载方法
     * 根据wsdl文本,方法名称通过调用webservice发布的服务,针对于方法需要参数的情况
     * @param wsdlText 自己构造的wsdl文本
     * @param targetMethodName webservice服务提供的接口对应的目标的方法名称
     * @return 返回值为根据上述条件获取到的请求报文
     */
    public static String getSoapResponseMessageByText(String wsdlText,String targetMethodName){
        //指挥者:SoapResponseDirector,具体的构建者是:TextSoapResponseBuilder
        SoapResponseDirector soapResponseDirector = new SoapResponseDirector(TEXT_SOAP_RESPONSE_BUILDER);
        //注意:如果此种构建者无法满足需求，请继承AbstractSoapResponseBuilder,来进行扩展
        return soapResponseDirector.buildSoapResponseMessage(wsdlText, targetMethodName);
    }

}
