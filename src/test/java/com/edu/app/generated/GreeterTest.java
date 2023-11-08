package com.edu.app.generated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreeterTest {

    @Test
    void test() {
        Double newValue = 99.0d;
        Greeter greeter = new Greeter();
        greeter.setPowerGenerated( newValue);

        Assertions.assertEquals(newValue, greeter.getPowerGenerated());

    }
}
