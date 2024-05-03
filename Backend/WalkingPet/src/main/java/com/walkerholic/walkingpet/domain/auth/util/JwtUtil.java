package com.walkerholic.walkingpet.domain.auth.util;

import com.walkerholic.walkingpet.domain.auth.error.TokenBaseException;
import com.walkerholic.walkingpet.domain.auth.error.TokenErrorCode;
import com.walkerholic.walkingpet.domain.users.dto.UsersDto;
import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final UsersRepository usersRepository;

    @Autowired
    public JwtUtil(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Value("${jwt.secret-key}")
    private String SECRETKEY;

    @Value("${jwt.refresh-token-expiration-time}")
    private Long REFRESH_TOKEN_EXPIRATION;
    @Value("${jwt.access-token-expiration-time}")
    private Long ACCESS_TOKEN_EXPIRATION;
    private Long ACCESS_TOKEN_EXPIRATION_PERIOD = 600000L;
    private Long REFRESH_TOKEN_EXPIRATION_PERIOD = 3600000L;
    
    public String generateAccessToken(UsersDto userDTO) {
        return createToken(ACCESS_TOKEN_EXPIRATION_PERIOD, userDTO);
    }

    public String generateRefreshToken(UsersDto userDTO) {
        return createToken(REFRESH_TOKEN_EXPIRATION_PERIOD, userDTO);
    }
    
    private String createToken(Long expirationPeriod, UsersDto userDTO) {

        System.out.println("토큰 생성: " + userDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "1");
        claims.put("iss", "test");

        System.out.println("만료 날짜: " + new Date(System.currentTimeMillis() + expirationPeriod));

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationPeriod))
                .signWith(SignatureAlgorithm.HS512, SECRETKEY)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
    }
    
    public boolean validateAccessToken(String accessToken) {
        if(accessToken == null || accessToken.length() <= 0) {
            throw new TokenBaseException(TokenErrorCode.ACCESS_TOKEN_NOT_FOUND);
        }

        boolean isTokenExpired = checkTokenExpired(accessToken);
        if(!isTokenExpired) {
            System.out.println("토큰 만료");
            throw new TokenBaseException(TokenErrorCode.TOKEN_EXPIRED);
        } else {
            return isTokenExpired;
        }
    }


    public Boolean validateRefreshToken(String refreshToken) {
        //TODO: refreshtoken 유효화 검사
        
//        User user = usersRepository.findByRefreshToken(refreshToken);
//
//        if(user == null) {
//            new GlobalBaseException(GlobalErrorCode.TOKEN_EXPIRED);
//        }
//
//        String refreshTokenInDB = user.getRefreshToken();
//        if(!refreshToken.equals(refreshTokenInDB) || checkTokenExpired(refreshTokenInDB)) {
//            new TokenException(TokenErrorResult.TOKEN_EXPIRED);
//        }

        return true;
    }
    
    public boolean checkTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
//        Claims claims = getClaims(token);
        boolean isTokenExpired = expirationDate.after(new Date());
        System.out.println("토큰 만료 시간: " + expirationDate);
        System.out.println("현재 시간: " + new Date());
        System.out.println("결과 : " + isTokenExpired);
        return isTokenExpired;
//        return true;
    }

    public Map<String, String> initToken(UsersDto savedOrFindUser) {
        Map<String, String> tokenMap = new HashMap<>();
        String accessToken = generateAccessToken(savedOrFindUser);
        String refreshToken = generateRefreshToken(savedOrFindUser);

        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        updRefreshTokenInDB(refreshToken, savedOrFindUser);

        return tokenMap;
    }

    public Map<String, String> refreshingAccessToken(UsersDto userDTO, String refreshToken) {
        Map<String, String> tokenMap = new HashMap<>();
        String accessToken = generateAccessToken(userDTO);

        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }

    private void updRefreshTokenInDB(String refreshToken, UsersDto savedOrFindUser) {
        //TODO: refreshtoken db 저장 시 필요
//        savedOrFindUser.setRefreshToken(refreshToken);
//        usersRepository.save(savedOrFindUser.toEntity());
    }


    public String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        } else {
            System.out.println("access token 없음");
            throw new TokenBaseException(TokenErrorCode.ACCESS_TOKEN_NOT_FOUND);
        }
    }
}
