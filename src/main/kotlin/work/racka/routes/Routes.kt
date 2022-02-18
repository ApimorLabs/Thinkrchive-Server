package work.racka.routes

object Routes {
    const val API_VERSION = "/v1"
    const val ADMIN = "$API_VERSION/admins"
    const val REGISTER_REQUEST = "$ADMIN/register"
    const val LOGIN_REQUEST = "$ADMIN/login"
    const val DELETE_ADMIN = "$ADMIN/delete"
    const val ALL_LAPTOPS = "$API_VERSION/all-laptops"
    const val ADD_LAPTOP = "$ALL_LAPTOPS/add"
    const val UPDATE_LAPTOP = "$ALL_LAPTOPS/update"
    const val DELETE_LAPTOP = "$ALL_LAPTOPS/delete"
    const val DELETE_ALL_LAPTOPS = "$DELETE_LAPTOP/all"

    class AllRoutes(
        val adminRoutes: AdminRoutes,
        val laptopRoutes: LaptopRoutes
    )
}