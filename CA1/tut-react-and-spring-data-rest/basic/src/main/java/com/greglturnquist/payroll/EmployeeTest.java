package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEquals() {
        assertThrows(IllegalArgumentException.class, () -> {
            Employee employee = new Employee(null, "lastName", "description",
                    1, "eMail");
        });
    }


}