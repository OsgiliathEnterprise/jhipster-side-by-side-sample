package com.mycompany.sidebysidesample.gen.service.impl;

import com.mycompany.sidebysidesample.gen.domain.Job;
import com.mycompany.sidebysidesample.gen.repository.JobRepository;
import com.mycompany.sidebysidesample.gen.service.JobService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.sidebysidesample.gen.domain.Job}.
 */
@Service
@Transactional
public class JobServiceImpl implements JobService {

    private static final Logger LOG = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job save(Job job) {
        LOG.debug("Request to save Job : {}", job);
        return jobRepository.save(job);
    }

    @Override
    public Job update(Job job) {
        LOG.debug("Request to update Job : {}", job);
        return jobRepository.save(job);
    }

    @Override
    public Optional<Job> partialUpdate(Job job) {
        LOG.debug("Request to partially update Job : {}", job);

        return jobRepository
            .findById(job.getId())
            .map(existingJob -> {
                if (job.getJobTitle() != null) {
                    existingJob.setJobTitle(job.getJobTitle());
                }
                if (job.getMinSalary() != null) {
                    existingJob.setMinSalary(job.getMinSalary());
                }
                if (job.getMaxSalary() != null) {
                    existingJob.setMaxSalary(job.getMaxSalary());
                }

                return existingJob;
            })
            .map(jobRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> findAll() {
        LOG.debug("Request to get all Jobs");
        return jobRepository.findAll();
    }

    public Page<Job> findAllWithEagerRelationships(Pageable pageable) {
        return jobRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the jobs where JobHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Job> findAllWhereJobHistoryIsNull() {
        LOG.debug("Request to get all jobs where JobHistory is null");
        return StreamSupport.stream(jobRepository.findAll().spliterator(), false).filter(job -> job.getJobHistory() == null).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Job> findOne(Long id) {
        LOG.debug("Request to get Job : {}", id);
        return jobRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Job : {}", id);
        jobRepository.deleteById(id);
    }
}
