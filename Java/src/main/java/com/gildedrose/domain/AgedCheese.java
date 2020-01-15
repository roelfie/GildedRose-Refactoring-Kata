package com.gildedrose.domain;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Aged cheese increases in quality the older it gets (instead of decrease).
 */
@ToString
@SuperBuilder
public class AgedCheese extends Item {

    public AgedCheese(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    protected Item.ItemBuilder getItemBuilder() {
        return AgedCheese.builder();
    }

    protected int calculateQualityIncrease(int newSellIn) {
        return -1 * super.calculateQualityIncrease(newSellIn);
    }
}