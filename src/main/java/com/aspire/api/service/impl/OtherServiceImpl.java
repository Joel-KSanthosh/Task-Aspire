package com.aspire.api.service.impl;

import com.aspire.api.dto.EmployeePost;
import com.aspire.api.service.OtherService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;


@Service
public class OtherServiceImpl implements OtherService {

    @Override
    public void validation(EmployeePost employee){
        employee.findStandardDesignation();
        employee.findStandardDepartment();
    }

    @Override
    public Integer getValidation(Integer experience, Integer id){
        if(experience!=null && id!=null){
            if(experience<0 && id<1){
                throw new IllegalArgumentException("Invalid experience and managerId");
            } else if (experience<0) {
                throw new IllegalArgumentException("Invalid experience");
            } else if (id<1) {
                throw new IllegalArgumentException("Invalid managerId");
            }
            return 1;
        }
        else if (experience!=null){
            if (experience<0) {
                throw new IllegalArgumentException("Invalid experience");
            }
            return 2;
        } else if (id!=null) {
            if (id<1) {
                throw new IllegalArgumentException("Invalid experience");
            }
            return 3;
        }
        return 4;
    }

    @Override
    public Integer calculateDifferenceInYears(LocalDate start,LocalDate end){
        return Period.between(start,end).getYears();
    }
}
