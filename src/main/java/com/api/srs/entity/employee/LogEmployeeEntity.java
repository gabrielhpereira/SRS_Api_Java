package com.api.srs.entity.employee;

import jakarta.persistence.*;

@Entity
@Table
public class LogEmployeeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer employeeId;

    @Column
    private String description;

    @Column
    private String date;

    public LogEmployeeEntity() {

    }

    private LogEmployeeEntity(Builder builder) {
        this.id = builder.id;
        this.employeeId = builder.employeeId;
        this.description = builder.description;
        this.date = builder.date;
    }

    public static class Builder {
        private Integer id;
        private Integer employeeId;
        private String description;
        private String date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder employeeId(Integer employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public LogEmployeeEntity build() {
            return new LogEmployeeEntity(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
