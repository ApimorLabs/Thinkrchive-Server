package work.racka.data.table

import org.jetbrains.exposed.sql.Table

object AdminTable: Table() {
    val email = varchar("email", 256)
    val username = varchar("username", 256)
    val hashPassword = varchar("hashPassword", 256)

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(email)
}