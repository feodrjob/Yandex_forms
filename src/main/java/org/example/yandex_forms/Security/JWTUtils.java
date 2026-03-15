package org.example.yandex_forms.Security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// Генерируем токен, извлеакем имя из токена
@Component // Делаем бином чтобы внедрять другие компоненты
public class JWTUtils {
    //Секретный ключ для подписи токена
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    //Срок действия токена в милисекундах
    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;
    //Метод для создания ключа подписи из секретной строки
    private Key key(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()) //Время выпуска
                .setExpiration(new Date ((new Date()).getTime() + jwtExpirationMs)) //Время истечения
                .signWith(key(), SignatureAlgorithm.HS256) // Подписываем ключем
                .compact();
    }
    //Извлчение юзернейм из токена
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try
        {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e)
        {
            return false;
        }
    }

}

