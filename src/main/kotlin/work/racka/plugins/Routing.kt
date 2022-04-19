package work.racka.plugins

import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import work.racka.di.inject
import work.racka.routes.Routes

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {
    install(Locations)

    val allRoutes by inject<Routes.AllRoutes>()

    routing {

        allRoutes.run {
            adminRoutes.run { routes() }
            laptopRoutes.run { routes() }
        }

        get("/") {
            call.respondText("Hello There! Welcome to Thinkrchive API Server.")
        }

        /**
        get<MyLocation> {
        call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
        }
        // Register nested routes
        get<Type.Edit> {
        call.respondText("Inside $it")
        }
        get<Type.List> {
        call.respondText("Inside $it")
        }
         **/
    }
}

/**
@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")
@Location("/type/{name}")
data class Type(val name: String) {
@Location("/edit")
data class Edit(val type: Type)

@Location("/list/{page}")
data class List(val type: Type, val page: Int)
}
 **/
