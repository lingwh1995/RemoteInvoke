package com.dragonsoft.designpattern.builder.builder_b;

/**
 * 指挥者
 */
public class HouseDirector {
    private AbstractHouseBuilder houseBuilder;

    /**通过构造器传入HouseBuilder*/
    public HouseDirector(AbstractHouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    /**用于重置建造者*/
    public void setHouseBuilder(AbstractHouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    /**
     * 如何建造房子交给指挥者
     */
    public House buildeHouse(){
        houseBuilder.buildBasic();
        houseBuilder.buildWalls();
        houseBuilder.buildRoof();
        return houseBuilder.buildHouse();
    }
}
