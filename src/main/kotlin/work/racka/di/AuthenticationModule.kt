package work.racka.di

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import org.koin.dsl.module
import work.racka.authentication.Auth
import work.racka.authentication.JwtService

val authenticationModule = module {
    single<JWTVerifier> {
        JWT.require(Auth.algorithm)
            .withIssuer(Auth.ISSUER)
            .build()
    }

    single { JwtService(get()) }
}