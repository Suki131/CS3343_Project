package testparkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import parkinglot.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testStaff {

    private Staff staff;

    @BeforeEach
    void setUp() {
        staff = new Staff("111", "Jane Doe");
    }

    @Test
    void testGetStaffName() {
        assertEquals(staff.getName(), "Jane Doe");
    }

    @Test
    void testSetStaffName() {
        staff.setName("Chris Wong");
        assertEquals(staff.getName(), "Chris Wong");
    }

}
