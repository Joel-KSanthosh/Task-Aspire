package com.aspire.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeGet {
    @JsonProperty("accountManager")
    private String name;

    @JsonProperty("department")
    private String department;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("employeeList")
    private List<Employee> employeeList;
}
