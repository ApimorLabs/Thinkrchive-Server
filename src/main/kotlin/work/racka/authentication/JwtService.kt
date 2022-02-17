package work.racka.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import work.racka.data.model.Admin

class JwtService(val verifier: JWTVerifier) {
    fun generateToken(admin: Admin): String =
        JWT.create()
            .withSubject(Auth.SUBJECT)
            .withIssuer(Auth.ISSUER)
            .withClaim("email", admin.email)
            .sign(Auth.algorithm)
}