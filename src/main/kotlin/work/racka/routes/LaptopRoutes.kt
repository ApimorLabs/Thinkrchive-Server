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
import work.racka.data.model.Laptop
import work.racka.data.model.request.LaptopRequest
import work.racka.data.model.response.Response
import work.racka.repository.Repository
import work.racka.util.Constants

@OptIn(KtorExperimentalLocationsAPI::class)
class LaptopRoutes(
    private val dbRepo: Repository
) {

    fun Route.routes() {
        get<AllLaptopsGetRoute> {
            try {
                val laptops = dbRepo.getAllLaptops()
                call.respond(HttpStatusCode.OK, laptops)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(
                    HttpStatusCode.Conflict,
                    emptyList<Laptop>()
                )
            }
        }

        authenticate("jwt") {
            post<LaptopAddRoute> {
                val laptop = try {
                    call.receive<LaptopRequest>()
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_MISSING_FIELDS)
                    )
                    return@post
                }

                try {
                    dbRepo.addLaptop(laptop)
                    call.respond(HttpStatusCode.OK, Constants.SUCCESS_LAPTOP_ADDED)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.Conflict,
                        Response(false, e.message ?: Constants.ERROR_GENERIC)
                    )
                }
            }

            post<LaptopUpdateRoute> {
                val laptop = try {
                    call.receive<LaptopRequest>()
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_MISSING_FIELDS)
                    )
                    return@post
                }

                try {
                    dbRepo.updateLaptop(laptop)
                    call.respond(HttpStatusCode.OK, Constants.SUCCESS_LAPTOP_UPDATED)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.Conflict,
                        Response(false, e.message ?: Constants.ERROR_GENERIC)
                    )
                }
            }

            delete<LaptopDeleteRoute> {
                val laptopModel = try {
                    call.parameters["model"]!!
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_PARAMETER_MODEL_NOT_PRESENT)
                    )
                    return@delete
                }

                try {
                    val laptop = dbRepo.getLaptop(laptopModel)
                    if (laptop != null) {
                        dbRepo.deleteLaptop(laptopModel)
                        call.respond(
                            HttpStatusCode.OK,
                            Response(true, Constants.SUCCESS_LAPTOP_DELETED)
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            Response(false, Constants.ERROR_LAPTOP_NOT_FOUND)
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(
                        HttpStatusCode.Conflict,
                        Response(false, e.message ?: Constants.ERROR_GENERIC)
                    )
                }
            }

            delete<LaptopDeleteAllRoute> {
                val regKey = try {
                    call.parameters["regKey"]!!
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        Response(false, Constants.ERROR_PARAMETER_REG_KEY_NOT_PRESENT)
                    )
                    return@delete
                }
                try {
                    val adminRegKey: String = System.getenv(Auth.ADMIN_REG_KEY)
                    if (regKey == adminRegKey) {
                        dbRepo.deleteAllLaptops()
                        call.respond(
                            HttpStatusCode.OK,
                            Response(true, Constants.SUCCESS_ALL_LAPTOPS_DELETED)
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
        }
    }

    @Location(Routes.ALL_LAPTOPS)
    class AllLaptopsGetRoute

    @Location(Routes.ADD_LAPTOP)
    class LaptopAddRoute

    @Location(Routes.UPDATE_LAPTOP)
    class LaptopUpdateRoute

    @Location(Routes.DELETE_LAPTOP)
    class LaptopDeleteRoute

    @Location(Routes.DELETE_ALL_LAPTOPS)
    class LaptopDeleteAllRoute
}