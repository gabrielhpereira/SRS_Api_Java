package com.api.srs.security.repository.token;

import com.api.srs.security.entity.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

  @Query("SELECT t FROM TokenEntity t"
      + " INNER JOIN UserEntity u"
      + "     ON t.user.id = u.id"
      + " WHERE u.id = :userId"
      + "     AND (t.expired = false OR t.revoked = false)")
  public List<TokenEntity> findAllValidTokenByUser(@Param("userId") Integer userId);

  @Query("SELECT t FROM TokenEntity t WHERE t.token = :token")
  public Optional<TokenEntity> findByToken(@Param("token") String token);
}
