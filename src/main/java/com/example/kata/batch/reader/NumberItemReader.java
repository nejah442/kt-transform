package com.example.kata.batch.reader;

import com.example.kata.utils.ExceptionConstants;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Profile("batch")
public class NumberItemReader implements ItemStreamReader<Integer> {


    private final Resource inputResource;

    private BufferedReader reader;

    public NumberItemReader(@Value("${batch.input-file}") Resource inputResource) throws IOException {
        this.inputResource = inputResource;
    }

    @Override
    public void open(ExecutionContext executionContext) {
        try {
            this.reader = new BufferedReader(new InputStreamReader(inputResource.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException("Could not open file for reading", e);
        }
    }


    @Override
    public Integer read() throws Exception {
        String line = reader.readLine();

        if (line == null) {
            return null;
        }

        if (line.isEmpty()) {
            throw new IllegalArgumentException(ExceptionConstants.NULL_NUMBER);
        }
        try {
            int number = Integer.parseInt(line);
            if (number > 100 || number < 0) {
                throw new IllegalArgumentException(ExceptionConstants.OUT_OF_RANGE);
            }
            return number;
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ExceptionConstants.NOT_A_NUMBER);
        }

    }

    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close reader", e);
        }
    }

}
