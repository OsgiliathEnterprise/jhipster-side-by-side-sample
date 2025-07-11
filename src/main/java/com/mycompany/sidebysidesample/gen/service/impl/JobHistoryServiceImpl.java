package com.mycompany.sidebysidesample.gen.service.impl;

import com.mycompany.sidebysidesample.gen.domain.JobHistory;
import com.mycompany.sidebysidesample.gen.repository.JobHistoryRepository;
import com.mycompany.sidebysidesample.gen.service.JobHistoryService;
import com.mycompany.sidebysidesample.gen.service.dto.JobHistoryDTO;
import com.mycompany.sidebysidesample.gen.service.mapper.JobHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final JobHistoryMapper jobHistoryMapper;

    public JobHistoryServiceImpl(JobHistoryRepository jobHistoryRepository, JobHistoryMapper jobHistoryMapper) {
        this.jobHistoryRepository = jobHistoryRepository;
        this.jobHistoryMapper = jobHistoryMapper;
    }

    @Override
    public JobHistoryDTO save(JobHistoryDTO jobHistoryDTO) {
        LOG.debug("Request to save JobHistory : {}", jobHistoryDTO);
        JobHistory jobHistory = jobHistoryMapper.toEntity(jobHistoryDTO);
        jobHistory = jobHistoryRepository.save(jobHistory);
        return jobHistoryMapper.toDto(jobHistory);
    }

    @Override
    public JobHistoryDTO update(JobHistoryDTO jobHistoryDTO) {
        LOG.debug("Request to update JobHistory : {}", jobHistoryDTO);
        JobHistory jobHistory = jobHistoryMapper.toEntity(jobHistoryDTO);
        jobHistory = jobHistoryRepository.save(jobHistory);
        return jobHistoryMapper.toDto(jobHistory);
    }

    @Override
    public Optional<JobHistoryDTO> partialUpdate(JobHistoryDTO jobHistoryDTO) {
        LOG.debug("Request to partially update JobHistory : {}", jobHistoryDTO);

        return jobHistoryRepository
            .findById(jobHistoryDTO.getId())
            .map(existingJobHistory -> {
                jobHistoryMapper.partialUpdate(existingJobHistory, jobHistoryDTO);

                return existingJobHistory;
            })
            .map(jobHistoryRepository::save)
            .map(jobHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobHistoryDTO> findAll() {
        LOG.debug("Request to get all JobHistories");
        return jobHistoryRepository.findAll().stream().map(jobHistoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get JobHistory : {}", id);
        return jobHistoryRepository.findById(id).map(jobHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete JobHistory : {}", id);
        jobHistoryRepository.deleteById(id);
    }
}
