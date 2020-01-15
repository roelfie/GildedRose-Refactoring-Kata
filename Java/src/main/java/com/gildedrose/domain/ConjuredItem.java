package com.gildedrose.domain;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * A conjured item is an item whose quality degrades twice as fast as a normal item.
 */
@ToString
@SuperBuilder
public class ConjuredItem extends Item {

    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    protected Item.ItemBuilder getItemBuilder() {
        return ConjuredItem.builder();
    }

    @Override
    protected int calculateQualityIncrease(int sellIn) {
        return 2 * super.calculateQualityIncrease(sellIn);
    }
}