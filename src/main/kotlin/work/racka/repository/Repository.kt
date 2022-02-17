package work.racka.repository

import work.racka.data.model.Admin
import work.racka.data.model.Laptop

interface Repository {

    // Add admin
    suspend fun addAdmin(admin: Admin)

    // Search admin for login
    suspend fun findAdmin(identifier: String): Admin?

    //Laptop query CRUD operations
    suspend fun getAllLaptops(): List<Laptop>

    suspend fun addLaptop(laptop: Laptop)

    suspend fun updateLaptop(laptop: Laptop)

    suspend fun deleteLaptop(model: String)
}