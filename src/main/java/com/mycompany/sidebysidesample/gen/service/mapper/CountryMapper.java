package com.mycompany.sidebysidesample.gen.service.mapper;

import com.mycompany.sidebysidesample.gen.domain.Country;
import com.mycompany.sidebysidesample.gen.domain.Region;
import com.mycompany.sidebysidesample.gen.service.dto.CountryDTO;
import com.mycompany.sidebysidesample.gen.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "region", source = "region", qualifiedByName = "regionId")
    CountryDTO toDto(Country s);

    @Named("regionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RegionDTO toDtoRegionId(Region region);
}
