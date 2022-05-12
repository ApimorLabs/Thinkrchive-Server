package work.racka.authentication.admin

import io.ktor.server.application.*
import work.racka.authentication.Auth
import work.racka.authentication.token.TokenConfig
import work.racka.util.Constants

object AdminConfig {

    fun tokenConfig(env: ApplicationEnvironment): TokenConfig = TokenConfig(
        issuer = env.config.property("jwt.issuer").getString(),
        audience = env.config.property("jwt.audience").getString(),
        secret = System.getenv(Auth.JWT_SECRET_VARIABLE),
        expiresIn = Constants.EXPIRES_IN_YEAR
    )
}