package com.api.srs.vo.employee;

public class EmployeeVo {

    private String cpf;
    private String name;
    private String sector;

    public EmployeeVo(){

    }

    public EmployeeVo(String cpf, String name, String sector){
        this.cpf = cpf;
        this.name = name;
        this.sector = sector;
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

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}