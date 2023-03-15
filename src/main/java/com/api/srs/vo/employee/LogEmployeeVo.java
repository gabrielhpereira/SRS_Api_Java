package com.api.srs.vo.employee;

import java.util.Date;

public class LogEmployeeVo {
    private Integer id;
    private String employeeCpf;
    private String description;
    private Date date;

    public LogEmployeeVo() {

    }

    public LogEmployeeVo(Integer id, String employeeCpf, String description, Date date) {
        this.id = id;
        this.employeeCpf = employeeCpf;
        this.description = description;
        this.date = date;
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
