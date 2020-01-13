package com.gildedrose;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.util.Objects;
import java.util.stream.IntStream;

public class QualityTestCaseAggregator implements ArgumentsAggregator {

    @Override
    public QualityTestCase aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) throws ArgumentsAggregationException {
        return QualityTestCase.builder()
                .item(createItem(arguments))
                .expectedQualities(createExpectedQualities(arguments))
                .build();
    }

    private int[] createExpectedQualities(ArgumentsAccessor arguments) {
        return IntStream.concat(
                IntStream.of(getQuality(arguments)), // for convenience, put today's quality value at index 0
                arguments.toList()
                        .subList(5, arguments.size()) // ignore the first 5 columns (input data)
                        .stream()
                        .filter(Objects::nonNull)
                        .mapToInt(o -> Integer.parseInt((String) o))
        ).toArray();
    }

    private Item createItem(ArgumentsAccessor arguments) {
        return new Item(
                arguments.getString(2),
                arguments.getInteger(3),
                getQuality(arguments));
    }

    private Integer getQuality(ArgumentsAccessor arguments) {
        return arguments.getInteger(4);
    }
}
