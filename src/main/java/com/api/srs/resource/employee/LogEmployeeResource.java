package com.api.srs.resource.employee;

import com.api.srs.service.employee.LogEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logEmployee")
public class LogEmployeeResource {

    @Autowired
    private LogEmployeeService logEmployeeService;
}
