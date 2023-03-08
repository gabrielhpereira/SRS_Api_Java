package com.api.srs.repository.user;

import com.api.srs.entity.user.UserEntity;
import com.api.srs.vo.user.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    String SELECT = "SELECT new com.api.srs.vo.user.UserVo(u) FROM UserEntity u";

    @Query(SELECT)
    public List<UserVo> listAllUsers();

    @Query(SELECT
            + " WHERE :id = 0 OR u.id = :id"
            + " AND :name IS NULL OR u.name LIKE CONCAT('%', :name, '%')"
            + " AND :email IS NULL OR u.email LIKE CONCAT('%', :email, '%')"
            + " AND :address iS NULL OR u.address LIKE CONCAT('%', :address, '%')")
    public List<UserVo> listUserByFilters(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("email") String email,
            @Param("address") String address
    );
}
