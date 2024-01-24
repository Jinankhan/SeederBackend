package com.seeder.apigateway.service;

import com.seeder.apigateway.exception.AccessDeniedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  private final long jwtExpiration;

  public JwtService(
    @Value("${application.security.jwt.expiration}") long jwtExpiration
  ) {
    this.jwtExpiration = jwtExpiration;
  }

  public String getEmailFromJwtToken(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public void validateToken(final String token) throws AccessDeniedException {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    } catch (SignatureException ex) {
      throw new AccessDeniedException("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      throw new AccessDeniedException("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      throw new AccessDeniedException("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      throw new AccessDeniedException("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      throw new AccessDeniedException("JWT claims string is empty.");
    }
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    UserDetails userDetails
  ) {
    return buildToken(userDetails, jwtExpiration);
  }

  private String buildToken(UserDetails userDetails, long jwtExpiration) {
    return Jwts
      .builder()
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }
}
