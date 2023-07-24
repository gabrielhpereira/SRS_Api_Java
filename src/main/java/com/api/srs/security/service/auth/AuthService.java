package com.api.srs.security.service.auth;

import com.api.srs.security.dto.token.TokenDto;
import com.api.srs.security.dto.user.UserDto;
import com.api.srs.security.entity.token.TokenEntity;
import com.api.srs.security.entity.user.UserEntity;
import com.api.srs.security.enums.RoleType;
import com.api.srs.security.enums.TokenType;
import com.api.srs.security.service.token.TokenService;
import com.api.srs.security.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public TokenDto registerNewUser(UserDto userDto) {
    UserEntity user = UserEntity
        .builder()
        .firstname(userDto.firstname())
        .lastname(userDto.lastname())
        .email(userDto.email())
        .password(passwordEncoder.encode(userDto.password()))
        .roleType(RoleType.USER)
        .build();

    UserEntity savedUser = this.userService.saveUser(user);
    String jwtToken = this.jwtService.generatedToken(user);
    String refreshToken = this.jwtService.generatedRefreshToken(user);

    this.saveUserToken(savedUser, jwtToken);

    return new TokenDto(jwtToken, refreshToken);
  }

  public TokenDto authenticate(UserDto userDto) {
    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.email(), userDto.password()));

    UserEntity user = this.userService.findByEmail(userDto.email()).orElseThrow();

    String jwtToken = this.jwtService.generatedToken(user);
    String refreshToken = this.jwtService.generatedRefreshToken(user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return new TokenDto(jwtToken, refreshToken);
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

    final String refreshToken = authHeader.substring(7);
    final String email = this.jwtService.extractUsername(refreshToken);

    if (email != null) {
      UserEntity user = this.userService.findByEmail(email).orElseThrow();

      if (this.jwtService.isTokenValid(refreshToken, user)) {
        String accessToken = this.jwtService.generatedToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), tokenDto);
      }
    }
  }

  private void saveUserToken(UserEntity user, String jwtToken) {
    TokenEntity token = TokenEntity
        .builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();

    this.tokenService.saveToken(token);
  }

  private void revokeAllUserTokens(UserEntity user) {
    List<TokenEntity> validUserTokens = this.tokenService.findAllValidTokenByUser(user.getId());

    if (validUserTokens.isEmpty()) return;

    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });

    this.tokenService.saveAllTokens(validUserTokens);
  }
}