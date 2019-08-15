package com.dragonsoft.designpattern.signleton.signleton_c;

/**
 * @author ronin
 */
public class Client {
    public static void main(String[] args) {
        Signleton instance1 = Signleton.getInstance();
        Signleton instance2 = Signleton.getInstance();
        System.out.println(instance1 == instance2);
    }
}
