package work.racka.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.koin.dsl.module
import java.net.URI

val mainModule = module {
    single {
        val config = HikariConfig()
        config.apply {
            val uri = URI(System.getenv("ONLINE_DATABASE_URL"))
            val userInfo = uri.userInfo.split(":").toTypedArray()
            val username = userInfo[0]
            val password = userInfo[1]

            driverClassName = System.getenv("JDBC_DRIVER")
            //jdbcUrl = System.getenv("DATABASE_URL") For Local Use
            jdbcUrl =
                "jdbc:postgresql://${uri.host}:${uri.port}${uri.path}?sslmode=require&user=$username&password=$password"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        HikariDataSource(config)
    }
}