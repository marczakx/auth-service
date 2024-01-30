package marczakx.auth.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import marczakx.auth.model.LoginRequest;
import marczakx.auth.model.LoginResponse;

@Service
public class AuthService {

  @Value("#{environment.JWT_SECRET}")
  private String jwtSecret;

  @Value("${jwt.expiration.ms}")
  private long jwtExpirationMs;

  public LoginResponse login(LoginRequest loginRequest) {
    return new LoginResponse(generateTokenFromUsername(loginRequest.user()));
  }

  private String generateTokenFromUsername(String username) {
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(key())
      .compact();
  }

  private SecretKey key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public void validateToken(String token) {
    extractAllClaims(token);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

}
