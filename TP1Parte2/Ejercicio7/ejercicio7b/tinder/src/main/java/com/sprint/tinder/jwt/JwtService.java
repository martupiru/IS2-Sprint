package com.sprint.tinder.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "ASBSABDASNDKASFALKFNALFALJD2312312541341ASDASF67564";

    public String getToken(UserDetails user){
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user){
        return Jwts
                .builder()
                .claims(extraClaims)  // Cambio: .claims() en lugar de .setClaims()
                .subject(user.getUsername())  // Cambio: .subject() en lugar de .setSubject()
                .issuedAt(new Date(System.currentTimeMillis()))  // Cambio: .issuedAt()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // Cambio: .expiration()
                .signWith(getKey())  // Cambio: solo getKey(), el algoritmo se detecta automáticamente
                .compact();
    }

    private SecretKey getKey(){  // Cambio: SecretKey en lugar de Key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // La del video estaba deprecada, esta es la nueva para versiones posteriores a la 0.11
    private Claims getAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}