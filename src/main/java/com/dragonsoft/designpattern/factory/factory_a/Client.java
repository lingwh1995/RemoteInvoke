package com.dragonsoft.designpattern.factory.factory_a;

/**
 * <pre>
 *      分析:
 *          1.创建类的时候直接使用了new这个关键字,格式为:
 *              Person person = new Person();
 *              这意味着这个类是调用者(Client)直接创建的
 * <pre/>
 * @author ronin
 */
public class Client {
    public static void main(String[] args) {
        //创建一个person类的实例
        Person person = new Person();
        person.setId("001");
        person.setName("张三");
        person.setAge("28");
        System.out.println(person);
    }
}
