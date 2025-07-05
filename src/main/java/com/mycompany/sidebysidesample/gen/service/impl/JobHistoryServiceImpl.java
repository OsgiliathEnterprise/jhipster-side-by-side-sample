package com.mycompany.sidebysidesample.gen.service.impl;

import com.mycompany.sidebysidesample.gen.domain.JobHistory;
import com.mycompany.sidebysidesample.gen.repository.JobHistoryRepository;
import com.mycompany.sidebysidesample.gen.service.JobHistoryService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.sidebysidesample.gen.domain.JobHistory}.
 */
@Service
@Transactional
public class JobHistoryServiceImpl implements JobHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(JobHistoryServiceImpl.class);

    private final JobHistoryRepository jobHistoryRepository;

    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository) {
        this.jobHistoryRepository = jobHistoryRepository;
    }

    @Override
    public JobHistory save(JobHistory jobHistory) {
        LOG.debug("Request to save JobHistory : {}", jobHistory);
        return jobHistoryRepository.save(jobHistory);
    }

    @Override
    public JobHistory update(JobHistory jobHistory) {
        LOG.debug("Request to update JobHistory : {}", jobHistory);
        return jobHistoryRepository.save(jobHistory);
    }

    @Override
    public Optional<JobHistory> partialUpdate(JobHistory jobHistory) {
        LOG.debug("Request to partially update JobHistory : {}", jobHistory);

        return jobHistoryRepository
            .findById(jobHistory.getId())
            .map(existingJobHistory -> {
                if (jobHistory.getStartDate() != null) {
                    existingJobHistory.setStartDate(jobHistory.getStartDate());
                }
                if (jobHistory.getEndDate() != null) {
                    existingJobHistory.setEndDate(jobHistory.getEndDate());
                }

                return existingJobHistory;
            })
            .map(jobHistoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobHistory> findAll() {
        LOG.debug("Request to get all JobHistories");
        return jobHistoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobHistory> findOne(Long id) {
        LOG.debug("Request to get JobHistory : {}", id);
        return jobHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete JobHistory : {}", id);
        jobHistoryRepository.deleteById(id);
    }
}
