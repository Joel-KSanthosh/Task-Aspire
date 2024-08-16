package com.aspire.api.controller;

import com.aspire.api.dto.CustomResponse;
import com.aspire.api.dto.EmployeePost;

import com.aspire.api.dto.UpdateEmployee;
import com.aspire.api.service.CompanyService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CompanyController {

    private CompanyService companyService;

    @GetMapping("employee")
    public CustomResponse getEmployee(
            @RequestParam(required = false,name = "year-of-experience") Integer experience,
            @RequestParam(required = false) Integer managerId
    ){
        return new CustomResponse("successfully fetched",companyService.getEmployees(experience,managerId));
    }

    @PostMapping("/employee/add")
    public CustomResponse addEmployee(@Valid @RequestBody EmployeePost employee){
        companyService.insertEmployee(employee);
        return new CustomResponse("successfully created");
    }

    @PutMapping("/employee/change")
    public CustomResponse changeEmployeeManager(@RequestParam Integer employeeId,@RequestParam Integer managerId){
        UpdateEmployee employee = companyService.changeEmployeeManager(employeeId,managerId);
        return new CustomResponse(employee.getEmployee()+"'s manager has been successfully changed from "
                +employee.getPrevManager()+" to "+employee.getNewManager());
    }

    @DeleteMapping("/employee/delete")
    public CustomResponse deleteEmployee(@RequestParam Integer employeeId){
        String name = companyService.deleteEmployee(employeeId);
        return new CustomResponse("Successfully deleted "+name+" from employee list of the organization");
    }
}
