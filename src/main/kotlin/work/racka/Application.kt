package work.racka

import io.ktor.application.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.modules
import work.racka.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(Koin) {
        modules()
    }

    connectDatabase()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
}
