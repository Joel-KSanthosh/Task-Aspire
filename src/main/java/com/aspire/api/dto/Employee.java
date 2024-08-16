package com.aspire.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class Employee {
    private String name;
    private Integer id;
    private String designation;
    private String department;
    private String email;
    private String mobile;
    private String location;
    private Date dateOfJoining;
    private Date createdTime;
    private Date updatedTime;
}
