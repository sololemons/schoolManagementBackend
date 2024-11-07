package com.SCHMA.SchoolManagement.SECURITY.CONFIG;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// This Is The Class That Will Manipulate The JWT token To Extract The User Enmail
@Service
public class JwtService {
    private static final String SECRET_KEYS = "jnPEtFZQZEF3y+2x1oObCKn3gcTz4D6UPgsxIi9mi7owlCei39IrlR0Ju5N/y+GTox+M9reCChMsTVszs0aQXrvjohLA2YFwi1SKoQ3eeXZM1n0aEKXMXegD+/KnIJ1IpFp/0x8qqjycRmFmiq2+V7ogUpo+y88h/q/8kXqSe6j7lVg6KpMOw8Mpnp5b8mU4VAipAFr9CWC8+lObSa/+JQenBiZ16aRkYCVo0ujkfg8fdWLQ4v2mNTzuj5zbBhmWZCT4GG9fsjzjRMW6PvveIZ+wOBofKaym3lOwuQd0xdEIO6NZR4H9XJinP6OFGMens+pWbTH9Of2SQorofEQe88c5by9hZUWtIIrSW8/aY/c=\n";
    // This Will Return The Username Which has Been Extracted From The JWT token
    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    //Extract A single Claim That We May Pass in the payload
    public <T> T extractClaim(String token, Function<Claims,T > claimResolver){
        final Claims claims = extractAllClaims(token);
        return  claimResolver.apply(claims);
    }
    // A simplified way of Generating a Token Where You Dont Need Additional Claims In The Payload
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    // Extract All Claims
    private Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEYS);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    // Generating A token And Setting The Subject As the Username
// Also Checks wHen The Token Should Expire
    public String generateToken(
            Map <String,Object> extractClaim,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    //validate Token
    public boolean IstokenValid(String token,UserDetails userDetails){

        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



}