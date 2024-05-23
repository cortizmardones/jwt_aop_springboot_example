package com.example.demo.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.dto.ValidTokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

@Service
public class TokenServiceImpl implements TokenService {
	
    @Value("${jwt.secret}")
    private String secretKey;

	@Override
	public TokenResponseDTO getToken(String subject) {
		
		//Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave secreta aleatoria.
		byte[] secretBytes = Base64.getDecoder().decode(secretKey);
		Key key = Keys.hmacShaKeyFor(secretBytes);
		
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hora de expiración
        
		return TokenResponseDTO.builder()
				.subject(subject)
				.token(Jwts.builder()
						.setSubject(subject)
						.setIssuedAt(now)
						.setExpiration(expiryDate)
						.setHeaderParam("headerClaimPersonalizado", "header_1")
						.claim("bodyClaimPersonalizado", "body_1")
						.signWith(key)
						.compact())
				.build();
		
	}
	
    public ValidTokenResponse validToken(String token) {
        try {
    		byte[] secretBytes = Base64.getDecoder().decode(secretKey);
    		Key key = Keys.hmacShaKeyFor(secretBytes);
        	
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            
//            JwsHeader claimsHeader = claimsJws.getHeader();
//            System.out.println("headerClaimPersonalizado: " + claimsHeader.get("headerClaimPersonalizado"));
//            
//            Claims claimsBody = claimsJws.getBody();	
//            System.out.println("Subject: " + claimsBody.getSubject());
//            System.out.println("Expiration: " + claimsBody.getExpiration());
//            System.out.println("bodyClaimPersonalizado: " + claimsBody.get("bodyClaimPersonalizado"));
//            System.out.println();
            
            return ValidTokenResponse.builder().tokenType("JWT").state(true).build();
            
        } catch (SecurityException | IllegalArgumentException e) {
        	//System.out.println("Token Inválido: " + token + "\n");
        	return ValidTokenResponse.builder().tokenType("JWT").state(false).build();
        } catch (ExpiredJwtException e) {
        	//System.out.println("Token Expirado: " + token + "\n");
        	return ValidTokenResponse.builder().tokenType("JWT").state(false).build();
        }
    }
    

}
