package com.aspire.api.repository.impl;

import com.aspire.api.dto.Employee;
import com.aspire.api.dto.EmployeeGet;
import com.aspire.api.dto.EmployeePost;
import com.aspire.api.dto.MaxId;
import com.aspire.api.model.CompanyEmployee;
import com.aspire.api.model.Designation;
import com.aspire.api.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private MongoTemplate mongoTemplate;


    @Override
    public boolean checkIfManagerForDepartmentExist(String department) {
        Query query = new Query(Criteria.where("department").is(department)
                .and("managerId").is(0));
        return mongoTemplate.exists(query, "employee");
    }

    @Override
    public void insertEmployee(CompanyEmployee employee) {
        mongoTemplate.insert(employee);
    }

    @Override
    public EmployeeGet findManagerById(Integer id) {
        Query query = new Query(Criteria.where("_id").is(id)
                .and("managerId").is(0).and("designation").is(Designation.MANAGER));
        return mongoTemplate.findOne(query,EmployeeGet.class,"employee");
    }

    @Override
    public Integer findNextId() {
        synchronized (this) {
            Query query = new Query();
            query.with(Sort.by(Sort.Direction.DESC, "_id")).limit(1);

            MaxId maxId = mongoTemplate.findOne(query, MaxId.class, "employee");

            if (maxId == null || maxId.get_id() == null) {
                return 1;
            }
            return maxId.get_id() + 1;
        }
    }

    @Override
    public EmployeePost findEmployeeById(Integer id){
        return mongoTemplate.findById(id,EmployeePost.class,"employee");
    }

    @Override
    public void updateEmployee(CompanyEmployee employee) {
        mongoTemplate.save(employee);
    }

    @Override
    public void deleteEmployee(CompanyEmployee employee) {
        mongoTemplate.remove(employee);
    }

    @Override
    public boolean checkIfEmployeeUnderManagerExist(Integer id) {
        Query query = new Query(Criteria.where("managerId").is(id));
        return mongoTemplate.exists(query,"employee");
    }

    @Override
    public List<Employee> findEmployeeUnderManagerById(Integer managerId) {
        Query query = new Query(Criteria.where("managerId").is(managerId));
        return mongoTemplate.find(query,Employee.class,"employee");
    }

    @Override
    public List<EmployeeGet> findAllManagers() {
        Query query = new Query(Criteria.where("managerId").is(0)
                .and("designation").is(Designation.MANAGER));
        return mongoTemplate.find(query,EmployeeGet.class,"employee");
    }

    @Override
    public List<Employee> findAllEmployees() {
        return mongoTemplate.findAll(Employee.class,"employee");
    }

}
