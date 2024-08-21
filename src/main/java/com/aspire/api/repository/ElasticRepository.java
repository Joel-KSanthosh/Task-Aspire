package com.aspire.api.repository;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.model.CompanyEmployee;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticRepository extends ElasticsearchRepository<CompanyEmployee,Integer> {
    @Query("{\"bool\": {\"must\": [{\"term\": {\"department.keyword\": \"?0\"}},{\"term\": {\"designation.keyword\": \"Account Manager\"}}]}}")
    CompanyEmployee existsDepartmentManager(String department);


    @Query("{\"bool\": {\"must\": [{\"term\": {\"id\": \"?0\"}}, {\"term\": {\"designation.keyword\": \"Account Manager\"}}]}}")
    CompanyEmployee findByManagerId(Integer managerId);

    CompanyEmployee findTopByOrderByIdDesc();

    @Query("{\"bool\": {\"must\": [{\"term\": {\"managerId\": \"?0\"}}]}}")
    CompanyEmployee existsEmployeeUnderManagerById(Integer managerId);

    @Query("{\"bool\": {\"must\": [{\"term\": {\"managerId\": \"?0\"}}]}}")
    List<CompanyEmployee> findAllEmployeeUnderManager(Integer id);

    @Query("{\"bool\": {\"must\": [{\"term\": {\"managerId\": 0}}, {\"term\": {\"designation.keyword\": \"Account Manager\"}}]}}")
    List<CompanyEmployee> findAllManagers();

    @Query("{\"bool\": {\"must\": [" +
            "{\"range\": {\"managerId\": {\"gt\": 0}}}, " +
            "{\"term\": {\"designation.keyword\": \"Associate\"}}" +
            "]}}")
    List<CompanyEmployee> findAllEmployees();
}
