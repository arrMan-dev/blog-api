package com.arrisdev.blogspringbootproject.security;

import com.arrisdev.blogspringbootproject.exceptions.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiration;

    //method to generate token

    public String generateToken(Authentication authentication){
        String usernane = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpiration);

        String token = Jwts.builder()
                .setSubject(usernane)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    //get username from the token

    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //method to validate jwt token

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims String is empty.");
        }
    }
}
