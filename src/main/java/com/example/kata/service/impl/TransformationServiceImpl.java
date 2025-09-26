package com.example.kata.service.impl;

import com.example.kata.service.TransformationService;
import com.example.kata.transformer.NumberTransformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransformationServiceImpl implements TransformationService {

    private final NumberTransformer transformer;

    public TransformationServiceImpl(NumberTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public String transform(int number) {
        return transformer.transformNumber(number);
    }
}
