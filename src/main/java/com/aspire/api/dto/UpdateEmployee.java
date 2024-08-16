package com.aspire.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateEmployee {
    private String employee;
    private String prevManager;
    private String newManager;
}
