package com.dragonsoft.designpattern.factory.factory_d;

/**
 * @author ronin
 */
public class GenericFactory {

    private GenericFactory(){}

    private static final  GenericFactory GENERIC_FACTORY = new GenericFactory();

    public static GenericFactory init(){
        return GENERIC_FACTORY;
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clazz) throws Exception {
        return clazz.newInstance();
    }
}
