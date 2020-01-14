package com.gildedrose;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    GildedRose(Item item) {
        this(new Item[]{item});
    }

    public void updateQuality() {
        this.items = Arrays
                .stream(items)
                .map(item -> item.nextDay())
                .collect(toList())
                .toArray(new Item[] {});
    }
}