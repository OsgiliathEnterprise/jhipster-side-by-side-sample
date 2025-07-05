package com.mycompany.sidebysidesample.gen.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Employee;
import com.mycompany.sidebysidesample.gen.domain.Job;
import com.mycompany.sidebysidesample.gen.domain.Task;
import com.mycompany.sidebysidesample.gen.service.dto.EmployeeDTO;
import com.mycompany.sidebysidesample.gen.service.dto.JobDTO;
import com.mycompany.sidebysidesample.gen.service.dto.TaskDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring")
public interface JobMapper extends EntityMapper<JobDTO, Job> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "tasks", source = "tasks", qualifiedByName = "taskIdSet")
    JobDTO toDto(Job s);

    @Mapping(target = "removeTask", ignore = true)
    Job toEntity(JobDTO jobDTO);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("taskId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TaskDTO toDtoTaskId(Task task);

    @Named("taskIdSet")
    default Set<TaskDTO> toDtoTaskIdSet(Set<Task> task) {
        return task.stream().map(this::toDtoTaskId).collect(Collectors.toSet());
    }
}
