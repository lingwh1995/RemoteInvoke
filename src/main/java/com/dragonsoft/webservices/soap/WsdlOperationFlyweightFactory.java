package com.dragonsoft.webservices.soap;

import com.dragonsoft.webservices.utils.GenericsFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Operation;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取WsdlOperation对象的享元工厂
 * @author ronin
 * @version V1.0
 * @since 2019/8/12 13:44
 */
public class WsdlOperationFlyweightFactory {

    /**存放WsdlOperation享元池*/
    private Map<String,Flyweight> wsdlOperationPool = new HashMap<String,Flyweight>();
    /**存放url的享元池*/
    private Map<String,String> urlPool = new HashMap<String,String>();

    /**
     * 获取享元对象，该享元对象的内部状态就是共享的元数据，每一个共享的元数据代表一个webservice发布方提供的一个方法
     * @param wsdlUrl webservice发布方提供的WSDL
     * @param targetMethodName webservice发布方提供的接口的对应的方法名
     * @return 返回一个享元对象
     * @throws Exception
     */
    public synchronized Flyweight getWsdlOperation(String wsdlUrl, String targetMethodName) throws Exception {
        //拼接key
        String key = wsdlUrl + targetMethodName;
        Flyweight flyweight = null;
        if(wsdlOperationPool.containsKey(key)){
            flyweight = wsdlOperationPool.get(key);
        }else {
            //优化处理
            if(urlPool.containsKey(wsdlUrl)){
                //说明享元池中存放了该WSDL对应的所有方法，但是这些方法里面没有和方法名为methodName对应的方法
                //故返回一个空对象，防止调用该对象方法时发生空指针异常
                return new UnSharedFlyweight(wsdlUrl,targetMethodName);
            }
            //把url放入到url享元池中
            urlPool.put(wsdlUrl,wsdlUrl);
            //创建一个享元对象，放入到享元池
            WsdlProject project = new WsdlProject();
            WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, wsdlUrl);
            //扫描WSDL对应的wsdl文件,把一个对应所包含的所有方法(每一个方法对应一个WsdlOperation对象)全部放入的享元池中
            for (Operation operation : wsdls[0].getOperationList()) {
                //拼接key
                WsdlOperation wsdlOperation = (WsdlOperation) operation;
                String index =  wsdlUrl + wsdlOperation.getName();
                Flyweight element = GenericsFactory.init().getPrototypeInstance(WsdlOperationFlyweight.class,wsdlOperation);
                wsdlOperationPool.put(index,element);
                //返回目标享元对象
                if(key.equals(index)){
                    flyweight = element;
                }
            }
        }
        //返回享元对象
        return flyweight;
    }
}
