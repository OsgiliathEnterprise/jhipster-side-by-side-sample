package com.mycompany.sidebysidesample.gen.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.sidebysidesample.gen.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentDTO.class);
        DepartmentDTO departmentDTO1 = new DepartmentDTO();
        departmentDTO1.setId(1L);
        DepartmentDTO departmentDTO2 = new DepartmentDTO();
        assertThat(departmentDTO1).isNotEqualTo(departmentDTO2);
        departmentDTO2.setId(departmentDTO1.getId());
        assertThat(departmentDTO1).isEqualTo(departmentDTO2);
        departmentDTO2.setId(2L);
        assertThat(departmentDTO1).isNotEqualTo(departmentDTO2);
        departmentDTO1.setId(null);
        assertThat(departmentDTO1).isNotEqualTo(departmentDTO2);
    }
}
