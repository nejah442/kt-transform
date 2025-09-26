package com.example.kata.transformer.impl;

import com.example.kata.transformer.NumberTransformer;
import com.example.kata.utils.TransformationConstants;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("defaultTransformer")
@Primary
public class DefaultNumberTransformerImpl implements NumberTransformer {

    /**
     * api implementation for number transformation
     * @param number to transform
     * @return String transformed
     */
    @Override
    public String transformNumber(int number) {
        StringBuilder result = new StringBuilder();
        String numberStr = String.valueOf(number);

        // priority for "divisible by" rule
        if (number % 3 == 0) {
            result.append(TransformationConstants.DIVISIBLE_BY_THREE);
        }
        if (number % 5 == 0) {
            result.append(TransformationConstants.DIVISIBLE_BY_FIVE);
        }

        // rule "contains" from left to right
        for (char c : numberStr.toCharArray()) {
            if (c == '3') {
                result.append(TransformationConstants.CONTAINS_THREE);
            } else if (c == '5') {
                result.append(TransformationConstants.CONTAINS_FIVE);
            } else if (c == '7') {
                result.append(TransformationConstants.CONTAINS_SEVEN);
            }
        }

        // if empty result return input number
        return result.isEmpty() ? numberStr : result.toString();
    }
}
