package com.api.srs.repository.employee;

import com.api.srs.dto.employee.LogEmployeeDto;
import com.api.srs.entity.employee.LogEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogEmployeeRepository extends JpaRepository<LogEmployeeEntity, Integer> {

    @Query("SELECT new com.api.srs.dto.employee.LogEmployeeDto(l.id, l.employeeId, l.description, l.date)"
            + "     FROM LogEmployeeEntity l"
            + " WHERE l.id = :employeeId")
    public List<LogEmployeeDto> listAllLogEmployee(
            @Param("employeeId") Integer employeeId
    );
}
