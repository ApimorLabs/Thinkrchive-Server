package work.racka.repository

import org.litote.kmongo.eq
import work.racka.data.database.MongoDB
import work.racka.data.model.Admin
import work.racka.data.model.Laptop
import work.racka.data.model.request.LaptopRequest
import work.racka.data.model.response.LaptopResponse
import work.racka.repository.Converters.asDbLaptop
import work.racka.repository.Converters.asLaptopResponse

class MongoRepositoryImpl(
    private val db: MongoDB
) : Repository {

    override suspend fun addAdmin(admin: Admin) {
        db.collection<Admin>().insertOne(admin)
    }

    override suspend fun findAdmin(email: String): Admin? =
        db.collection<Admin>().findOne(Admin::email eq email)

    override suspend fun deleteAdmin(email: String) {
        db.collection<Admin>().deleteOne(Admin::email eq email)
    }

    override suspend fun getAllLaptops(): List<LaptopResponse> =
        db.collection<Laptop>().find()
            .toList().map {
                it.asLaptopResponse()
            }

    override suspend fun getLaptop(model: String): LaptopResponse? =
        db.collection<Laptop>().findOneById(model)
            ?.asLaptopResponse()

    override suspend fun addLaptop(laptop: LaptopRequest) {
        db.collection<Laptop>().insertOne(laptop.asDbLaptop())
    }

    override suspend fun updateLaptop(laptop: LaptopRequest) {
        db.collection<Laptop>().replaceOneById(laptop.model, laptop.asDbLaptop())
    }

    override suspend fun deleteLaptop(model: String) {
        db.collection<Laptop>().deleteOneById(model)
    }

    override suspend fun deleteAllLaptops() {
        db.collection<Laptop>().deleteMany()
    }
}