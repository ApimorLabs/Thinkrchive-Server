package work.racka.authentication

import com.auth0.jwt.algorithms.Algorithm
import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Auth {
    // This is stored in your remote environment and is used prevent
    // anyone from making an admin account unless they have the key
    // provided by the owner
    // Temp solution
    const val ADMIN_REG_KEY = "ADMIN_REG_KEY"

    const val ISSUER = "Thinkrchive"
    const val SUBJECT = "ThinkrchiveAuthentication"
    const val JWT_SECRET_VARIABLE = "JWT_SECRET"
    const val HASH_KEY_VARIABLE = "HASH_SECRET_KEY"
    const val SECURE_RANDOM_ALGORITHM = "SHA1PRNG"
    const val HMAC_ALGORITHM = "HmacSHA256"

    private val jwtSecret: String = System.getenv(JWT_SECRET_VARIABLE)
    val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret)

    private val hashKey: ByteArray = System.getenv(HASH_KEY_VARIABLE).toByteArray()
    private val hmacKey = SecretKeySpec(hashKey, HMAC_ALGORITHM)

    fun hash(password: String): String {
        val hmac = Mac.getInstance(HMAC_ALGORITHM)
        hmac.init(hmacKey)
        return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
    }
}