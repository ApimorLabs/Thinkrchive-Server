package work.racka.repository

import work.racka.data.model.Admin
import work.racka.data.model.request.LaptopRequest
import work.racka.data.model.response.LaptopResponse

interface Repository {

    // TODO: Add a method to get all current admins

    // Add admin
    suspend fun addAdmin(admin: Admin)

    // Search admin for login
    suspend fun findAdmin(email: String): Admin?

    // Delete admin
    suspend fun deleteAdmin(email: String)

    //Laptop query CRUD operations
    suspend fun getAllLaptops(): List<LaptopResponse>

    suspend fun getLaptop(model: String): LaptopResponse?

    suspend fun addLaptop(laptop: LaptopRequest)

    suspend fun updateLaptop(laptop: LaptopRequest)

    suspend fun deleteLaptop(model: String)

    suspend fun deleteAllLaptops()

}