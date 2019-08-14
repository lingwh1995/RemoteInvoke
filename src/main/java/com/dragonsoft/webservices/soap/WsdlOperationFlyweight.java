package com.dragonsoft.webservices.soap;

import com.eviware.soapui.impl.wsdl.WsdlOperation;

/**
 * 具体的享元对象
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 13:33
 */
public class WsdlOperationFlyweight implements Flyweight {

    /**内部状态:即需要被共享的元对象*/
    protected WsdlOperation wsdlOperation;

    /**
     * 构造器
     * @param wsdlOperation
     */
    public WsdlOperationFlyweight(WsdlOperation wsdlOperation) {
        this.wsdlOperation = wsdlOperation;
    }

    /**
     * 获取soap协议格式的请求报文
     *
     * @return
     */
    @Override
    public String getSoapRequestMessage() {
        return wsdlOperation.createRequest(true);
    }

}
