package com.gildedrose.domain;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.util.Objects;
import java.util.stream.IntStream;

public class ItemQualityTestCaseAggregator implements ArgumentsAggregator {

    @Override
    public ItemQualityTestCase aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {
        return ItemQualityTestCase.builder()
                .item(createItem(arguments))
                .expectedQualities(createExpectedQualities(arguments))
                .build();
    }

    private int[] createExpectedQualities(ArgumentsAccessor arguments) {
        return IntStream.concat(
                IntStream.of(getQuality(arguments)), // for convenience, put today's quality value at index 0
                arguments.toList()
                        .subList(6, arguments.size()) // ignore the first 6 columns (metadata / input data)
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToInt(o -> Integer.parseInt((String) o))
        ).toArray();
    }

    private <T extends Item> T createItem(ArgumentsAccessor arguments) {
        String itemType = arguments.getString(2);
        Item.ItemBuilder itemBuilder = getItemBuilder(itemType);
        return (T) itemBuilder
                .name(arguments.getString(3))
                .sellIn(arguments.getInteger(4))
                .quality(getQuality(arguments))
                .build();
    }

    private Item.ItemBuilder getItemBuilder(String itemType) {
        switch (itemType) {
            case "LegendaryItem":
                return LegendaryItem.builder();
            case "BackstagePass":
                return BackstagePass.builder();
            case "AgedCheese":
                return AgedCheese.builder();
            case "ConjuredItem":
                return ConjuredItem.builder();
            default:
                return Item.builder();
        }
    }

    private Integer getQuality(ArgumentsAccessor arguments) {
        return arguments.getInteger(5);
    }
}