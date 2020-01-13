package com.gildedrose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class QualityTestCase {

    @Getter
    private Item item;

    /** expectedQualities[i] contains the expected quality on day i */
    private int[] expectedQualities;

    public int getNumberOfDays() {
        return expectedQualities.length - 1; // Ignore index 0, which represents today.
    }

    public int getExpectedQualityOnDay(int i) {
        return expectedQualities[i];
    }
}
