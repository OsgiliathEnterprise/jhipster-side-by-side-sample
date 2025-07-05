package com.mycompany.sidebysidesample.service;

import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import java.util.List;

public interface EmployeeWithDepartmentService {
    /**
     * Get all employees with their department names.
     *
     * @return a list of EmployeeWithDepartmentDTO containing employee email and department name.
     */
    List<EmployeeWithDepartmentDTO> findAllEmployeesWithDepartment();
}
