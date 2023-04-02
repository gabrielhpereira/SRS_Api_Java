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
    private Integer idEmployee;

    @Column
    private String description;

    @Column
    private Date date;

    public LogEmployeeEntity() {

    }

    private LogEmployeeEntity(Builder builder) {
        this.id = builder.id;
        this.idEmployee = builder.idEmployee;
        this.description = builder.description;
        this.date = builder.date;
    }

    public static class Builder {
        private Integer id;
        private Integer idEmployee;
        private String description;
        private Date date;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder idEmployee(Integer idEmployee) {
            this.idEmployee = idEmployee;
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

    public Integer getIdEmployee() {
        return this.idEmployee;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDate() {
        return this.date;
    }
}
