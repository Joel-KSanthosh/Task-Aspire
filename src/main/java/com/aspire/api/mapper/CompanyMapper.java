package com.aspire.api.mapper;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.model.CompanyEmployee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CompanyEmployee toCompanyEmployee(EmployeePost employeePost){
        return modelMapper.map(employeePost, CompanyEmployee.class);
    }

    public Employee toEmployeeDTO(CompanyEmployee companyEmployee){
        return modelMapper.map(companyEmployee, Employee.class);
    }

    public EmployeeGet toEmployeeGet(CompanyEmployee companyEmployee){
        return modelMapper.map(companyEmployee, EmployeeGet.class);
    }

}
