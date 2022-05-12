package work.racka.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import work.racka.authentication.Auth
import work.racka.authentication.JwtService
import work.racka.data.model.Admin
import work.racka.repository.Repository

fun Application.configureSecurity() {

    val jwtService by inject<JwtService>()
    val db by inject<Repository>()
    val hashFunction = { s: String -> Auth.hash(s) }
    val appEnvironment = environment

    authentication {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = appEnvironment.config.property("jwt.realm").getString()
            validate { jwtCredential ->
                val payload = jwtCredential.payload
                val email = payload.getClaim("email").asString()
                val admin = db.findAdmin(email)
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
                val admin = Admin(
                    email = email!!,
                    username = username!!,
                    hashPassword = hashFunction(password!!)
                )
                call.respond("You token is ${jwtService.generateToken(admin)}")
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
