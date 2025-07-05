package com.mycompany.sidebysidesample.repository;

import com.mycompany.sidebysidesample.gen.domain.Employee;
import com.mycompany.sidebysidesample.gen.repository.EmployeeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface EmployeeExtensionRepository extends EmployeeRepository, EmployeeRepositoryRepositoryInternal {}

interface EmployeeRepositoryRepositoryInternal {
    @Query("FROM Employee entity WHERE entity.id = 2")
    Employee findSecondEmployee();
}
