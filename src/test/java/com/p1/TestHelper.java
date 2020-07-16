package com.p1;

import com.cqrs.aggregates.AggregateExecutionException;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.*;

public class TestHelper {
    public static <T extends Throwable> void assertAggregateThrowsException(Class<T> expectedType, Executable executable) {
        try {
            executable.execute();
            fail("Aggregate should throw " + expectedType.getCanonicalName() + " but didn't");
        } catch (AggregateExecutionException e) {
            Throwable cause = e.getCause();
            if(cause instanceof InvocationTargetException){
                cause = cause.getCause();
            }
            if(!cause.getClass().getCanonicalName().equals(expectedType.getCanonicalName())){
                fail("Aggregate throws " + cause.getClass().getCanonicalName() + " instead of " + expectedType.getCanonicalName());
            }
        } catch (Throwable throwable) {
            fail("Unexpected exception " + throwable.getClass().getCanonicalName() + " thrown instead of " + expectedType.getCanonicalName());
        }
    }
}
