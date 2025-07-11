package com.mycompany.sidebysidesample.gen.service;

import com.mycompany.sidebysidesample.gen.service.dto.JobDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.sidebysidesample.gen.domain.Job}.
 */
public interface JobService {
    /**
     * Save a job.
     *
     * @param jobDTO the entity to save.
     * @return the persisted entity.
     */
    JobDTO save(JobDTO jobDTO);

    /**
     * Updates a job.
     *
     * @param jobDTO the entity to update.
     * @return the persisted entity.
     */
    JobDTO update(JobDTO jobDTO);

    /**
     * Partially updates a job.
     *
     * @param jobDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobDTO> partialUpdate(JobDTO jobDTO);

    /**
     * Get all the jobs.
     *
     * @return the list of entities.
     */
    List<JobDTO> findAll();

    /**
     * Get all the JobDTO where JobHistory is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<JobDTO> findAllWhereJobHistoryIsNull();

    /**
     * Get all the jobs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" job.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobDTO> findOne(Long id);

    /**
     * Delete the "id" job.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
