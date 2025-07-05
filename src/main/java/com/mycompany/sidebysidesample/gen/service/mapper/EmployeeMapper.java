package com.mycompany.sidebysidesample.gen.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Department;
import com.mycompany.sidebysidesample.gen.domain.Employee;
import com.mycompany.sidebysidesample.gen.service.dto.DepartmentDTO;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentId")
    @Mapping(target = "manager", source = "manager", qualifiedByName = "employeeId")
    EmployeeDTO toDto(Employee s);

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
