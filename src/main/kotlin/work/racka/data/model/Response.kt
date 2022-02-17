package work.racka.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val success: Boolean,
    val message: String
)
