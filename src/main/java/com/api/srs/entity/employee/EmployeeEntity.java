package com.api.srs.entity.employee;

import jakarta.persistence.*;

@Entity
@Table
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(unique = true)
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

    public EmployeeEntity(EmployeeEntity entity) {
        this.id = entity.getId();
        this.cpf = entity.getCpf();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.address = entity.getAddress();
        this.email = entity.getEmail();
        this.sector = entity.getSector();
    }

    private EmployeeEntity(Builder builder) {
        super();
        this.id = builder.id;
        this.cpf = builder.cpf;
        this.name = builder.name;
        this.phone = builder.phone;
        this.address = builder.address;
        this.email = builder.email;
        this.sector = builder.sector;
    }

    public static class Builder {
        private Integer id;
        private String cpf;
        private String name;
        private String phone;
        private String address;
        private String email;
        private String sector;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return this.sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}