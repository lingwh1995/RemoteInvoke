package com.dragonsoft.designpattern.builder.builder_b;

/**
 * 具体的建造者，只负责产品的创建
 */
public class HighHouseBuilder extends AbstractHouseBuilder {

    @Override
    public void buildBasic() {
        super.house.setBasic("高楼大厦地基");
    }

    @Override
    public void buildWalls() {
        super.house.setWalls("高楼大厦墙面");
    }

    @Override
    public void buildRoof() {
        super.house.setRoof("高楼大厦屋顶");
    }
}
