package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The Gilded Rose functionality is all about Items. Items have some constant characteristics (name, isConjured, ..)
 * and characteristics that vary over time (quality, sellIn).
 * <p/>
 * The obvious way to test this, is a 'data driven' approach: A table specifying input values (name, isConjured, ..)
 * and expected output values (quality, sellIn) per day.
 * <p/>
 * In Junit you can use @ParameterizedTest to create a data-driven test:
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
 * You can use a CSV file as the data source for such a test.
 * <p/>
 * One difficulty is that we have a time-dimension: The expected output is not a simple {expectedQuality, expectedSellIn}
 * pair, but a time series of pairs (one for each day that passes by). This is somewhat hard to visualize in a
 * CSV file (each cell represents a point in time, so would contain a pair (expectedQuality, expectedSellIn)).
 * <p/>
 * Also, updating the 'quality' is much more complex than updating 'sellIn'. Therefore we've chosen to test updating
 * 'quality' and updating 'sellIn' in separate test, with separate backing data sets.
 * <p/>
 * The advantage of using a CSV file as data source, is that CSV files are very convenient to edit (with any CSV editor).
 * The drawback of using CSV as the source of such a data-driven test is that it is hard to describe your test case
 * (what exactly are you testing (e.g. edge cases) and why?). If you need more expressive tests, consider tools like
 * Serenity, JBehave, cucumber. But for now (an Item class with just a few attributes) this CSV approach will do.
 */
class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @ParameterizedTest(name = "[{0}] {2}(sellIn = {3}, quality = {4})")
    @CsvFileSource(resources = "/GildedRose-quality-test.csv", numLinesToSkip = 1)
    void testUpdateQuality(@AggregateWith(QualityTestCaseAggregator.class) QualityTestCase testCase) {
        Item item = testCase.getItem();
        GildedRose rose = new GildedRose(new Item[]{item});
        for (int i = 1; i <= testCase.getNumberOfDays(); i++) {
            rose.updateQuality();
            assertEquals(testCase.getExpectedQualityOnDay(i), item.quality);
        }
    }
}
