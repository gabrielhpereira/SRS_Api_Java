package com.api.srs.vo.employee;

import java.util.Date;

public class LogEmployeeVo {
    private Integer id;
    private Integer idEmployee;
    private String description;
    private Date date;

    public LogEmployeeVo() {

    }

    public LogEmployeeVo(Integer id, Integer idEmployee, String description, Date date) {
        this.id = id;
        this.idEmployee = idEmployee;
        this.description = description;
        this.date = date;
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
