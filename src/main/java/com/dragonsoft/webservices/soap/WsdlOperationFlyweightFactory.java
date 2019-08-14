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

//    /**饿汉式*/
//    private static final WsdlOperationFlyweightFactory flyweightFactory = new WsdlOperationFlyweightFactory();
//
//    /**
//     * 私有化享元工厂
//     */
//    private WsdlOperationFlyweightFactory(){}
//
//    /**
//     * 获取单例的享元工厂实例
//     * @return
//     */
//    public static WsdlOperationFlyweightFactory getInstance(){
//        return flyweightFactory;
//    }

    /**存放WsdlOperation享元池*/
    private Map<String,Flyweight> wsdlOperationPool = new HashMap<String,Flyweight>();
    /**存放url的享元池*/
    private Map<String,String> urlPool = new HashMap<String,String>();

    public synchronized Flyweight getWsdlOperation(String wsdlUrl, String methodName) throws Exception {
        //拼接key
        String key = wsdlUrl + methodName;
        Flyweight flyweight = null;
        if(wsdlOperationPool.containsKey(key)){
            flyweight = wsdlOperationPool.get(key);
        }else {
            //优化处理
            if(urlPool.containsKey(wsdlUrl)){
                //说明享元池中存放了该wsdlUrl对应的所有方法，但是这些方法里面没有和方法名为methodName对应的方法
                //故返回一个空对象，防止调用该对象方法时发生空指针异常
                return new UnSharedFlyweight(wsdlUrl,methodName);
            }
            //把url放入到url享元池中
            urlPool.put(wsdlUrl,wsdlUrl);
            //创建一个享元对象，放入到享元池
            WsdlProject project = new WsdlProject();
            WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, wsdlUrl);
            //扫描wsdlUrl对应的wsdl文件,把一个对应所包含的所有方法(每一个方法对应一个WsdlOperation对象)全部放入的享元池中
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
