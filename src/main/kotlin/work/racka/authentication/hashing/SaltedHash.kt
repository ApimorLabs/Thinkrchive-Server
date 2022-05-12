package work.racka.authentication.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
