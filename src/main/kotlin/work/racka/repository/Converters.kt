package work.racka.repository

import org.jetbrains.exposed.sql.ResultRow
import work.racka.data.model.Admin
import work.racka.data.table.AdminTable

object Converters {
    fun rowToAdmin(row: ResultRow?): Admin? {
        row?.let {
            return Admin(
                email = row[AdminTable.email],
                username = row[AdminTable.username],
                hashPassword = row[AdminTable.hashPassword]
            )
        }
        return null
    }
}