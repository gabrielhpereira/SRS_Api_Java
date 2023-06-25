package com.api.srs.security.entity.token;

import com.api.srs.security.entity.user.UserEntity;
import com.api.srs.security.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TokenEntity {

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(unique = true)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column
  private TokenType tokenType;

  @Column
  private boolean revoked;

  @Column
  private boolean expired;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
