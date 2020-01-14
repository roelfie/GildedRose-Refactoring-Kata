package com.gildedrose;

import java.util.Arrays;
import java.util.function.Consumer;

class GildedRose {

    // ========================================================================
    //  Update logic for Item.sellIn
    // ========================================================================
    private static final Consumer<Item> UPDATE_SELL_IN_DEFAULT = item -> item.sellIn = item.sellIn - 1;
    private static final Consumer<Item> UPDATE_SELL_IN_SULFURAS = item -> {/*Do nothing (legendary item)*/};

    // ========================================================================
    //  Update logic for Item.quality
    // ========================================================================
    private static final Consumer<Item> UPDATE_QUALITY_DEFAULT = item -> {
        item.quality = Math.max(0, item.quality - (item.sellIn>=0 ? 1 : 2));
    };
    private static final Consumer<Item> UPDATE_QUALITY_CONJURED = item -> {
        item.quality = Math.max(0, item.quality - (item.sellIn>=0 ? 2 : 4));
    };
    private static final Consumer<Item> UPDATE_QUALITY_SULFURAS = item -> {/*Do nothing (legendary item)*/};
    private static final Consumer<Item> UPDATE_QUALITY_AGED_BRIE = item -> {
        item.quality = Math.min(50, item.quality + (item.sellIn>=0 ? 1 : 2));
    };
    private static final Consumer<Item> UPDATE_QUALITY_BACKSTAGE = item -> {
        if (item.sellIn < 0) {
            item.quality = 0;
        } else if (item.sellIn < 5) {
            item.quality = item.quality + 3;
        } else if (item.sellIn < 10) {
            item.quality = item.quality + 2;
        } else {
            item.quality = item.quality + 1;
        }
        item.quality = Math.min(50, item.quality);
    };
    // ========================================================================

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    GildedRose(Item item) {
        this(new Item[]{item});
    }

    // TODO Rename to 'updateItemForNextDay' (don't forget to update the job that calls this method).
    public void updateQuality() {
        Arrays.stream(items)
                .map(this::updateSellIn)
                .forEach(this::updateQuality);
    }

    private Item updateSellIn(Item item) {
        switch(item.name) {
            case "Sulfuras, Hand of Ragnaros":
                UPDATE_SELL_IN_SULFURAS.accept(item);
                break;
            default:
                UPDATE_SELL_IN_DEFAULT.accept(item);
        }
        return item;
    }

    private void updateQuality(Item item) {
        switch(item.name) {
            case "Sulfuras, Hand of Ragnaros":
                UPDATE_QUALITY_SULFURAS.accept(item);
                break;
            case "Aged Brie":
                UPDATE_QUALITY_AGED_BRIE.accept(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                UPDATE_QUALITY_BACKSTAGE.accept(item);
                break;
            case "Conjured Mana Cake":
                UPDATE_QUALITY_CONJURED.accept(item);
                break;
            default:
                UPDATE_QUALITY_DEFAULT.accept(item);
        }
    }
}