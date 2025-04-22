package com.example.granja_gaia.security;

import com.example.granja_gaia.dtos.TokenDataDTO;
import com.example.granja_gaia.modelos.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "375ec1bfd98afb0e55aa10907716db40941a160687ba7dd09bdc3ec9a39206dd";
    private static final long EXPIRATION_TIME = 3600000; // 1 hora

    /**
     * Genera un token JWT con los datos del usuario
     */
    public String generateToken(Usuario usuario) {
        TokenDataDTO tokenDataDTO = TokenDataDTO
                .builder()
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .fecha_creacion(System.currentTimeMillis())
                .fecha_expiracion(System.currentTimeMillis() + EXPIRATION_TIME)
                .build();

        return Jwts
                .builder()
                .claim("tokenDataDTO", tokenDataDTO)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el nickname del usuario desde el token
     */
    public String extractNickname(String token) {
        return extractClaim(token, claims -> {
            Map<String, Object> tokenData = (LinkedHashMap<String, Object>) claims.get("tokenDataDTO");
            return (String) tokenData.get("username");
        });
    }

    /**
     * Verifica si el token es válido comparando con UserDetails
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractNickname(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extrae todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrae un claim específico del token
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Verifica si el token ha expirado
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, claims -> {
            Map<String, Object> tokenData = (LinkedHashMap<String, Object>) claims.get("tokenDataDTO");
            return new Date((Long) tokenData.get("fecha_expiracion"));
        });
    }

    /**
     * Extrae los datos completos del token
     */
    public TokenDataDTO extractTokenData(String token) {
        Claims claims = extractAllClaims(token);
        Map<String, Object> mapa = (LinkedHashMap<String, Object>) claims.get("tokenDataDTO");
        return TokenDataDTO.builder()
                .username((String) mapa.get("username"))
                .fecha_creacion((Long) mapa.get("fecha_creacion"))
                .fecha_expiracion((Long) mapa.get("fecha_expiracion"))
                .rol((String) mapa.get("rol"))
                .build();
    }

    /**
     * Obtiene la clave de firma
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}