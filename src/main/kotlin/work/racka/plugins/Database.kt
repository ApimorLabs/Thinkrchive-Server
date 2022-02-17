package work.racka.plugins

import io.ktor.application.*
import org.koin.ktor.ext.inject
import work.racka.data.database.LaptopDatabase

fun Application.connectDatabase() {
    val db by inject<LaptopDatabase>()
    db.connect()
}