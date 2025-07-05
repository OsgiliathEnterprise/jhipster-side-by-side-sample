package com.mycompany.sidebysidesample.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Country;
import com.mycompany.sidebysidesample.gen.domain.Region;
import com.mycompany.sidebysidesample.gen.service.dto.CountryDTO;
import com.mycompany.sidebysidesample.gen.service.dto.DepartmentDTO;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.gen.service.dto.RegionDTO;
import com.mycompany.sidebysidesample.gen.service.mapper.EntityMapper;
import com.mycompany.sidebysidesample.service.dto.EmployeeWithDepartmentDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeWithDepartmentMapper extends EntityMapper<EmployeeWithDepartmentDTO, EmployeeDTO> {
    default EmployeeWithDepartmentDTO toDto(EmployeeDTO employeeDTO, DepartmentDTO departmentDTO) {
        if (employeeDTO == null) {
            return null;
        }

        String email = employeeDTO.getEmail();
        String departmentName = departmentDTO.getDepartmentName();
        return new EmployeeWithDepartmentDTO(email, departmentName);
    }
}
