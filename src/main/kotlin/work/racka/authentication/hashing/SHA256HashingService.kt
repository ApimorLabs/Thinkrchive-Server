package work.racka.authentication.hashing

import io.ktor.util.*
import work.racka.authentication.Auth
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SHA256HashingService : HashingService {
    override fun generateSaltedHash(password: String, saltedHashLength: Int): SaltedHash {
        val salt = SecureRandom.getInstance(Auth.SECURE_RANDOM_ALGORITHM)
            .generateSeed(saltedHashLength)
        val saltAsHex = salt.toHex()
        val hash = hash(saltAsHex + password)
        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    override fun verify(password: String, saltedHash: SaltedHash): Boolean {
        return hash(saltedHash.salt + password) == saltedHash.hash
    }

    private fun hash(value: String): String {
        val hashKey: ByteArray = System.getenv(Auth.HASH_KEY_VARIABLE).toByteArray()
        val hmacKey = SecretKeySpec(hashKey, Auth.HMAC_ALGORITHM)
        val hmac = Mac.getInstance(Auth.HMAC_ALGORITHM)
        hmac.init(hmacKey)
        return hex(hmac.doFinal(value.toByteArray(Charsets.UTF_8)))
    }

    private fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
}