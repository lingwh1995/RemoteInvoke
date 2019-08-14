package com.dragonsoft.webservices.soap;


import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 泛型工厂:根据不同的Class对象创建不同的单例对象
 * @author ronin
 */
public class GenericsFactory {

    /**私有化泛型工厂构造器*/
    private GenericsFactory(){}

    /**生产一个单例的泛型泛型工厂实例*/
    private static final GenericsFactory GENERICS_FACTORY = new GenericsFactory();

    /**
     * 获取泛型工厂实例的方法
     * @return 返回一个单例的泛型工厂示例
     */
    public static GenericsFactory init(){
        return GENERICS_FACTORY;
    }

    /**
     * 单例对象池
     */
    private static final Map<String,Object> SINGLETON_OBJECT_POOL = new HashMap<String,Object>();

    public <T> T getInstance(Class clazz){
        T target = null;
        try {
            Constructor declaredConstructor = clazz.getDeclaredConstructor(null);
            declaredConstructor.setAccessible(true);
            if(!SINGLETON_OBJECT_POOL.containsKey(clazz.getSimpleName())){
                target = (T)clazz.newInstance() ;
                SINGLETON_OBJECT_POOL.put(clazz.getSimpleName(),target);
            }else {
                target = (T)SINGLETON_OBJECT_POOL.get(clazz.getSimpleName());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return target;
    }
}
