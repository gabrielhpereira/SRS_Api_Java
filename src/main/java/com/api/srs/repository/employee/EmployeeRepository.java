package com.api.srs.repository.employee;

import com.api.srs.entity.employee.EmployeeEntity;
import com.api.srs.vo.employee.EmployeeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    String SELECT = "SELECT new com.api.srs.vo.employee.EmployeeVo(e.id, e.cpf, e.name, e.sector) FROM EmployeeEntity e";

    @Query(SELECT)
    public List<EmployeeVo> listAllEmployee();

    @Query(SELECT
            + " WHERE 1 = 1"
            + "     AND :id = 0 OR e.id = :id"
            + "     AND :cpf IS NULL OR e.cpf LIKE CONCAT ('%', :cpf, '%')"
            + "     AND :name IS NULL OR e.name LIKE CONCAT('%', :name, '%')"
            + "     AND :sector IS NULL OR e.sector LIKE CONCAT('%', :sector, '%')")
    public List<EmployeeVo> listEmployeeByFilters(
            @Param("id") Integer id,
            @Param("cpf") String cpf,
            @Param("name") String name,
            @Param("sector") String sector
    );
}
