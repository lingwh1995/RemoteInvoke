package com.dragonsoft.designpattern.signleton.signleton_a;

/**
 * @author ronin
 */
public class Client {
    public static void main(String[] args) {
        Person person1 = new Person();
        Person person2 = new Person();
        System.out.println(person1 == person2);
    }
}
