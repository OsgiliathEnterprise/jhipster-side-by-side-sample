package com.mycompany.sidebysidesample.gen.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Department;
import com.mycompany.sidebysidesample.gen.domain.Location;
import com.mycompany.sidebysidesample.gen.service.dto.DepartmentDTO;
import com.mycompany.sidebysidesample.gen.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    DepartmentDTO toDto(Department s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
