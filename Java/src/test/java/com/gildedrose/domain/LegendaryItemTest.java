package com.gildedrose.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class LegendaryItemTest {

    @ParameterizedTest
    @CsvSource({
            "100, 100",
            "25, 25",
            "1, 1",
            "0, 0",
            "-1, -1",
            "-100, -100"
    })
    void testCalculateSellIn(int sellIn, int expectedSellIn) {
        Item item = new LegendaryItem("Legendary item name", sellIn, 0);
        assertThat(item.calculateSellIn(), is(expectedSellIn));
    }
}