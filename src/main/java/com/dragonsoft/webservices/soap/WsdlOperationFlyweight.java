package com.dragonsoft.webservices.soap;

import com.eviware.soapui.impl.wsdl.WsdlOperation;

/**
 * 具体的享元对象
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 13:33
 */
public class WsdlOperationFlyweight implements Flyweight {

    /**私有化构造，需要此对象时从泛型工厂获取*/
    private WsdlOperationFlyweight(){
        throw new UnsupportedOperationException();
    }

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
     * @return 返回值为原始的请求报文
     */
    @Override
    public String getSoapRequestMessage() {
        return wsdlOperation.createRequest(true);
    }

}
