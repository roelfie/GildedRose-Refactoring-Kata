package com.gildedrose.domain;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Legendary items have no 'sell by' date, and as such, their quality does not degrade.
 */
@ToString
@SuperBuilder
public class LegendaryItem extends Item {

    public LegendaryItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    protected Item.ItemBuilder getItemBuilder() {
        return LegendaryItem.builder();
    }

    @Override
    protected int calculateSellIn() {
        return sellIn;
    }

    @Override
    protected int calculateQuality(int sellIn) {
        return quality;
    }
}