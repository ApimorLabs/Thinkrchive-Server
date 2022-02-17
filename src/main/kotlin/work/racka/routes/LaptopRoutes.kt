package work.racka.routes

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import work.racka.data.model.Laptop
import work.racka.data.model.Response
import work.racka.repository.Repository
import work.racka.util.Constants

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
                    call.receive<Laptop>()
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
                    call.receive<Laptop>()
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
                    dbRepo.deleteLaptop(laptopModel)
                    call.respond(
                        HttpStatusCode.OK,
                        Response(true, Constants.SUCCESS_LAPTOP_DELETED)
                    )
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
}