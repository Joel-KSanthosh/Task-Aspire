package com.aspire.api.service;

import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.dto.UpdateEmployee;

import java.util.List;

public interface CompanyService {
    public void insertEmployee(EmployeePost employee);
    public UpdateEmployee changeEmployeeManager(Integer employeeId, Integer managerId);
    public String deleteEmployee(Integer id);
    public List<?> getEmployees(Integer experience, Integer managerId);
}
