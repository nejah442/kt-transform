package com.example.kata.batch.listener;

import com.example.kata.utils.ExceptionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TransformationListener implements SkipListener<Integer, String>, ItemWriteListener<String>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationListener.class);
    private int invalidNumbers = 0;
    private int outOfRangeNumbers = 0;
    private int nullNumbers = 0;
    private int writeCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        // count all skip types
        if (t.getMessage() != null && t.getMessage().equals(ExceptionConstants.NULL_NUMBER)) {
            nullNumbers++;
        } else if (t.getMessage() != null && t.getMessage().equals(ExceptionConstants.NOT_A_NUMBER)) {
            invalidNumbers++;
        } else if (t.getMessage() != null && t.getMessage().equals(ExceptionConstants.OUT_OF_RANGE)) {
            outOfRangeNumbers++;
        }
    }

    @Override
    public void onSkipInWrite(String item, Throwable t) {
        if (t instanceof IOException && t.getMessage() != null && t.getMessage().equals(ExceptionConstants.WRITER_IO_EXCEPTION)) {
            LOGGER.error("Un problème est survenu lors de l'ecriture du fichier Output");
        }
    }

    @Override
    public void onSkipInProcess(Integer item, Throwable t) {
        SkipListener.super.onSkipInProcess(item, t);
    }


    @Override
    public void afterWrite(Chunk<? extends String> items) {
        writeCount += items.size();
    }


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("- Nombres NULL : {}", nullNumbers);
        LOGGER.info("- Nombres non valides : {}", invalidNumbers);
        LOGGER.info("- Nombres hors plage : {}", outOfRangeNumbers);
        LOGGER.info("- Nombres traités : {}", writeCount);
        return stepExecution.getExitStatus();
    }

}
