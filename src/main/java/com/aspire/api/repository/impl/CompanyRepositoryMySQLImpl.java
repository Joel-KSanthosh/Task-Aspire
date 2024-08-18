package com.aspire.api.repository.impl;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.model.CompanyEmployee;
import com.aspire.api.repository.CompanyRepository;
import com.aspire.api.repository.MySqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class CompanyRepositoryMySQLImpl implements CompanyRepository {

    private MySqlRepository mySqlRepository;

    @Override
    public boolean checkIfManagerForDepartmentExist(String department) {
        return mySqlRepository.existsDepartmentManager(department);
    }

    @Override
    public void insertEmployee(CompanyEmployee employee) {
        System.out.println(employee);
        mySqlRepository.save(employee);
    }

    @Override
    public EmployeeGet findManagerById(Integer id) {
        CompanyEmployee employee = mySqlRepository.findByManagerId(id);
        System.out.println(employee);
        if(employee!=null) {
            EmployeeGet employeeGet = new EmployeeGet(employee.getName(), employee.getDepartment(), id, new ArrayList<>());
            return employeeGet;
        }
        return null;
    }

    @Override
    public Integer findNextId() {
        Integer id = mySqlRepository.findMaxId();
        if(id==null){
            return 1;
        }
        return id+1;
    }

    @Override
    public EmployeePost findEmployeeById(Integer id) {
        CompanyEmployee employee = mySqlRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee with given id doesn't exist"));
        return new EmployeePost(employee.getName(), employee.getDesignation(),
                employee.getEmail(), employee.getDepartment(),
                employee.getMobile(), employee.getLocation(),
                employee.getManagerId(),employee.getDateOfJoining());
    }

    @Override
    public void updateEmployee(CompanyEmployee employee) {
        CompanyEmployee employees = mySqlRepository.findById(employee.getId()).orElse(null);
        employee.setCreatedTime(employees.getCreatedTime());
        mySqlRepository.save(employee);
    }

    @Override
    public void deleteEmployee(CompanyEmployee employee) {
        mySqlRepository.delete(findById(employee.getId()));
    }

    @Override
    public boolean checkIfEmployeeUnderManagerExist(Integer id) {
        return mySqlRepository.existsEmployeeUnderManagerById(id);
    }

    @Override
    public List<Employee> findEmployeeUnderManagerById(Integer managerId) {
        return mySqlRepository.findAllEmployeeUnderManager(managerId);
    }

    @Override
    public List<EmployeeGet> findAllManagers() {
        return mySqlRepository.findAllManagers();
    }

    @Override
    public List<Employee> findAllEmployees() {
        return mySqlRepository.findAllEmployees();
    }

    @Override
    public CompanyEmployee findById(Integer id) {
        return mySqlRepository.findById(id).orElse(null);
    }
}
