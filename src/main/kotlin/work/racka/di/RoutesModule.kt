package work.racka.di

import org.koin.dsl.module
import work.racka.routes.AdminRoutes
import work.racka.routes.LaptopRoutes
import work.racka.routes.Routes

val routesModule = module {
    factory {
        AdminRoutes(
            dbRepo = get(),
            hashService = get(),
            tokenService = get()
        )
    }

    factory { LaptopRoutes(dbRepo = get()) }

    factory {
        Routes.AllRoutes(
            adminRoutes = get(),
            laptopRoutes = get()
        )
    }
}