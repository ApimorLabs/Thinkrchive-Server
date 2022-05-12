package work.racka.authentication.hashing

interface HashingService {
    fun generateSaltedHash(password: String, saltedHashLength: Int = 32): SaltedHash
    fun verify(password: String, saltedHash: SaltedHash): Boolean
}