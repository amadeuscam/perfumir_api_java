package com.amadeuscam.perfumir_api.services.impl;

import com.amadeuscam.perfumir_api.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey; // Secret key for JWT signing.
    
    @Value("${jwt.time.token}")
    private Long jwtTimeToken; // Expiration time (in milliseconds) of the tokens.

    @Value("${jwt.time.refresh.token}")
    private Long jwtTimeRefreshToken; // Expiration time (in milliseconds) of refresh tokens.

    /**
     * Generate a new JWT token.
     *
     * @param userDetails The user details for which to generate the token.
     * @return A string representing the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTimeToken))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generate a new refresh token.
     *
     * @param extraClaims Extra claims to be added to the refresh token.
     * @param userDetails The user details for which to generate the refresh token.
     * @return A string representing the generated JWT refresh token.
     */
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTimeRefreshToken))
                .signWith(getSiginKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token The JWT token.
     * @return An instance of Claims containing all the claims in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Retrieves the signing key used for JWT signature from the secret key property.
     *
     * @return The key used for JWT signature.
     */
    private Key getSiginKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Validates whether a given token is valid and belongs to the specified user.
     *
     * @param token The JWT token to validate.
     * @param userDetails The user details for which the token should be valid.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks whether a JWT token has expired.
     *
     * @param token The JWT token to check for expiration.
     * @return true if the token has expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}