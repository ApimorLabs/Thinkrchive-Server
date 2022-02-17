package work.racka.repository

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import work.racka.data.database.LaptopDatabase
import work.racka.data.model.Admin
import work.racka.data.table.AdminTable
import work.racka.repository.Converters.rowToAdmin

class RepositoryImpl(
    private val db: LaptopDatabase
) : Repository {
    override suspend fun addAdmin(admin: Admin) {
        db.query {
            AdminTable.insert { adminTable ->
                adminTable[email] = admin.email
                adminTable[username] = admin.username
                adminTable[hashPassword] = admin.hashPassword
            }
        }
    }

    override suspend fun findAdmin(identifier: String): Admin? =
        db.query {
            AdminTable.select {
                AdminTable.email.eq(identifier) or AdminTable.username.eq(identifier)
            }
                .map { rowToAdmin(it) }
                .singleOrNull()
        }
}