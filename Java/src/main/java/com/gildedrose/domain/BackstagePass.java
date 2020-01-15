package com.gildedrose.domain;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Backstage passes have some more complex rules for quality degradation:
 * <ul>
 *     <li>The quality of a backstage pass increases instead of decreases.</li>
 *     <li>The closer the 'sell by' date, the faster the quality increases.</li>
 *     <li>After the concert date (= sell by date) the quality drops to 0.</li>
 * </ul>
 */
@ToString
@SuperBuilder
public class BackstagePass extends Item {

    public BackstagePass(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    protected Item.ItemBuilder getItemBuilder() {
        return BackstagePass.builder();
    }

    protected int calculateQuality(int sellIn) {
        int newQuality;
        if (sellIn < 0) {
            newQuality = 0;
        } else if (sellIn < 5) {
            newQuality = quality + 3;
        } else if (sellIn < 10) {
            newQuality = quality + 2;
        } else {
            newQuality = quality + 1;
        }
        return Math.min(50, newQuality);
    }
}