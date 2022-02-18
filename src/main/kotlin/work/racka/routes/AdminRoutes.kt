package work.racka.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import work.racka.authentication.Auth
import work.racka.authentication.JwtService
import work.racka.data.model.Admin
import work.racka.data.model.Response
import work.racka.data.model.request.LoginRequest
import work.racka.data.model.request.RegisterRequest
import work.racka.repository.Repository
import work.racka.util.Constants

class AdminRoutes(
    private val dbRepo: Repository,
    private val jwtService: JwtService,
    private val hashFunction: (String) -> String
) {
    fun Route.routes() {
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
                    val admin = Admin(
                        email = registerRequest.email,
                        username = registerRequest.username,
                        hashPassword = hashFunction(registerRequest.password)
                    )
                    dbRepo.addAdmin(admin)
                    call.respond(
                        HttpStatusCode.OK,
                        Response(true, jwtService.generateToken(admin))
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
                val admin = dbRepo.findAdmin(loginRequest.identifier)
                if (admin == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_BAD_EMAIL)
                    )
                } else {
                    if (admin.hashPassword == hashFunction(loginRequest.password)) {
                        call.respond(
                            HttpStatusCode.OK,
                            Response(true, jwtService.generateToken(admin))
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
                    val admin = dbRepo.findAdmin(deleteRequest.identifier)
                    if (admin == null) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            Response(false, Constants.ERROR_BAD_EMAIL)
                        )
                    } else {
                        if (admin.hashPassword == hashFunction(deleteRequest.password)) {
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