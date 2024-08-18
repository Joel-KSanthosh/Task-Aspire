package com.aspire.api.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
public class CompanyEmployee implements Persistable<Integer>{


    private String name;

    @Id
    private Integer id;

    private String designation;
    private String department;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String location;
    private Date dateOfJoining;

    @CreatedDate
    @Column(updatable = false)
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    private Integer managerId;

    @Override
    public boolean isNew() {
        return createdTime == null;
    }

}

