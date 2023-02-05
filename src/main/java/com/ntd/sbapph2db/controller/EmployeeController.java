package com.ntd.sbapph2db.controller;

import com.ntd.sbapph2db.dto.EmployeeDto;
import com.ntd.sbapph2db.exception.ResourceNotFoundException;
import com.ntd.sbapph2db.model.Employee;
import com.ntd.sbapph2db.payload.EmployeePayload;
import com.ntd.sbapph2db.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        return ResponseEntity.ok(employeeRepository.findAll().stream().map(EmployeeDto::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        final Employee emp = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee not found!"));
        return ResponseEntity.ok(new EmployeeDto(emp));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Validated @RequestBody EmployeePayload payload) {
        Employee employee = employeeRepository.save(new Employee(payload));
        return ResponseEntity.ok(new EmployeeDto(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                      @Validated @RequestBody EmployeePayload payload) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee not found!"));
        employee.setEmail(payload.email());
        employee.setLastName(payload.lastName());
        employee.setFirstName(payload.firstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(new EmployeeDto(updatedEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeRepository.deleteById(employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("Employee not found!")).getId());
        return ResponseEntity.ok().build();
    }
}
