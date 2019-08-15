package com.dragonsoft.designpattern.builder.builder_b;

/**
 * 抽象的建造者
 */
public abstract class AbstractHouseBuilder {
    protected House house = new House();
    /**地基*/
    public abstract void buildBasic();
    /**墙面*/
    public abstract void buildWalls();
    /**屋顶*/
    public abstract void buildRoof();

    /**
     * 建造好房子后，返回房子
     * @return
     */
    public House buildHouse(){
        return house;
    }
}
