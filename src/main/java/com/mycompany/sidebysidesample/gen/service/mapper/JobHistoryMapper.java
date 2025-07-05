package com.mycompany.sidebysidesample.gen.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Department;
import com.mycompany.sidebysidesample.gen.domain.Employee;
import com.mycompany.sidebysidesample.gen.domain.Job;
import com.mycompany.sidebysidesample.gen.domain.JobHistory;
import com.mycompany.sidebysidesample.gen.service.dto.DepartmentDTO;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.gen.service.dto.JobDTO;
import com.mycompany.sidebysidesample.gen.service.dto.JobHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobHistory} and its DTO {@link JobHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface JobHistoryMapper extends EntityMapper<JobHistoryDTO, JobHistory> {
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentId")
    @Mapping(target = "job", source = "job", qualifiedByName = "jobId")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    JobHistoryDTO toDto(JobHistory s);

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);

    @Named("jobId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JobDTO toDtoJobId(Job job);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
