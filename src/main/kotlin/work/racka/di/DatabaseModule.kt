package work.racka.di

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import work.racka.data.database.LaptopDatabase
import java.net.URI

val databaseModule = module {
    single {
        val config = HikariConfig()
        config.apply {
            driverClassName = System.getenv("JDBC_DRIVER")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            /**
             * This has been wrapped inside a try block so that we can test with
             * our local Postgres database using our local environment variable
             * Since the URLs don't match it will throw an exception on the local
             * machine and will use the local environment variable but will not throw
             * an exception in our remote server since the URL will be correct for try { }
             */
            jdbcUrl = try {
                val uri = URI(System.getenv("DATABASE_URL"))
                val userInfo = uri.userInfo.split(":").toTypedArray()
                val username = userInfo[0]
                val password = userInfo[1]
                "jdbc:postgresql://${uri.host}:${uri.port}${uri.path}?sslmode=require&user=$username&password=$password"
            } catch (e: Exception) {
                e.printStackTrace()
                System.getenv("DATABASE_URL")
            }

            validate()
        }
        HikariDataSource(config)
    }

    single {
        LaptopDatabase(
            hikariDataSource = get(),
            dispatcher = Dispatchers.IO
        )
    }

    single {  }
}