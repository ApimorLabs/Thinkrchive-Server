package work.racka.repository

import work.racka.data.model.Admin
import work.racka.data.model.Laptop

interface Repository {

    // TODO: Add a method to get all current admins

    // Add admin
    suspend fun addAdmin(admin: Admin)

    // Search admin for login
    suspend fun findAdmin(identifier: String): Admin?

    // Delete admin
    suspend fun deleteAdmin(email: String)

    //Laptop query CRUD operations
    suspend fun getAllLaptops(): List<Laptop>

    suspend fun getLaptop(model: String): Laptop?

    suspend fun addLaptop(laptop: Laptop)

    suspend fun updateLaptop(laptop: Laptop)

    suspend fun deleteLaptop(model: String)

    suspend fun deleteAllLaptops()

}