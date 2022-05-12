package work.racka.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import work.racka.authentication.admin.AdminConfig
import work.racka.authentication.hashing.HashingService
import work.racka.authentication.token.TokenClaim
import work.racka.authentication.token.TokenClaimNames
import work.racka.authentication.token.TokenService
import work.racka.data.model.Admin
import work.racka.repository.Repository

fun Application.configureSecurity() {

    val hashService: HashingService by inject()
    val tokenService: TokenService by inject()
    val repo: Repository by inject()
    val appEnvironment = environment

    authentication {
        jwt("jwt") {
            val adminTokenConfig = AdminConfig.tokenConfig(appEnvironment)
            verifier {
                JWT.require(Algorithm.HMAC256(adminTokenConfig.secret))
                    .withIssuer(adminTokenConfig.issuer)
                    .withAudience(adminTokenConfig.audience)
                    .build()
            }
            realm = appEnvironment.config.property("jwt.realm").getString()
            validate { jwtCredential ->
                val payload = jwtCredential.payload
                val email = payload.getClaim(TokenClaimNames.EMAIL).asString()
                val admin = repo.findAdmin(email)
                admin
            }
        }
    }

    routing {
        // Testing JWT Token generation
        get("/token-test") {
            val email = call.request.queryParameters["email"]
            val username = call.request.queryParameters["username"]
            val password = call.request.queryParameters["password"]

            try {
                val saltedHash = hashService.generateSaltedHash(password!!)
                val admin = Admin(
                    email = email!!,
                    username = username!!,
                    hashPassword = saltedHash.hash,
                    salt = saltedHash.salt
                )
                val tokenConfig = AdminConfig.tokenConfig(appEnvironment)
                val tokenClaim = TokenClaim(name = TokenClaimNames.EMAIL, value = admin.email)
                val token = tokenService.generate(tokenConfig, tokenClaim)
                call.respond("You token is $token and salt is ${saltedHash.salt}")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(
                    HttpStatusCode.BadRequest,
                    "One of the parameter(s) is null"
                )
            }
        }
    }

}
