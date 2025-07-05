package com.mycompany.sidebysidesample.service;

import com.mycompany.sidebysidesample.gen.service.EmployeeService;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import java.util.List;

public interface EmployeeServiceExtension extends EmployeeService {
    EmployeeDTO secondEmployee();
}
