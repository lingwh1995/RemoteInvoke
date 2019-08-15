package com.dragonsoft.designpattern.signleton.signleton_c;

/**
 * <pre>
 *      饿汉式单例类创建步骤:
 *          1.私有化构造方法
 *          2.创建单例对象
 *          3.创建返回单例实例的方法
 * <pre/>
 * @author ronin
 */
public class Signleton {
    /**
     * 1.私有化构造方法
     */
    private Signleton() {}

    /**
     * 2.创建单例对象
     */
    private static final Signleton SINGLETON = new Signleton();

    /**
     * 3.创建返回单例实例的方法
     * @return
     */
    public static Signleton getInstance(){
        return SINGLETON;
    }

}
