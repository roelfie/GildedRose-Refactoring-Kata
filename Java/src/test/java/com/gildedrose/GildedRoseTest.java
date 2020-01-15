package com.gildedrose;

import com.gildedrose.domain.Item;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class GildedRoseTest {

    @Test
    void testUpdateQuality() {
        Item item = Item.builder().name("Item name").sellIn(10).quality(25).build();
        GildedRose gildedRose = new GildedRose(new Item[]{item});

        assertThat(gildedRose.items.length, is(1));

        gildedRose.updateQuality();

        assertThat(gildedRose.items.length, is(1));
        assertThat(gildedRose.items[0].getSellIn(), is(9));
        assertThat(gildedRose.items[0].getQuality(), is(24));
    }
}