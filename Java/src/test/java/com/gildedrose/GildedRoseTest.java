package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Gilded Rose functionality is all about Items. Items have some constant characteristics (name, isConjured, ..)
 * and characteristics that vary over time (quality, sellIn).
 * <p/>
 * Since the calculation of 'quality' and the calculation of 'sellIn' are completely different (and unrelated), we test
 * them independently.
 * <p/>
 * We use a data-driven approach (because of conciseness and maintainability) to test these calculations: Specify input
 * values (name, sellIn, quality, isConjured) and the expected output value per day (for instance, quality) in a table.
 * One test scenario per row. The test framework will feed a test method with these data, one scenario (row) at a time.
 *
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests">Parameterized tests with JUnit</a>
 */
class GildedRoseTest {

    @ParameterizedTest(name = "[{0}] {2}(sellIn = {3}, quality = {4})")
    @CsvFileSource(resources = "/GildedRose.csv", numLinesToSkip = 1)
    void testUpdateQuality(@AggregateWith(QualityTestCaseAggregator.class) QualityTestCase testCase) {
        GildedRose rose = new GildedRose(testCase.getItem());
        for (int i = 1; i <= testCase.getNumberOfDays(); i++) {
            rose.updateQuality();
            testCase.verifyQualityOnDay(i);
        }
    }

    @ParameterizedTest(name = "[{index}] {0}(sellIn = {1})")
    @CsvSource({
            /* Sulfuras (a legendary item) never has to be sold. Its 'sellIn' value is never updated. */
            "'Sulfuras, Hand of Ragnaros',                1, 1",
            "'Sulfuras, Hand of Ragnaros',                0, 0",
            "'Sulfuras, Hand of Ragnaros',                -1, -1",

            /* Backstage passes do have a sell by date */
            "'Backstage passes to a TAFKAL80ETC concert', 1, 0",
            "'Backstage passes to a TAFKAL80ETC concert', 0, -1",
            "'Backstage passes to a TAFKAL80ETC concert', -1, -2",

            /* Brie does have a sell by date */
            "'Aged Brie',                                 1, 0",
            "'Aged Brie',                                 0, -1",
            "'Aged Brie',                                 -1, -2",

            /* All other items have a sell by date, and their 'sellIn' value decreases by 1 each day */
            "'Normal item',                               1, 0",
            "'Normal item',                               0, -1",
            "'Normal item',                               -1, -2"
    })
    void testUpdateSellIn(String name, int sellIn, int expectedSellIn) {
        Item item = new Item(name, sellIn, 0);
        GildedRose rose = new GildedRose(item);
        rose.updateQuality();
        assertEquals(expectedSellIn, item.sellIn);
    }

    @ParameterizedTest(name = "[{index}] {0}(quality = {1}, sellIn = {2})")
    @CsvSource({
            /* No matter the value of 'quality', the 'sellIn' value is always updated the same way */
            "'Normal item', 80,  1, 0",
            "'Normal item', 50,  1, 0",
            "'Normal item', 25,  1, 0",
            "'Normal item', 1,   1, 0",
            "'Normal item', 0,   1, 0",
            "'Normal item', -1,  1, 0",
            "'Normal item', -10, 1, 0"
    })
    void testUpdateSellIn_independentOfQuality(String name, int quality, int sellIn, int expectedSellIn) {
        Item item = new Item(name, sellIn, quality);
        GildedRose rose = new GildedRose(item);
        rose.updateQuality();
        assertEquals(expectedSellIn, item.sellIn);
    }

}