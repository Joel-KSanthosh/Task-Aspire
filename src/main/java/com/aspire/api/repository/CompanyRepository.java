package com.aspire.api.repository;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.model.CompanyEmployee;

import java.util.List;

public interface CompanyRepository {
    public boolean checkIfManagerForDepartmentExist(String department);
    public void insertEmployee(CompanyEmployee employee);
    public EmployeeGet findManagerById(Integer id);
    public Integer findNextId();
    public EmployeePost findEmployeeById(Integer id);
    public void updateEmployee(CompanyEmployee employee);
    public void deleteEmployee(CompanyEmployee employee);
    public boolean checkIfEmployeeUnderManagerExist(Integer id);
    public List<Employee> findEmployeeUnderManagerById(Integer managerId);
    public List<EmployeeGet> findAllManagers();
    public List<Employee> findAllEmployees();
    public CompanyEmployee findById(Integer id);
}
