package work.racka.repository

import work.racka.data.model.Admin

interface Repository {

    // Add admin
    suspend fun addAdmin(admin: Admin)

    // Search admin for login
    suspend fun findAdmin(identifier: String): Admin?

    //
}