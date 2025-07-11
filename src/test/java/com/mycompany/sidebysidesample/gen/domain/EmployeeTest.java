package com.mycompany.sidebysidesample.gen.domain;

import static com.mycompany.sidebysidesample.gen.domain.DepartmentTestSamples.*;
import static com.mycompany.sidebysidesample.gen.domain.EmployeeTestSamples.*;
import static com.mycompany.sidebysidesample.gen.domain.EmployeeTestSamples.*;
import static com.mycompany.sidebysidesample.gen.domain.JobHistoryTestSamples.*;
import static com.mycompany.sidebysidesample.gen.domain.JobTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.sidebysidesample.gen.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void departmentTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        employee.setDepartment(departmentBack);
        assertThat(employee.getDepartment()).isEqualTo(departmentBack);

        employee.department(null);
        assertThat(employee.getDepartment()).isNull();
    }

    @Test
    void managerTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        employee.setManager(employeeBack);
        assertThat(employee.getManager()).isEqualTo(employeeBack);

        employee.manager(null);
        assertThat(employee.getManager()).isNull();
    }

    @Test
    void jobTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Job jobBack = getJobRandomSampleGenerator();

        employee.addJob(jobBack);
        assertThat(employee.getJobs()).containsOnly(jobBack);
        assertThat(jobBack.getEmployee()).isEqualTo(employee);

        employee.removeJob(jobBack);
        assertThat(employee.getJobs()).doesNotContain(jobBack);
        assertThat(jobBack.getEmployee()).isNull();

        employee.jobs(new HashSet<>(Set.of(jobBack)));
        assertThat(employee.getJobs()).containsOnly(jobBack);
        assertThat(jobBack.getEmployee()).isEqualTo(employee);

        employee.setJobs(new HashSet<>());
        assertThat(employee.getJobs()).doesNotContain(jobBack);
        assertThat(jobBack.getEmployee()).isNull();
    }

    @Test
    void jobHistoryTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        JobHistory jobHistoryBack = getJobHistoryRandomSampleGenerator();

        employee.setJobHistory(jobHistoryBack);
        assertThat(employee.getJobHistory()).isEqualTo(jobHistoryBack);
        assertThat(jobHistoryBack.getEmployee()).isEqualTo(employee);

        employee.jobHistory(null);
        assertThat(employee.getJobHistory()).isNull();
        assertThat(jobHistoryBack.getEmployee()).isNull();
    }
}
