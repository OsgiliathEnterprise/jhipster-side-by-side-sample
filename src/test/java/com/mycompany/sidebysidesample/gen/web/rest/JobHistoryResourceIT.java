package com.mycompany.sidebysidesample.gen.web.rest;

import static com.mycompany.sidebysidesample.gen.domain.JobHistoryAsserts.*;
import static com.mycompany.sidebysidesample.gen.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.sidebysidesample.gen.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.sidebysidesample.gen.IntegrationTest;
import com.mycompany.sidebysidesample.gen.domain.Department;
import com.mycompany.sidebysidesample.gen.domain.Employee;
import com.mycompany.sidebysidesample.gen.domain.Job;
import com.mycompany.sidebysidesample.gen.domain.JobHistory;
import com.mycompany.sidebysidesample.gen.repository.JobHistoryRepository;
import com.mycompany.sidebysidesample.gen.service.dto.JobHistoryDTO;
import com.mycompany.sidebysidesample.gen.service.mapper.JobHistoryMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JobHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobHistoryResourceIT {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/job-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobHistoryMapper jobHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobHistoryMockMvc;

    private JobHistory jobHistory;

    private JobHistory insertedJobHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createEntity(EntityManager em) {
        JobHistory jobHistory = new JobHistory().startDate(DEFAULT_START_DATE).endDate(DEFAULT_END_DATE);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        jobHistory.setDepartment(department);
        // Add required entity
        Job job;
        if (TestUtil.findAll(em, Job.class).isEmpty()) {
            job = JobResourceIT.createEntity();
            em.persist(job);
            em.flush();
        } else {
            job = TestUtil.findAll(em, Job.class).get(0);
        }
        jobHistory.setJob(job);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity();
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        jobHistory.setEmployee(employee);
        return jobHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobHistory createUpdatedEntity(EntityManager em) {
        JobHistory updatedJobHistory = new JobHistory().startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);
        // Add required entity
        Department department;
        if (TestUtil.findAll(em, Department.class).isEmpty()) {
            department = DepartmentResourceIT.createUpdatedEntity(em);
            em.persist(department);
            em.flush();
        } else {
            department = TestUtil.findAll(em, Department.class).get(0);
        }
        updatedJobHistory.setDepartment(department);
        // Add required entity
        Job job;
        if (TestUtil.findAll(em, Job.class).isEmpty()) {
            job = JobResourceIT.createUpdatedEntity();
            em.persist(job);
            em.flush();
        } else {
            job = TestUtil.findAll(em, Job.class).get(0);
        }
        updatedJobHistory.setJob(job);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity();
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        updatedJobHistory.setEmployee(employee);
        return updatedJobHistory;
    }

    @BeforeEach
    void initTest() {
        jobHistory = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedJobHistory != null) {
            jobHistoryRepository.delete(insertedJobHistory);
            insertedJobHistory = null;
        }
    }

    @Test
    @Transactional
    void createJobHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);
        var returnedJobHistoryDTO = om.readValue(
            restJobHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobHistoryDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JobHistoryDTO.class
        );

        // Validate the JobHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedJobHistory = jobHistoryMapper.toEntity(returnedJobHistoryDTO);
        assertJobHistoryUpdatableFieldsEquals(returnedJobHistory, getPersistedJobHistory(returnedJobHistory));

        insertedJobHistory = returnedJobHistory;
    }

    @Test
    @Transactional
    void createJobHistoryWithExistingId() throws Exception {
        // Create the JobHistory with an existing ID
        jobHistory.setId(1L);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobHistoryMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobHistories() throws Exception {
        // Initialize the database
        insertedJobHistory = jobHistoryRepository.saveAndFlush(jobHistory);

        // Get all the jobHistoryList
        restJobHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    @Test
    @Transactional
    void getJobHistory() throws Exception {
        // Initialize the database
        insertedJobHistory = jobHistoryRepository.saveAndFlush(jobHistory);

        // Get the jobHistory
        restJobHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, jobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingJobHistory() throws Exception {
        // Get the jobHistory
        restJobHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobHistory() throws Exception {
        // Initialize the database
        insertedJobHistory = jobHistoryRepository.saveAndFlush(jobHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobHistory
        JobHistory updatedJobHistory = jobHistoryRepository.findById(jobHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJobHistory are not directly saved in db
        em.detach(updatedJobHistory);
        updatedJobHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(updatedJobHistory);

        restJobHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobHistoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jobHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJobHistoryToMatchAllProperties(updatedJobHistory);
    }

    @Test
    @Transactional
    void putNonExistingJobHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobHistory.setId(longCount.incrementAndGet());

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobHistoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jobHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobHistory.setId(longCount.incrementAndGet());

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jobHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobHistory.setId(longCount.incrementAndGet());

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedJobHistory = jobHistoryRepository.saveAndFlush(jobHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobHistory using partial update
        JobHistory partialUpdatedJobHistory = new JobHistory();
        partialUpdatedJobHistory.setId(jobHistory.getId());

        partialUpdatedJobHistory.endDate(UPDATED_END_DATE);

        restJobHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobHistory))
            )
            .andExpect(status().isOk());

        // Validate the JobHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJobHistory, jobHistory),
            getPersistedJobHistory(jobHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateJobHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedJobHistory = jobHistoryRepository.saveAndFlush(jobHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobHistory using partial update
        JobHistory partialUpdatedJobHistory = new JobHistory();
        partialUpdatedJobHistory.setId(jobHistory.getId());

        partialUpdatedJobHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE);

        restJobHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobHistory))
            )
            .andExpect(status().isOk());

        // Validate the JobHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobHistoryUpdatableFieldsEquals(partialUpdatedJobHistory, getPersistedJobHistory(partialUpdatedJobHistory));
    }

    @Test
    @Transactional
    void patchNonExistingJobHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobHistory.setId(longCount.incrementAndGet());

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobHistoryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobHistory.setId(longCount.incrementAndGet());

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobHistory.setId(longCount.incrementAndGet());

        // Create the JobHistory
        JobHistoryDTO jobHistoryDTO = jobHistoryMapper.toDto(jobHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jobHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobHistory() throws Exception {
        // Initialize the database
        insertedJobHistory = jobHistoryRepository.saveAndFlush(jobHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jobHistory
        restJobHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobHistory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jobHistoryRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected JobHistory getPersistedJobHistory(JobHistory jobHistory) {
        return jobHistoryRepository.findById(jobHistory.getId()).orElseThrow();
    }

    protected void assertPersistedJobHistoryToMatchAllProperties(JobHistory expectedJobHistory) {
        assertJobHistoryAllPropertiesEquals(expectedJobHistory, getPersistedJobHistory(expectedJobHistory));
    }

    protected void assertPersistedJobHistoryToMatchUpdatableProperties(JobHistory expectedJobHistory) {
        assertJobHistoryAllUpdatablePropertiesEquals(expectedJobHistory, getPersistedJobHistory(expectedJobHistory));
    }
}
