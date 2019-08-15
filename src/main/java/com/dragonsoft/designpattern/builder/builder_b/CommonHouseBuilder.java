package com.dragonsoft.designpattern.builder.builder_b;

/**
 * 具体的建造者
 */
public class CommonHouseBuilder extends AbstractHouseBuilder {

    @Override
    public void buildBasic() {
        super.house.setBasic("普通房子地基");
    }

    @Override
    public void buildWalls() {
        super.house.setWalls("普通房子墙面");
    }

    @Override
    public void buildRoof() {
        super.house.setRoof("普通房子屋顶");
    }
}
