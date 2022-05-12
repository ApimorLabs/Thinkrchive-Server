package work.racka.authentication.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import work.racka.authentication.Auth
import java.util.*

class JwtTokenService : TokenService {
    override fun generate(config: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withSubject(Auth.SUBJECT)
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
        claims.forEach { tokenClaim ->
            token = token.withClaim(tokenClaim.name, tokenClaim.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }
}