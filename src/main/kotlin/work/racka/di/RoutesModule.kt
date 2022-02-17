package work.racka.di

import org.koin.dsl.module
import work.racka.authentication.Auth
import work.racka.routes.AdminRoutes
import work.racka.routes.LaptopRoutes
import work.racka.routes.Routes

val routesModule = module {
    single {
        AdminRoutes(
            dbRepo = get(),
            jwtService = get(),
            hashFunction = { pass -> Auth.hash(pass) }
        )
    }

    single { LaptopRoutes(dbRepo = get()) }

    single {
        Routes.AllRoutes(
            adminRoutes = get(),
            laptopRoutes = get()
        )
    }
}