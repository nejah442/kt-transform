package com.example.kata.transformer;

import com.example.kata.transformer.impl.DefaultNumberTransformerImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class DefaultNumberTransformerImplTest {
    private final NumberTransformer transformer = new DefaultNumberTransformerImpl();


    @Test
    void shouldReturnNumberIfNoRulesMatch() {
        assertThat(transformer.transformNumber(1)).isEqualTo("1");
    }

    @Test
    void shouldApplyContainsRules() {
        assertThat(transformer.transformNumber(3)).isEqualTo("FOOFOO");
    }

    @Test
    void shouldApplyDivisibleByThreeRules() {
        assertThat(transformer.transformNumber(9)).isEqualTo("FOO");
    }

    @Test
    void shouldApplyDivisibleByFiveRules() {
        assertThat(transformer.transformNumber(20)).isEqualTo("BAR");
    }

    @Test
    void shouldTransformNumber() {
        // verify samples
        assertThat(transformer.transformNumber(53)).isEqualTo("BARFOO");
        assertThat(transformer.transformNumber(51)).isEqualTo("FOOBAR");
        assertThat(transformer.transformNumber(33)).isEqualTo("FOOFOOFOO");
    }
}
