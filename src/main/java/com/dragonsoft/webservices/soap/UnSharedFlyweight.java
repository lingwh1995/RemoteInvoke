package com.dragonsoft.webservices.soap;

/**
 * 不共享的、空的享元对象，防止调用时抛出空指针异常
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 15:52
 */
public class UnSharedFlyweight implements Flyweight {

    /**提示信息*/
    private String tipsMessage ;

    /**
     * @param wsdlUrl webservices接口发布方提供的WSDL
     * @param methodName webservices发布方提供的方法的接口对应的方法名
     */
    public UnSharedFlyweight(String wsdlUrl, String methodName) {
        tipsMessage = "wsdlUrl:"+wsdlUrl+",methodName:"+methodName+"享元池中存放了该wsdlUrl对应的所有方法，但是这些方法里面没有和方法名为methodName对应的方法";
    }

    /**
     * 获取soap协议格式的请求报文，此处会返回一个提示信息
     * @return 返回值为提示信息
     */
    @Override
    public String getSoapRequestMessage() {
        return tipsMessage;
    }
}
