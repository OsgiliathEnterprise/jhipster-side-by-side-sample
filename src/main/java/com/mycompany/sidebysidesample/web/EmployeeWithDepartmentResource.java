package com.mycompany.sidebysidesample.web;

import com.mycompany.sidebysidesample.gen.repository.EmployeeRepository;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.service.EmployeeWithDepartmentService;
import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.sidebysidesample.gen.domain.Employee}.
 */
@RestController
@RequestMapping("/api/v1/employeeswithdepartment")
public class EmployeeWithDepartmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeWithDepartmentResource.class);

    private static final String ENTITY_NAME = "genEmployee";

    @Value("${jhipster.clientApp.name:sidebysidesample}")
    private String applicationName;

    private final EmployeeWithDepartmentService employeeWithDepartmentService;

    public EmployeeWithDepartmentResource(EmployeeWithDepartmentService employeeWithDepartmentService) {
        this.employeeWithDepartmentService = employeeWithDepartmentService;
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("")
    public List<EmployeeWithDepartmentDTO> getAllEmployeesWithDepartments(@RequestParam(name = "filter", required = false) String filter) {
        LOG.debug("REST request to get all Employees");
        return employeeWithDepartmentService.findAllEmployeesWithDepartment();
    }
}
