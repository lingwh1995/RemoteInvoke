package com.dragonsoft.designpattern.factory.factory_d;

/**
 * @author ronin
 */
public class Client {
    public static void main(String[] args) throws Exception {
//        Dog dog = new GenericFactory().getInstance(Dog.class);
//        Person person = new GenericFactory().getInstance(Person.class);
        GenericFactory instance1 = GenericFactory.init();
        GenericFactory instance2 = GenericFactory.init();
        System.out.println(instance1 == instance2);

        Dog dog = instance1.getInstance(Dog.class);
        Person person = instance1.getInstance(Person.class);
    }
}
