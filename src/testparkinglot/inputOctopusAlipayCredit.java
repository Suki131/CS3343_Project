package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public abstract class inputOctopusAlipayCredit {

    protected ByteArrayInputStream testInputStream;

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUpIO() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));
        testInputStream = null;
    }

    @AfterEach
    void tearDownIO() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    protected String getOutput() {
        return outContent.toString();
    }

    protected void resetOutput() {
        outContent.reset();
    }
}