package com.ntd.sbapph2db.dto;

import com.ntd.sbapph2db.model.Employee;

public record EmployeeDto(
        long id,
        String firstName,
        String lastName,
        String email
) {
    public EmployeeDto(Employee emp) {
        this(emp.getId(), emp.getFirstName(),emp.getLastName(),emp.getEmail());
    }
}
