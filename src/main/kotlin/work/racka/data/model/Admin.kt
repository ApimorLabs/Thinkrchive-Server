package work.racka.data.model

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class Admin(
    val _id: String,
    val email: String = _id,
    val username: String,
    val hashPassword: String,
    val salt: String
) : Principal
