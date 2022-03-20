package work.racka.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val success: Boolean,
    val message: String
)
