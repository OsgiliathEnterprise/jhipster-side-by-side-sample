package com.mycompany.sidebysidesample.gen.service.mapper;

import static com.mycompany.sidebysidesample.gen.domain.DepartmentAsserts.*;
import static com.mycompany.sidebysidesample.gen.domain.DepartmentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartmentMapperTest {

    private DepartmentMapper departmentMapper;

    @BeforeEach
    void setUp() {
        departmentMapper = new DepartmentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDepartmentSample1();
        var actual = departmentMapper.toEntity(departmentMapper.toDto(expected));
        assertDepartmentAllPropertiesEquals(expected, actual);
    }
}
