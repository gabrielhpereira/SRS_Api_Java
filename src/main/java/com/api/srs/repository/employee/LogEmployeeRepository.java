package com.api.srs.repository.employee;

import com.api.srs.entity.employee.LogEmployeeEntity;
import com.api.srs.vo.employee.LogEmployeeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogEmployeeRepository extends JpaRepository<LogEmployeeEntity, Integer> {

    @Query("SELECT new com.api.srs.vo.employee.LogEmployeeVo(l.id, l.idEmployee, l.description, l.date)"
            + "     FROM LogEmployeeEntity l"
            + " WHERE l.id = :idEmployee")
    public List<LogEmployeeVo> listAllLogEmployee(
            @Param("idEmployee") Integer idEmployee
    );
}
