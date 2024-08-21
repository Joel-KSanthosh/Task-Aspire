package com.aspire.api.repository.impl;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.mapper.CompanyMapper;
import com.aspire.api.model.CompanyEmployee;
import com.aspire.api.repository.CompanyRepository;
import com.aspire.api.repository.ElasticRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CompanyRepositoryElasticImpl implements CompanyRepository {

    private ElasticRepository elasticRepository;
    private CompanyMapper companyMapper;

    @Override
    public boolean checkIfManagerForDepartmentExist(String department) {
        if(elasticRepository.existsDepartmentManager(department) != null){
            return true;
        }
        return false;
    }

    @Override
    public void insertEmployee(CompanyEmployee employee) {
        elasticRepository.save(employee);
    }

    @Override
    public EmployeeGet findManagerById(Integer id) {
        CompanyEmployee employee = elasticRepository.findByManagerId(id);
        System.out.println(employee);
        if (employee != null) {
            EmployeeGet employeeGet = new EmployeeGet(employee.getName(), employee.getDepartment(), id, new ArrayList<>());
            return employeeGet;
        }
        return null;
    }

    @Override
    public Integer findNextId() {
        try {
            CompanyEmployee maxId = elasticRepository.findTopByOrderByIdDesc();
            if (maxId != null) {
                return maxId.getId() + 1;
            }
            return 1;
        } catch (Exception e) {
            // Log the exception and handle it appropriately
            System.err.println("Error finding next ID: " + e.getMessage());
            return 1; // or handle the error as needed
        }
    }

    @Override
    public EmployeePost findEmployeeById(Integer id) {
        CompanyEmployee employee = elasticRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee with given id doesn't exist"));
        return new EmployeePost(employee.getName(), employee.getDesignation(),
                employee.getEmail(), employee.getDepartment(),
                employee.getMobile(), employee.getLocation(),
                employee.getManagerId(), employee.getDateOfJoining());
    }

    @Override
    public void updateEmployee(CompanyEmployee employee) {
        CompanyEmployee employees = elasticRepository.findById(employee.getId()).orElse(null);
        employee.setCreatedTime(employees.getCreatedTime());
        elasticRepository.save(employee);
    }

    @Override
    public void deleteEmployee(CompanyEmployee employee) {
        elasticRepository.delete(findById(employee.getId()));
    }

    @Override
    public boolean checkIfEmployeeUnderManagerExist(Integer id) {
        if(elasticRepository.existsEmployeeUnderManagerById(id)!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<Employee> findEmployeeUnderManagerById(Integer managerId) {
        List<CompanyEmployee> employees = elasticRepository.findAllEmployeeUnderManager(managerId);
        List<Employee> employeeList = new ArrayList<>();
        for(CompanyEmployee employee : employees){
            employeeList.add(companyMapper.toEmployeeDTO(employee));
        }
        return employeeList;
    }

    @Override
    public List<EmployeeGet> findAllManagers() {
        List<CompanyEmployee> managers = elasticRepository.findAllManagers();
        List<EmployeeGet> managerList = new ArrayList<>();
        for(CompanyEmployee employee : managers){
            managerList.add(companyMapper.toEmployeeGet(employee));
        }
        return managerList;
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<CompanyEmployee> employees = elasticRepository.findAllEmployees();
        List<Employee> employeeList = new ArrayList<>();
        for(CompanyEmployee employee : employees){
            employeeList.add(companyMapper.toEmployeeDTO(employee));
        }
        return employeeList;
    }

    @Override
    public CompanyEmployee findById(Integer id) {
        return elasticRepository.findById(id).orElse(null);
    }
}