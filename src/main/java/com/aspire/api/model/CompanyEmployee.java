package com.aspire.api.model;

import java.time.Instant;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "employee")
public class CompanyEmployee implements Persistable<Integer>{


    private String name;

    @Id
    private Integer id;

    private String designation;
    private String department;

    private String email;

    private String mobile;

    private String location;

    @NotEmpty(message = "Enter joining date")
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant dateOfJoining;

    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant createdTime;

    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant updatedTime;

    private Integer managerId;

    @Override
    public boolean isNew() {
        return createdTime == null;
    }

}

