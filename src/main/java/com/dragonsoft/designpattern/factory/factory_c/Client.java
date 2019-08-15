package com.dragonsoft.designpattern.factory.factory_c;

/**
 * @author ronin
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Dog dog = new GenericFactory().getInstance(Dog.class);
        Person person = new GenericFactory().getInstance(Person.class);
    }
}
