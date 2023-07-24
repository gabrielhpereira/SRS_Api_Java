package com.api.srs.security.service.auth;

import com.api.srs.security.entity.token.TokenEntity;
import com.api.srs.security.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenService tokenService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

    final String jwtToken = authHeader.substring(7);

    TokenEntity storedToken = this.tokenService.findByToken(jwtToken).orElse(null);

    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);

      this.tokenService.saveToken(storedToken);
      SecurityContextHolder.clearContext();
    }
  }
}
