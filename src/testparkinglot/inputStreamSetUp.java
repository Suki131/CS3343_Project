package testparkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public abstract class inputStreamSetUp {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpIO() {
        outContent.reset();
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream(new byte[0]));
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