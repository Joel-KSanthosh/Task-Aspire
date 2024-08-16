package com.aspire.api.service;

import com.aspire.api.dto.EmployeePost;

import java.time.LocalDate;

public interface OtherService {
    public void validation(EmployeePost employee);
    public Integer getValidation(Integer experience,Integer id);
    public Integer calculateDifferenceInYears(LocalDate start, LocalDate end);
}
