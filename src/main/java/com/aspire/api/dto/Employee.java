package com.aspire.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employee {
    private String name;
    private Integer id;
    private String designation;
    private String department;
    private String email;
    private String mobile;
    private String location;
    private Instant dateOfJoining;
    private Instant createdTime;
    private Instant updatedTime;
}
