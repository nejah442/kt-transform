package com.example.kata.transformer;

import com.example.kata.transformer.impl.AlternateNumberTransformerImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AlternateNumberTransformerImplTest {
    private final NumberTransformer transformer = new AlternateNumberTransformerImpl();


    @Test
    void shouldReturnNumberIfNoRulesMatch() {
        assertThat(transformer.transformNumber(1)).isEqualTo("1\t\"1\"");
    }

    @Test
    void shouldApplyContainsRules() {
        assertThat(transformer.transformNumber(3)).isEqualTo("3\t\"FOOFOO\"");
    }

    @Test
    void shouldApplyDivisibleByThreeRules() {
        assertThat(transformer.transformNumber(9)).isEqualTo("9\t\"FOO\"");
    }

    @Test
    void shouldApplyDivisibleByFiveRules() {
        assertThat(transformer.transformNumber(20)).isEqualTo("20\t\"BAR\"");
    }

    @Test
    void shouldTransformNumber() {
        // verify samples
        assertThat(transformer.transformNumber(53)).isEqualTo("53\t\"BARFOO\"");
        assertThat(transformer.transformNumber(51)).isEqualTo("51\t\"FOOBAR\"");
        assertThat(transformer.transformNumber(33)).isEqualTo("33\t\"FOOFOOFOO\"");
    }
}
