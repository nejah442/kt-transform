package com.example.kata.batch.reader;

import com.example.kata.utils.ExceptionConstants;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.*;

class NumberItemReaderTest {

    private NumberItemReader reader;

    private final ExecutionContext executionContext = new ExecutionContext();

    private NumberItemReader createReaderWithContent(String content) throws Exception {
        Resource resource = new ByteArrayResource(content.getBytes());
        NumberItemReader reader = new NumberItemReader(resource);
        reader.open(executionContext);
        return reader;
    }

    @Test
    void testReadValidNumbers() throws Exception {
        String content = "10\n20\n30\n";
        reader = createReaderWithContent(content);

        assertEquals(10, reader.read());
        assertEquals(20, reader.read());
        assertEquals(30, reader.read());
        assertNull(reader.read());
        reader.close();
    }

    @Test
    void testReadEmptyLineThrows() throws Exception {
        String content = "10\n\n30\n";
        reader = createReaderWithContent(content);

        assertEquals(10, reader.read());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> reader.read());
        assertEquals(ExceptionConstants.NULL_NUMBER, ex.getMessage());
        reader.close();
    }

    @Test
    void testReadOutOfRangeThrows() throws Exception {
        String content = "10\n150\n30\n";
        reader = createReaderWithContent(content);

        assertEquals(10, reader.read());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> reader.read());
        assertEquals(ExceptionConstants.OUT_OF_RANGE, ex.getMessage());
        reader.close();
    }

    @Test
    void testReadNotANumberThrows() throws Exception {
        String content = "10\nabc\n30\n";
        reader = createReaderWithContent(content);

        assertEquals(10, reader.read());
        NumberFormatException ex = assertThrows(NumberFormatException.class, () -> reader.read());
        assertEquals(ExceptionConstants.NOT_A_NUMBER, ex.getMessage());
        reader.close();
    }
}
