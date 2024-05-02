//package com.walkerholic.walkingpet.domain.auth.util;
//
//import com.walkerholic.walkingpet.domain.users.dto.UsersDto;
//import com.walkerholic.walkingpet.domain.users.repository.UsersRepository;
//import com.walkerholic.walkingpet.global.error.GlobalBaseException;
//import com.walkerholic.walkingpet.global.error.GlobalErrorCode;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.Claims;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JwtUtil {
//    private final UsersRepository usersRepository;
//
//    @Autowired
//    public JwtUtil(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }
//
//    @Value("${jwt.secret-key}")
//    private String SECRETKEY;
//
//    @Value("${jwt.refresh-token-expiration-time}")
//    private Long REFRESH_TOKEN_EXPIRATION;
//    @Value("${jwt.access-token-expiration-time}")
//    private Long ACCESS_TOKEN_EXPIRATION;
//
//    private Long ACCESS_TOKEN_EXPIRATION_PERIOD = 600000L;
//
//
//    private Long REFRESH_TOKEN_EXPIRATION_PERIOD = 3600000L;
//
//
//    public String generateAccessToken(UsersDto userDTO) {
//        return createToken(ACCESS_TOKEN_EXPIRATION_PERIOD, userDTO);
//    }
//
//
//    public String generateRefreshToken(UsersDto userDTO) {
//        return createToken(REFRESH_TOKEN_EXPIRATION_PERIOD, userDTO);
//    }
//
//
//    private String createToken(Long expirationPeriod, UsersDto userDTO) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", userDTO.getUserId());
////        claims.put("iss", userDTO.getSocialProvider());
//
//        return Jwts.builder()
//                .addClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expirationPeriod))
//                .signWith(SignatureAlgorithm.HS256, SECRETKEY)
//                .compact();
//    }
//
//
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
//        return claimsResolver.apply(claims);
//    }
//
//
//
//    public boolean validateAccessToken(String accessToken) {
//        if(accessToken == null || accessToken.length() <= 0) {
//            throw new GlobalBaseException(GlobalErrorCode.ACCESS_TOKEN_NOT_FOUND);
//        }
//
//        boolean isTokenExpired = checkTokenExpired(accessToken);
//        if(isTokenExpired == true) {
//            throw new GlobalBaseException(GlobalErrorCode.TOKEN_EXPIRED);
//        } else {
//            return isTokenExpired;
//        }
//    }
//
//
//    public Boolean validateRefreshToken(String refreshToken) {
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
//
//        return true;
//    }
//
//
//    public boolean checkTokenExpired(String token) {
//        Date expirationDate = extractClaim(token, Claims::getExpiration);
//        boolean isTokenExpired = expirationDate.after(new Date());
//        return isTokenExpired;
//    }
//
//
//    public Map<String, String> initToken(UserDTO savedOrFindUser) {
//        Map<String, String> tokenMap = new HashMap<>();
//        String accessToken = generateAccessToken(savedOrFindUser);
//        String refreshToken = generateRefreshToken(savedOrFindUser);
//
//        tokenMap.put("accessToken", accessToken);
//        tokenMap.put("refreshToken", refreshToken);
//
//        updRefreshTokenInDB(refreshToken, savedOrFindUser);
//
//        return tokenMap;
//    }
//
//
//    public Map<String, String> refreshingAccessToken(UsersDto userDTO, String refreshToken) {
//        Map<String, String> tokenMap = new HashMap<>();
//        String accessToken = generateAccessToken(userDTO);
//
//        tokenMap.put("accessToken", accessToken);
//        tokenMap.put("refreshToken", refreshToken);
//
//        return tokenMap;
//    }
//
//
//    private void updRefreshTokenInDB(String refreshToken, UsersDto savedOrFindUser) {
//        savedOrFindUser.setRefreshToken(refreshToken);
//        usersRepository.save(savedOrFindUser.toEntity());
//    }
//
//
//    public String extractTokenFromHeader(HttpServletRequest request) {
//        String header = request.getHeader("Authorization");
//        if(StringUtils.hasText(header) && header.startsWith("Bearer ")) {
//            return header.substring(7);
//        } else {
//            throw new TokenException(TokenErrorResult.ACCESS_TOKEN_NEED);
//        }
//    }
//}
