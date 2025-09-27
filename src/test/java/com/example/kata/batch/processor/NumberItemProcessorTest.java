package com.example.kata.batch.processor;

import com.example.kata.transformer.NumberTransformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NumberItemProcessorTest {

    private NumberTransformer numberTransformer;
    private NumberItemProcessor processor;

    @BeforeEach
    void setUp() {
        numberTransformer = mock(NumberTransformer.class);
        processor = new NumberItemProcessor();
        processor.setNumberTransformer(numberTransformer);
    }

    @Test
    void testProcess_callsTransformerAndCachesResult() throws Exception {
        int input = 42;
        String transformed = "FOO";

        when(numberTransformer.transformNumber(input)).thenReturn(transformed);

        // first call -> must call transformer
        String result1 = processor.process(input);
        assertEquals(transformed, result1);

        // second call -> must check cache
        String result2 = processor.process(input);
        assertEquals(transformed, result2);

        verify(numberTransformer, times(1)).transformNumber(input);
    }

    @Test
    void testProcess_differentInputs() throws Exception {
        int input1 = 1;
        int input2 = 2;

        when(numberTransformer.transformNumber(input1)).thenReturn("1");
        when(numberTransformer.transformNumber(input2)).thenReturn("2");

        String result1 = processor.process(input1);
        String result2 = processor.process(input2);

        assertEquals("1", result1);
        assertEquals("2", result2);

        verify(numberTransformer, times(1)).transformNumber(input1);
        verify(numberTransformer, times(1)).transformNumber(input2);
    }
}
