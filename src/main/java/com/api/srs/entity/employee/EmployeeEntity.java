package com.api.srs.entity.employee;

import jakarta.persistence.*;

@Entity
@Table
public class EmployeeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String cpf;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String sector;

    public EmployeeEntity() {

    }

    private EmployeeEntity(Builder builder) {
        super();
        this.cpf = builder.cpf;
        this.name = builder.name;
        this.phone = builder.phone;
        this.address = builder.address;
        this.email = builder.email;
        this.sector = builder.sector;
    }

    public static class Builder {
        private String cpf;
        private String name;
        private String phone;
        private String address;
        private String email;
        private String sector;

        public Builder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder sector(String sector) {
            this.sector = sector;
            return this;
        }

        public EmployeeEntity build() {
            return new EmployeeEntity(this);
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}