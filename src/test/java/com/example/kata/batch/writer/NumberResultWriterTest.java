package com.example.kata.batch.writer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.item.Chunk;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberResultWriterTest {

    private File tempFile;
    private NumberResultWriter writer;

    @BeforeEach
    void setup() throws IOException {
        // temporary file output
        tempFile = File.createTempFile("test-output", ".txt");
        tempFile.deleteOnExit();

        // Mock Resource
        Resource resource = Mockito.mock(Resource.class);
        Mockito.when(resource.getFile()).thenReturn(tempFile);

        writer = new NumberResultWriter(resource);
    }

    @Test
    void testWriteStringsToFile() throws Exception {
        writer.write(Chunk.of("1", "2"));

        List<String> lines = Files.readAllLines(tempFile.toPath());

        assertEquals(2, lines.size());
        assertEquals("1", lines.get(0));
        assertEquals("2", lines.get(1));

        // write new line
        writer.write(Chunk.of("FOO"));

        lines = Files.readAllLines(tempFile.toPath());
        assertEquals(3, lines.size());
        assertEquals("FOO", lines.get(2));
    }

}
