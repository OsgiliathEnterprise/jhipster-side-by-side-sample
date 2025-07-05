package com.mycompany.sidebysidesample.web;

import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.service.EmployeeServiceExtension;
import com.mycompany.sidebysidesample.service.EmployeeWithDepartmentService;
import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.mycompany.sidebysidesample.gen.domain.Employee}.
 */
@RestController
@RequestMapping("/api/v1/employeesextension")
public class EmployeeExtensionResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeExtensionResource.class);

    private static final String ENTITY_NAME = "genEmployee";

    @Value("${jhipster.clientApp.name:sidebysidesample}")
    private String applicationName;

    private final EmployeeServiceExtension employeeServiceExtension;

    public EmployeeExtensionResource(EmployeeServiceExtension employeeServiceExtension) {
        this.employeeServiceExtension = employeeServiceExtension;
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("")
    public ResponseEntity<EmployeeDTO> secondEmployee(@RequestParam(name = "filter", required = false) String filter) {
        LOG.debug("REST request to get second employee");
        EmployeeDTO employeeDTO = employeeServiceExtension.secondEmployee();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDTO.getId().toString()))
            .body(employeeDTO);
    }
}
