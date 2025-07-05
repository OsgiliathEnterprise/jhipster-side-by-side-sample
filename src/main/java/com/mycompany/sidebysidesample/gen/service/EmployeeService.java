package com.mycompany.sidebysidesample.gen.service;

import com.mycompany.sidebysidesample.gen.domain.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.sidebysidesample.gen.domain.Employee}.
 */
public interface EmployeeService {
    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    Employee save(Employee employee);

    /**
     * Updates a employee.
     *
     * @param employee the entity to update.
     * @return the persisted entity.
     */
    Employee update(Employee employee);

    /**
     * Partially updates a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Employee> partialUpdate(Employee employee);

    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    List<Employee> findAll();

    /**
     * Get all the Employee where JobHistory is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Employee> findAllWhereJobHistoryIsNull();

    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Employee> findOne(Long id);

    /**
     * Delete the "id" employee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
