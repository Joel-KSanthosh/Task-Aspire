package com.aspire.api.dto;

import com.aspire.api.model.Department;
import com.aspire.api.model.Designation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
public class EmployeePost {
    @NotBlank(message = "Enter a valid name")
    private String name;

    @NotBlank(message = "Enter a valid designation")
    private String designation;

    @NotBlank(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Enter a valid department")
    private String department;

    @NotBlank(message = "Enter a valid mobile")
    private String mobile;

    @NotBlank(message = "Enter a valid location")
    private String location;

    @NotEmpty(message = "Enter a valid manager id")
    private Integer managerId;

    @NotNull(message = "Enter a valid date")
    private Instant dateOfJoining;

    @JsonIgnore
    public boolean isManager(){
        return managerId == 0 && Designation.MANAGER.equalsIgnoreCase(designation);
    }

    @JsonIgnore
    public boolean isEmployee(){
        return managerId != 0 && Designation.ASSOCIATE.equalsIgnoreCase(designation);
    }

    @JsonIgnore
    public void findStandardDepartment(){
        if(department.equalsIgnoreCase(Department.BA)){
            setDepartment(Department.BA);
        } else if (department.equalsIgnoreCase(Department.SALES)) {
            setDepartment(Department.SALES);
        } else if (department.equalsIgnoreCase(Department.DELIVERY)) {
            setDepartment(Department.DELIVERY);
        } else if (department.equalsIgnoreCase(Department.QA)) {
            setDepartment(Department.QA);
        } else if (department.equalsIgnoreCase(Department.ENGINEERING)) {
            setDepartment(Department.ENGINEERING);
        }
        else {
            throw new IllegalArgumentException("Enter a valid Department");
        }
    }

    @JsonIgnore
    public void findStandardDesignation(){
        if(designation.equalsIgnoreCase(Designation.MANAGER)){
            setDesignation(Designation.MANAGER);
        } else if (designation.equalsIgnoreCase(Designation.ASSOCIATE)) {
            setDesignation(Designation.ASSOCIATE);
        }
        else {
            throw new IllegalArgumentException("Enter a valid Designation");
        }
    }


}
