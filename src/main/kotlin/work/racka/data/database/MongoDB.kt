package work.racka.data.database

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase

class MongoDB(
    val coroutineDatabase: CoroutineDatabase,
    val dispatcher: CoroutineDispatcher
) {

    inline fun <reified T : Any> collection()
            : CoroutineCollection<T> = coroutineDatabase.getCollection<T>()

    suspend fun <T> query(block: () -> T): T =
        withContext(dispatcher) {
            block()
        }

}