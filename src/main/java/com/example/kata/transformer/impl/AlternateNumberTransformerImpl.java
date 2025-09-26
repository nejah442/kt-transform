package com.example.kata.transformer.impl;

import com.example.kata.transformer.NumberTransformer;
import com.example.kata.utils.TransformationConstants;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Component("alternateTransformer")
public class AlternateNumberTransformerImpl implements NumberTransformer {

    private static final SortedMap<Integer, String> DIVISIBLE_BY_RULES_MAP =
            Collections.unmodifiableSortedMap(new TreeMap<>(Map.of(
                    3, TransformationConstants.DIVISIBLE_BY_THREE,
                    5, TransformationConstants.DIVISIBLE_BY_FIVE
            )));

    private static final Map<Character, String> CONTAINS_RULES_MAP = Map.of(
            '3', TransformationConstants.CONTAINS_THREE,
            '5', TransformationConstants.CONTAINS_FIVE,
            '7', TransformationConstants.CONTAINS_SEVEN
    );

    /**
     * alternate implementation for output file
     * @param number number to transform
     * @return String transformed number
     */
    @Override
    public String transformNumber(int number) {
        StringBuilder result = new StringBuilder();
        String numberStr = String.valueOf(number);
        String lineResult = "";

        // apply "divisible by" rules
        for (Map.Entry<Integer, String> entry : DIVISIBLE_BY_RULES_MAP.entrySet()) {
            int key = entry.getKey();
            String value = entry.getValue();

            if (number % key == 0) {
                result.append(value);
            }
        }

        // apply "contains" rules - from left to right
        for (char c : numberStr.toCharArray()) {
            if (CONTAINS_RULES_MAP.containsKey(c)) {
                result.append(CONTAINS_RULES_MAP.get(c));
            }
        }

        // if empty lineResult return input number
        return result.isEmpty() ? formatLine(numberStr, numberStr)
                : formatLine(numberStr, result.toString());
    }

    /**
     * format line for output file
     * @param numberStr number
     * @param result    transformed number
     * @return String
     */
    private String formatLine(String numberStr, String result)
    {
        return numberStr + "\t\"" + result + "\"";
    }
}
