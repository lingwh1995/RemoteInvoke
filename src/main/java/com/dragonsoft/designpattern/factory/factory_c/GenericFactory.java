package com.dragonsoft.designpattern.factory.factory_c;

/**
 * @author ronin
 */
public class GenericFactory {

    public <T> T getInstance(Class<T> clazz) throws Exception {
        return (T)clazz.newInstance();
    }
}
