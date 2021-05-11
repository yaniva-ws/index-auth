package com.wss.index.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private String secret;

    private long jwtSecondsTTL = 1000 * 60 * 5;// 5 min as default

    public JwtTokenUtil(String secret, long jwtSecondsTTL) {
        this.jwtSecondsTTL = jwtSecondsTTL;
        this.secret = secret;
    }

    public String generateToken() {
        return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtSecondsTTL))
                .signWith(fetchSigningKey(), SIGNATURE_ALGORITHM).compact();
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(fetchSigningKey()).build().parseClaimsJws(token).getBody();
        final Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Key fetchSigningKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secret), SIGNATURE_ALGORITHM.getJcaName());
    }
}
