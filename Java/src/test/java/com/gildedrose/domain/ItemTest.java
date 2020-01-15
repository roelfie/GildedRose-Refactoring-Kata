package com.gildedrose.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
class ItemTest {

    @ParameterizedTest
    @CsvSource({
            "100, 99",
            "25, 24",
            "1, 0",
            "0, -1",
            "-1, -2",
            "-100, -101"
    })
    void testCalculateSellIn(int sellIn, int expectedSellIn) {
        Item item = new Item("Item name", sellIn, 0);
        assertThat(item.calculateSellIn(), is(expectedSellIn));
    }

    @ParameterizedTest
    @CsvSource({
            /* No matter the value of 'quality', the 'sellIn' value is always updated the same way */
            "80,  1, 0",
            "50,  1, 0",
            "25,  1, 0",
            "1,   1, 0",
            "0,   1, 0",
            "-1,  1, 0",
            "-10, 1, 0"
    })
    void testCalculateSellIn_independentOfQuality(int quality, int sellIn, int expectedSellIn) {
        Item item = new Item("Item name", sellIn, quality);
        assertThat(item.calculateSellIn(), is(expectedSellIn));
    }

    @ParameterizedTest(name = "[{0}] {2}(sellIn = {3}, quality = {4})")
    @CsvFileSource(resources = "/ItemTest.csv", numLinesToSkip = 1)
    void testNextDay(@AggregateWith(ItemQualityTestCaseAggregator.class) ItemQualityTestCase testCase) {
        Item item = testCase.getItem();
        Item itemNextDay;

        for (int i = 1; i <= testCase.getNumberOfDays(); i++) {
            itemNextDay = item.nextDay();
            testCase.verifyItemQualityOnDay(itemNextDay, i);
            item = itemNextDay;
        }
    }
}