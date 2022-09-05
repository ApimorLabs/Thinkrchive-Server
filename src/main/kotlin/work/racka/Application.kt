package work.racka

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import work.racka.di.authenticationModule
import work.racka.di.databaseModule
import work.racka.di.routesModule
import work.racka.plugins.configureMonitoring
import work.racka.plugins.configureRouting
import work.racka.plugins.configureSecurity
import work.racka.plugins.configureSerialization

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(Koin) {
        modules(databaseModule, authenticationModule, routesModule)
    }
    //connectDatabase()
    configureSecurity()
    configureRouting()
    configureSerialization()
    configureMonitoring()
}
