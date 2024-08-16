package com.aspire.api.service.impl;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.dto.UpdateEmployee;
import com.aspire.api.exception.ManagerAlreadyExistException;
import com.aspire.api.exception.ManagerDoesNotExistException;
import com.aspire.api.mapper.CompanyMapper;
import com.aspire.api.model.CompanyEmployee;
import com.aspire.api.repository.CompanyRepository;
import com.aspire.api.service.CompanyService;
import com.aspire.api.service.OtherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private CompanyMapper companyMapper;
    private OtherService otherService;

    @Override
    public List<?> getEmployees(Integer experience,Integer managerId){
        Integer status = otherService.getValidation(experience,managerId);
        List<EmployeeGet> employeeList = new ArrayList<>();
        List<Employee> experiencedEmployees = new ArrayList<>();
        if(status.equals(1)){
            EmployeeGet manager = companyRepository.findManagerById(managerId);
            if(manager!=null){
                List<Employee> employees = companyRepository.findEmployeeUnderManagerById(managerId);
                if(employees!=null) {
                    LocalDate currentTime = LocalDate.now();
                    for (Employee employee : employees) {
                        if (otherService.calculateDifferenceInYears(employee.getDateOfJoining()
                                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                                currentTime
                        ) >= experience) {
                            experiencedEmployees.add(employee);
                        }
                    }
                    manager.setEmployeeList(experiencedEmployees);
                    employeeList.add(manager);
                }
                return employeeList;
            }
            throw new ManagerDoesNotExistException("Manager with given id doesn't exist");

        } else if (status.equals(2)) {
            List<Employee> employees = companyRepository.findAllEmployees();
            if(employees!=null) {
                LocalDate currentTime = LocalDate.now();
                for (Employee employee : employees) {
                    if (otherService.calculateDifferenceInYears(employee.getDateOfJoining()
                                    .toInstant().atZone(ZoneId.systemDefault())
                            .toLocalDate(), currentTime) >= experience) {
                        experiencedEmployees.add(employee);
                    }
                }
            }
            return experiencedEmployees;
        }
        else if (status.equals(3)){
            EmployeeGet manager = companyRepository.findManagerById(managerId);
            if(manager!=null) {
                List<Employee> employees = companyRepository.findEmployeeUnderManagerById(managerId);
                if(employees!=null) {
                    manager.setEmployeeList(employees);
                    employeeList.add(manager);
                }
                return employeeList;
            }
            throw new ManagerDoesNotExistException("Manager with given id doesn't exist");
        } else{
            List<EmployeeGet> managers = companyRepository.findAllManagers();
            if(managers!=null) {
                for (EmployeeGet manager : managers) {
                    manager.setEmployeeList(companyRepository.findEmployeeUnderManagerById(manager.getId()));
                }
                return managers;
            }
            return employeeList;
        }
    }

    @Override
    public void insertEmployee(EmployeePost employee) {
        otherService.validation(employee);
        if(employee.isManager()){
            if(companyRepository.checkIfManagerForDepartmentExist(employee.getDepartment())){
                throw new ManagerAlreadyExistException("Manager for "+employee.getDepartment()+" department already exist");
            }
            else{
                CompanyEmployee companyEmployee = companyMapper.toCompanyEmployee(employee);
                companyEmployee.setId(companyRepository.findNextId());
                companyRepository.insertEmployee(companyEmployee);
            }

        } else if (employee.isEmployee()) {
            EmployeeGet manager = companyRepository.findManagerById(employee.getManagerId());
            if(manager!=null){
                if(manager.getDepartment().equalsIgnoreCase(employee.getDepartment())){
                    CompanyEmployee companyEmployee = companyMapper.toCompanyEmployee(employee);
                    companyEmployee.setId(companyRepository.findNextId());
                    companyRepository.insertEmployee(companyEmployee);
                }
                else {
                    throw new IllegalArgumentException("Given manager department ("
                            + manager.getDepartment() + ") and employee department ("
                            + employee.getDepartment() + ") does not match");
                }
            }
            else {
                throw new ManagerDoesNotExistException("Manager with given id doesn't exist");
            }

        }
        else {
            throw new IllegalArgumentException("Enter a valid Manager or Employee");
        }
    }

    @Override
    public UpdateEmployee changeEmployeeManager(Integer employeeId, Integer managerId) {
        EmployeePost employee = companyRepository.findEmployeeById(employeeId);
        if (employee != null) {
            if (employee.isManager()) {
                throw new IllegalArgumentException("Cannot change a manager");
            } else if (employee.isEmployee()) {
                EmployeeGet prevManager = companyRepository.findManagerById(employee.getManagerId());
                if(managerId.equals(prevManager.getId())){
                    throw new IllegalArgumentException("Employee is under the given manager");
                }
                EmployeeGet newManager = companyRepository.findManagerById(managerId);
                if (newManager != null) {
                    employee.setDepartment(newManager.getDepartment());
                    employee.setManagerId(managerId);
                    CompanyEmployee companyEmployee = companyMapper.toCompanyEmployee(employee);
                    companyEmployee.setId(employeeId);
                    companyRepository.updateEmployee(companyEmployee);
                    return new UpdateEmployee(employee.getName(),
                            prevManager.getName(),
                            newManager.getName());
                } else {
                    throw new ManagerDoesNotExistException("Manager with given id doesn't exist");
                }
            } else {
                throw new IllegalArgumentException("Employee with given id doesn't exist");
            }
        } else {
            throw new IllegalArgumentException("Employee with given id doesn't exist");
        }
    }

    @Override
    public String deleteEmployee(Integer id) {
        EmployeePost employee = companyRepository.findEmployeeById(id);
        if(employee!=null){
            if(employee.isManager()){
                if(companyRepository.checkIfEmployeeUnderManagerExist(id)){
                    throw new IllegalArgumentException("Cannot delete manager with employees");
                }
            }
            CompanyEmployee companyEmployee = companyMapper.toCompanyEmployee(employee);
            companyEmployee.setId(id);
            companyRepository.deleteEmployee(companyEmployee);
            return employee.getName();
        }
        throw new IllegalArgumentException("Employee with given id doesn't exist");
    }

}
