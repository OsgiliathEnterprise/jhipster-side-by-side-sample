package com.mycompany.sidebysidesample.service.impl;

import com.mycompany.sidebysidesample.gen.service.DepartmentService;
import com.mycompany.sidebysidesample.gen.service.EmployeeService;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.service.EmployeeWithDepartmentService;
import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeWithDepartmentServiceImpl implements EmployeeWithDepartmentService {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    EmployeeWithDepartmentServiceImpl(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @Override
    public List<EmployeeWithDepartmentDTO> findAllEmployeesWithDepartment() {
        return employeeService
            .findAll()
            .stream()
            .map((EmployeeDTO employee) -> {
                if (employee.getDepartment() == null) {
                    return new EmployeeWithDepartmentDTO(employee.getEmail(), "Unknown");
                }
                Optional<String> departmentName = departmentService
                    .findOne(employee.getDepartment().getId())
                    .map(department -> department.getDepartmentName());
                return new EmployeeWithDepartmentDTO(employee.getEmail(), departmentName.orElse("Unknown"));
            })
            .toList();
    }
}
