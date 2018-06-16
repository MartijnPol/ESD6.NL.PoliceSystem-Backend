package utils;

import io.jsonwebtoken.*;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/
public class TokenHelper {

    static String key = "b858b430e82c39965277796185c4272398ffd1f47cbdd12a398f4d91c9ee90cf";

    public static String EncodeToken(String username) {
        return Jwts.builder().setSubject("login").signWith(SignatureAlgorithm.HS512, key).claim("username", username).compact();
    }

    public static Boolean CheckIfTokenIsTrusted(String jwsToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken).getBody();
            if (claims.getSubject().equals("login")) {
                return true;
            }
            return false;

        } catch (SignatureException | MalformedJwtException e) {
            return false;
        }
    }

    public static String GetUsernameFromToken(String jwsToken) {

        if (CheckIfTokenIsTrusted(jwsToken)) {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken).getBody();
            return (String) claims.get("username");
        }
        return null;
    }

}
