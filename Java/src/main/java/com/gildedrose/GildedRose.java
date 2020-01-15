package com.gildedrose;

import com.gildedrose.domain.Item;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        this.items = Arrays
                .stream(items)
                .map(item -> item.nextDay())
                .collect(toList())
                .toArray(new Item[] {});
    }
}