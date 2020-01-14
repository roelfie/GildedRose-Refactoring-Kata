package com.gildedrose;

import lombok.Data;

@Data
public class Item {

    private final String name;
    private final int sellIn;
    private final int quality;

    public Item nextDay() {
        int newSellIn = recalculateSellIn();
        return new Item(name, newSellIn, recalculateQuality(newSellIn));
    }

    private int recalculateSellIn() {
        switch(name) {
            case "Sulfuras, Hand of Ragnaros":
                return sellIn; /* Doesn't change (legendary item) */
            default:
                return sellIn - 1;
        }
    }

    private int recalculateQuality(int newSellIn) {
        switch(name) {
            case "Sulfuras, Hand of Ragnaros":
                return quality; /* Doesn't change (legendary item) */
            case "Aged Brie":
                return Math.min(50, quality + (newSellIn>=0 ? 1 : 2));
            case "Backstage passes to a TAFKAL80ETC concert":
                if (newSellIn < 0) {
                    return 0;
                } else if (newSellIn < 5) {
                    return Math.min(50, quality + 3);
                } else if (newSellIn < 10) {
                    return Math.min(50, quality + 2);
                } else {
                    return Math.min(50, quality + 1);
                }
            case "Conjured Mana Cake":
                return Math.max(0, quality - (newSellIn>=0 ? 2 : 4));
            default:
                return Math.max(0, quality - (newSellIn>=0 ? 1 : 2));
        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
