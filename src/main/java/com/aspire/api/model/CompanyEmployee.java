package com.aspire.api.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employee")
public class CompanyEmployee implements Persistable<Integer>{

    private String name;

    @Id
    private Integer id;

    private String designation;
    private String department;

    @Indexed(unique = true, background = true)
    private String email;

    @Indexed(unique = true, background = true)
    private String mobile;

    private String location;
    private Date dateOfJoining;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    private Integer managerId;

    @Override
    public boolean isNew() {
        return createdTime == null;
    }

}

