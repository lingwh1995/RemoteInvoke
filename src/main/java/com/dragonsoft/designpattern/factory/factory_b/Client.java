package com.dragonsoft.designpattern.factory.factory_b;

/**
 * <pre>
 *      通过工厂去获取对象的实例和通过new有什么区别呢?
 *          1.IOC:控制反转了,将创建对象的权力交给第三方了，这里第三方指的是DogFactory和PersonFactory
 *          2.对于调用者(Client)而言，不需要关注对象是怎么创建的，调用者(Client)只是在需要某个类的实例的时候向工厂要
 *             工厂内部怎么创建了，经过了多少步骤，每一步具体干了什么操作，调用者(Client)并不关心，换而言之，工厂隐藏
 *             了内部实现，通过提供一个外部访问接口和调用者(Client)进行交互
 *          3.调用者(Client)不在直接和具体的对象进行交互，只和工厂交互，调用者(Client)和具体类的耦合解除了，
 *
 * <pre/>
 * @author ronin
 */
public class Client {
    public static void main(String[] args) {
        Dog dog = DogFactory.getDog();

        Person person = PersonFactory.getPerson();
    }
}
