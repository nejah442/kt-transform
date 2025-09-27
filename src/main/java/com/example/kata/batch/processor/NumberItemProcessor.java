package com.example.kata.batch.processor;

import com.example.kata.transformer.NumberTransformer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NumberItemProcessor implements ItemProcessor<Integer, String> {

    @Autowired
    @Qualifier("alternateTransformer")
    private NumberTransformer numberTransformer;

    // cache for already transformed numbers - minimize process call
    private final Map<Integer, String> cacheProcessed = new ConcurrentHashMap<>();

    @Override
    public String process(Integer item) throws Exception {
        return cacheProcessed.computeIfAbsent(item, numberTransformer::transformNumber);
    }

    public void setNumberTransformer(NumberTransformer numberTransformer) {
        this.numberTransformer = numberTransformer;
    }
}
