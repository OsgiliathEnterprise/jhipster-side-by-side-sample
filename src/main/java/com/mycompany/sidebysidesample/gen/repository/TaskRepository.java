package com.mycompany.sidebysidesample.gen.repository;

import com.mycompany.sidebysidesample.gen.domain.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}
