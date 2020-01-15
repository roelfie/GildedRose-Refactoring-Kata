package com.gildedrose.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Item {

    @Getter protected final String name;
    @Getter protected final int sellIn;
    @Getter protected final int quality;

    public <T extends Item> T nextDay() {
        int newSellIn = calculateSellIn();
        int newQuality = calculateQuality(newSellIn);
        return (T) getItemBuilder()
                .name(name)
                .sellIn(newSellIn)
                .quality(newQuality)
                .build();
    }

    protected Item.ItemBuilder getItemBuilder() {
        return Item.builder();
    }

    protected int calculateSellIn() {
        return sellIn - 1;
    }

    protected int calculateQuality(int sellIn) {
        int delta = calculateQualityIncrease(sellIn);
        return Math.max(0, Math.min(50, quality + delta));
    }

    protected int calculateQualityIncrease(int sellIn) {
        return sellIn>=0 ? -1 : -2;
    }
}