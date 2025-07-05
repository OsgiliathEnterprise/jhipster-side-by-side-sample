package com.mycompany.sidebysidesample.gen.service;

import com.mycompany.sidebysidesample.gen.service.dto.JobHistoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.sidebysidesample.gen.domain.JobHistory}.
 */
public interface JobHistoryService {
    /**
     * Save a jobHistory.
     *
     * @param jobHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    JobHistoryDTO save(JobHistoryDTO jobHistoryDTO);

    /**
     * Updates a jobHistory.
     *
     * @param jobHistoryDTO the entity to update.
     * @return the persisted entity.
     */
    JobHistoryDTO update(JobHistoryDTO jobHistoryDTO);

    /**
     * Partially updates a jobHistory.
     *
     * @param jobHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobHistoryDTO> partialUpdate(JobHistoryDTO jobHistoryDTO);

    /**
     * Get all the jobHistories.
     *
     * @return the list of entities.
     */
    List<JobHistoryDTO> findAll();

    /**
     * Get the "id" jobHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" jobHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
