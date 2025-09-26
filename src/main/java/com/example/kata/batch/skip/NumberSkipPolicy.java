package com.example.kata.batch.skip;

import com.example.kata.utils.ExceptionConstants;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
public class NumberSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        return t instanceof NumberFormatException && t.getMessage().equals(ExceptionConstants.NOT_A_NUMBER)
                || t instanceof IllegalArgumentException && t.getMessage().equals(ExceptionConstants.NULL_NUMBER)
                || t instanceof IllegalArgumentException && t.getMessage().equals(ExceptionConstants.OUT_OF_RANGE);

    }
}
