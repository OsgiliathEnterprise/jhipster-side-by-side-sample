package com.mycompany.sidebysidesample.gen.service.impl;

import com.mycompany.sidebysidesample.gen.domain.Department;
import com.mycompany.sidebysidesample.gen.repository.DepartmentRepository;
import com.mycompany.sidebysidesample.gen.service.DepartmentService;
import com.mycompany.sidebysidesample.gen.service.dto.DepartmentDTO;
import com.mycompany.sidebysidesample.gen.service.mapper.DepartmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.sidebysidesample.gen.domain.Department}.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        LOG.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentDTO update(DepartmentDTO departmentDTO) {
        LOG.debug("Request to update Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public Optional<DepartmentDTO> partialUpdate(DepartmentDTO departmentDTO) {
        LOG.debug("Request to partially update Department : {}", departmentDTO);

        return departmentRepository
            .findById(departmentDTO.getId())
            .map(existingDepartment -> {
                departmentMapper.partialUpdate(existingDepartment, departmentDTO);

                return existingDepartment;
            })
            .map(departmentRepository::save)
            .map(departmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAll() {
        LOG.debug("Request to get all Departments");
        return departmentRepository.findAll().stream().map(departmentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the departments where JobHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAllWhereJobHistoryIsNull() {
        LOG.debug("Request to get all departments where JobHistory is null");
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
            .filter(department -> department.getJobHistory() == null)
            .map(departmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> findOne(Long id) {
        LOG.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Department : {}", id);
        departmentRepository.deleteById(id);
    }
}
