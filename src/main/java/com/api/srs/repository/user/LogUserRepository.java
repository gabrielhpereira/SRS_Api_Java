package com.api.srs.repository.user;

import com.api.srs.entity.user.LogUserEntity;
import com.api.srs.vo.user.LogUserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogUserRepository extends JpaRepository<LogUserEntity, Integer> {

    @Query("SELECT new com.api.srs.vo.user.LogUserVo(l.id, l.description, l.date, l.userId)"
            + "     FROM LogUserEntity l"
            + " WHERE l.userId = :userId")
    public List<LogUserVo> listAllLogUser(
            @Param("userId") Integer userId
    );
}