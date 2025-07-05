package com.mycompany.sidebysidesample.service.impl;

import com.mycompany.sidebysidesample.gen.repository.EmployeeRepository;
import com.mycompany.sidebysidesample.gen.service.DepartmentService;
import com.mycompany.sidebysidesample.gen.service.EmployeeService;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.gen.service.impl.EmployeeServiceImpl;
import com.mycompany.sidebysidesample.gen.service.mapper.EmployeeMapper;
import com.mycompany.sidebysidesample.repository.EmployeeExtensionRepository;
import com.mycompany.sidebysidesample.service.EmployeeServiceExtension;
import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Primary
public class EmployeeServiceExtensionImpl extends EmployeeServiceImpl implements EmployeeServiceExtension {

    private final EmployeeExtensionRepository employeeExtensionRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceExtensionImpl(
        EmployeeRepository employeeRepository,
        EmployeeMapper employeeMapper,
        EmployeeExtensionRepository employeeExtensionRepository
    ) {
        super(employeeRepository, employeeMapper);
        this.employeeExtensionRepository = employeeExtensionRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO secondEmployee() {
        return employeeMapper.toDto(employeeExtensionRepository.findSecondEmployee());
    }
}
