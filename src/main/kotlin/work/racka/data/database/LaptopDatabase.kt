package work.racka.data.database

import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import work.racka.data.table.AdminTable
import work.racka.data.table.LaptopTable

class LaptopDatabase(
    private val hikariDataSource: HikariDataSource,
    private val dispatcher: CoroutineDispatcher
) {

    fun connect() {
        Database.connect(hikariDataSource)
        transaction {
            SchemaUtils.create(AdminTable)
            SchemaUtils.create(LaptopTable)
        }
    }

    suspend fun <T> query(block: () -> T): T =
        withContext(dispatcher) {
            transaction { block() }
        }
}