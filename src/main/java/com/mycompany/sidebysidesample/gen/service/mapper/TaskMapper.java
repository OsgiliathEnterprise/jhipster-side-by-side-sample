package com.mycompany.sidebysidesample.gen.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Job;
import com.mycompany.sidebysidesample.gen.domain.Task;
import com.mycompany.sidebysidesample.gen.service.dto.JobDTO;
import com.mycompany.sidebysidesample.gen.service.dto.TaskDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "jobs", source = "jobs", qualifiedByName = "jobIdSet")
    TaskDTO toDto(Task s);

    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "removeJob", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    @Named("jobId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JobDTO toDtoJobId(Job job);

    @Named("jobIdSet")
    default Set<JobDTO> toDtoJobIdSet(Set<Job> job) {
        return job.stream().map(this::toDtoJobId).collect(Collectors.toSet());
    }
}
