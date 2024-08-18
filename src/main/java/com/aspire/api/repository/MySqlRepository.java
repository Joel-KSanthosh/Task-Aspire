package com.aspire.api.repository;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.model.CompanyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MySqlRepository extends JpaRepository<CompanyEmployee,Integer> {

    @Query("SELECT COUNT(e) > 0 FROM CompanyEmployee e WHERE e.department = :department")
    boolean existsDepartmentManager(String department);


    @Query("SELECT e FROM CompanyEmployee e WHERE e.id = :managerId AND e.designation = 'Account Manager'")
    CompanyEmployee findByManagerId(Integer managerId);

    @Query("SELECT MAX(id) FROM CompanyEmployee")
    Integer findMaxId();

    @Query("SELECT COUNT(e) > 0 FROM CompanyEmployee e WHERE e.managerId = :managerId")
    boolean existsEmployeeUnderManagerById(Integer managerId);

    @Query("SELECT new com.aspire.api.dto.Employee(e.name, e.id, e.designation, e.department, e.email, e.mobile, e.location, e.dateOfJoining, e.createdTime, e.updatedTime) " +
            "FROM CompanyEmployee e WHERE e.managerId = :id")
    List<Employee> findAllEmployeeUnderManager(Integer id);

    @Query("SELECT new com.aspire.api.dto.EmployeeGet(e.name,e.department,e.id,null) FROM CompanyEmployee e " +
            "WHERE e.managerId = 0 AND e.designation = 'Account Manager'")
    List<EmployeeGet> findAllManagers();

    @Query("SELECT new com.aspire.api.dto.Employee(e.name, e.id, e.designation, e.department, e.email, e.mobile, e.location, e.dateOfJoining, e.createdTime, e.updatedTime) " +
            "FROM CompanyEmployee e")
    List<Employee> findAllEmployees();
}
