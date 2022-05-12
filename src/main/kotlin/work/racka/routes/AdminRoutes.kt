package work.racka.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.locations.post
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import work.racka.authentication.Auth
import work.racka.authentication.admin.AdminConfig
import work.racka.authentication.hashing.HashingService
import work.racka.authentication.hashing.SaltedHash
import work.racka.authentication.token.TokenClaim
import work.racka.authentication.token.TokenClaimNames
import work.racka.authentication.token.TokenService
import work.racka.data.model.Admin
import work.racka.data.model.request.LoginRequest
import work.racka.data.model.request.RegisterRequest
import work.racka.data.model.response.Response
import work.racka.repository.Repository
import work.racka.util.Constants

@OptIn(KtorExperimentalLocationsAPI::class)
class AdminRoutes(
    private val dbRepo: Repository,
    private val hashService: HashingService,
    private val tokenService: TokenService
) {
    fun Route.routes() {
        val appEnvironment = environment

        // Register Admin
        post<AdminRegisterRoute> {
            val registerRequest = try {
                call.receive<RegisterRequest>()
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Response(false, Constants.ERROR_MISSING_FIELDS)
                )
                return@post
            }

            try {
                val adminRegKey: String = System.getenv(Auth.ADMIN_REG_KEY)
                if (registerRequest.regKey == adminRegKey) {
                    val saltedHash = hashService.generateSaltedHash(registerRequest.password)
                    val admin = Admin(
                        email = registerRequest.email,
                        username = registerRequest.username,
                        hashPassword = saltedHash.hash,
                        salt = saltedHash.salt
                    )
                    dbRepo.addAdmin(admin)
                    val config = AdminConfig.tokenConfig(appEnvironment!!)
                    val emailClaim = TokenClaim(
                        name = TokenClaimNames.EMAIL,
                        value = admin.email
                    )
                    call.respond(
                        HttpStatusCode.OK,
                        Response(true, tokenService.generate(config, emailClaim))
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_INVALID_ADMIN_KEY)
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict,
                    Response(false, e.message ?: Constants.ERROR_GENERIC)
                )
            }
        }

        // Login admin
        post<AdminLoginRoute> {
            val loginRequest = try {
                call.receive<LoginRequest>()
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    Response(false, Constants.ERROR_MISSING_FIELDS)
                )
                return@post
            }

            try {
                val admin = dbRepo.findAdmin(loginRequest.email)
                if (admin == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_BAD_EMAIL)
                    )
                } else {
                    val saltedHash = SaltedHash(
                        hash = admin.hashPassword,
                        salt = admin.salt
                    )
                    if (hashService.verify(password = loginRequest.password, saltedHash)) {
                        val config = AdminConfig.tokenConfig(appEnvironment!!)
                        val emailClaim = TokenClaim(
                            name = TokenClaimNames.EMAIL,
                            value = admin.email
                        )
                        call.respond(
                            HttpStatusCode.OK,
                            Response(true, tokenService.generate(config, emailClaim))
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            Response(false, Constants.ERROR_BAD_PASSWORD)
                        )
                    }
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.Conflict,
                    Response(false, e.message ?: Constants.ERROR_GENERIC)
                )
            }
        }

        // Delete Admin
        authenticate("jwt") {
            delete<AdminDeleteRoute> {
                val deleteRequest = try {
                    call.receive<LoginRequest>()
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_MISSING_FIELDS)
                    )
                    return@delete
                }

                try {
                    val admin = dbRepo.findAdmin(deleteRequest.email)
                    if (admin == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            Response(false, Constants.ERROR_BAD_EMAIL)
                        )
                    } else {
                        val saltedHash = SaltedHash(
                            hash = admin.hashPassword,
                            salt = admin.salt
                        )
                        if (hashService.verify(deleteRequest.password, saltedHash)) {
                            dbRepo.deleteAdmin(admin.email)
                            call.respond(
                                HttpStatusCode.OK,
                                Response(true, Constants.SUCCESS_ADMIN_DELETED)
                            )
                        } else {
                            call.respond(
                                HttpStatusCode.BadRequest,
                                Response(false, Constants.ERROR_BAD_PASSWORD)
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(
                        HttpStatusCode.Conflict,
                        Response(false, e.message ?: Constants.ERROR_GENERIC)
                    )
                }
            }
        }
    }

    @Location(Routes.LOGIN_REQUEST)
    class AdminLoginRoute

    @Location(Routes.REGISTER_REQUEST)
    class AdminRegisterRoute

    @Location(Routes.DELETE_ADMIN)
    class AdminDeleteRoute
}