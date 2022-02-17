package work.racka.di

import org.koin.dsl.module
import work.racka.authentication.Auth
import work.racka.routes.AdminRoutes

val routesModule = module {
    single {
        AdminRoutes(
            dbRepo = get(),
            jwtService = get(),
            hashFunction = { pass -> Auth.hash(pass) }
        )
    }

    single {  }
}