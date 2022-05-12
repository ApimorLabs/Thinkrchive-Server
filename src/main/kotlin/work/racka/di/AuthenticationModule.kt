package work.racka.di

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import org.koin.dsl.module
import work.racka.authentication.Auth
import work.racka.authentication.JwtService
import work.racka.authentication.hashing.HashingService
import work.racka.authentication.hashing.SHA256HashingService
import work.racka.authentication.token.JwtTokenService
import work.racka.authentication.token.TokenService

val authenticationModule = module {
    single<JWTVerifier> {
        JWT.require(Auth.algorithm)
            .withIssuer(Auth.ISSUER)
            .build()
    }

    single { JwtService(get()) }

    single<TokenService> { JwtTokenService() }

    single<HashingService> { SHA256HashingService() }
}