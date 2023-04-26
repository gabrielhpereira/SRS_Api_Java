package com.api.srs.security.repository.user;

import com.api.srs.security.dto.user.UserDto;
import com.api.srs.security.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    String SELECT = "SELECT new com.api.srs.security.dto.user.UserDto(u.id, u.firstname, u.lastname, u.email) FROM UserEntity u";

    @Query(SELECT)
    public List<UserDto> listAllUsers();

    @Query(SELECT
            + " WHERE 1 = 1"
            + "     AND (:name IS NULL OR UPPER(u.firstname + ' ' +u.lastname) LIKE CONCAT('%', :name, '%'))"
            + "     AND (:email IS NULL OR u.email LIKE CONCAT('%', :email, '%'))")
    public List<UserDto> listUserByFilters(
            @Param("name") String name,
            @Param("email") String email
    );

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    public Optional<UserEntity> findByEmail(@Param("email") String email);
}
