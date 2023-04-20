package com.api.srs.vo.employee;

public class EmployeeVo {
    private Integer id;
    private String cpf;
    private String name;
    private String sector;
    private String phone;
    private String address;
    private String email;

    public EmployeeVo() {}

    public EmployeeVo(Integer id, String cpf, String name, String sector, String phone, String address, String email) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.sector = sector;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}