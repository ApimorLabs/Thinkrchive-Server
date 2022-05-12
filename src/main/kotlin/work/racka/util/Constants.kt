package work.racka.util

object Constants {
    // DataBase
    const val DATABASE_NAME = "thinkpad_db"

    // Errors
    const val ERROR_MISSING_FIELDS = "Missing Some Fields"
    const val ERROR_GENERIC = "Some Problem Occurred"
    const val ERROR_BAD_EMAIL = "Wrong e-mail id!"
    const val ERROR_WRONG_ACCOUNT = "Wrong Account. Please Logout!"
    const val ERROR_BAD_PASSWORD = "Password Incorrect!"
    const val ERROR_PARAMETER_MODEL_NOT_PRESENT = "QueryParameter: model is not present"
    const val ERROR_PARAMETER_REG_KEY_NOT_PRESENT = "QueryParameter: regKey is not present"
    const val ERROR_INVALID_ADMIN_KEY = "Invalid Admin Key!"
    const val ERROR_LAPTOP_NOT_FOUND = "Laptop Model Not Found"

    // Success
    const val SUCCESS_LAPTOP_ADDED = "Laptop Added Successfully"
    const val SUCCESS_LAPTOP_UPDATED = "Laptop Updated Successfully"
    const val SUCCESS_LAPTOP_DELETED = "Laptop Deleted Successfully"
    const val SUCCESS_ADMIN_DELETED = "Admin Deleted Successfully"
    const val SUCCESS_ALL_LAPTOPS_DELETED = "All Laptops Deleted Successfully"

    // Other
    const val EXPIRES_IN_YEAR = 365L * 1000L * 60L * 60L * 24L
}