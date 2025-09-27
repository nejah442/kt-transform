package com.example.kata.batch.reader;

import com.example.kata.utils.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberItemReader.class);

    private final Resource inputResource;

    private BufferedReader reader;

    public NumberItemReader(@Value("${batch.input-file}") Resource inputResource) throws IOException {
        this.inputResource = inputResource;
    }

    @Override
    public void open(ExecutionContext executionContext) {

        // check if file exists
        if (!inputResource.exists()) {
            throw new IllegalStateException("Le fichier d'entrée est introuvable : " + inputResource.getFilename());
        }

        // check if file readable
        if (!inputResource.isReadable()) {
            throw new IllegalStateException("Le fichier d'entrée n'est pas lisible : " + inputResource.getFilename());
        }

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
