package work.racka.plugins

import io.ktor.server.application.*
import work.racka.data.database.LaptopDatabase
import work.racka.di.inject

fun Application.connectDatabase() {
    val db by inject<LaptopDatabase>()
    db.connect()
}