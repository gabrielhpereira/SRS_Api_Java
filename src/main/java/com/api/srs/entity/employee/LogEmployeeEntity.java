package com.api.srs.entity.employee;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class LogEmployeeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String employeeCpf;

    @Column
    private String description;

    @Column
    private Date date;

    public LogEmployeeEntity() {

    }

    private LogEmployeeEntity(Builder builder) {
        this.id = builder.id;
        this.employeeCpf = builder.employeeCpf;
        this.description = builder.description;
        this.date = builder.date;
    }

    public static class Builder {
        private Integer id;
        private String employeeCpf;
        private String description;
        private Date date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder employeeCpf(String employeeCpf) {
            this.employeeCpf = employeeCpf;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public LogEmployeeEntity build() {
            return new LogEmployeeEntity(this);
        }
    }

    public Integer getId() {
        return this.id;
    }

    public String getEmployeeCpf() {
        return this.employeeCpf;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDate() {
        return this.date;
    }
}
