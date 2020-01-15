package com.gildedrose.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Builder
@AllArgsConstructor
public class ItemQualityTestCase {

    @Getter
    private Item item;

    /** expectedQualities[i] contains the expected quality on day i */
    private int[] expectedQualities;

    public int getNumberOfDays() {
        return expectedQualities.length - 1; // Ignore index 0, which represents today.
    }

    public void verifyItemQualityOnDay(Item item, int i) {
        assertEquals(expectedQualities[i], item.getQuality(), String.format("Unexpected quality on day %d", i));
    }
}