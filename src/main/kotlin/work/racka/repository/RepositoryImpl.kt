package work.racka.repository

import org.jetbrains.exposed.sql.*
import work.racka.data.database.LaptopDatabase
import work.racka.data.model.Admin
import work.racka.data.model.Laptop
import work.racka.data.table.AdminTable
import work.racka.data.table.LaptopTable

class RepositoryImpl(
    private val db: LaptopDatabase
) : Repository {
    override suspend fun addAdmin(admin: Admin) {
        db.query {
            AdminTable.insert { adminTable ->
                adminTable[email] = admin.email
                adminTable[username] = admin.username
                adminTable[hashPassword] = admin.hashPassword
            }
        }
    }

    override suspend fun findAdmin(identifier: String): Admin? =
        db.query {
            AdminTable.select {
                AdminTable.email.eq(identifier) or AdminTable.username.eq(identifier)
            }
                .map { Converters.rowToAdmin(it) }
                .singleOrNull()
        }

    //Laptop query CRUD operations
    override suspend fun getAllLaptops(): List<Laptop> = db.query {
        LaptopTable.selectAll().mapNotNull { Converters.rowToLaptop(it) }
    }

    override suspend fun addLaptop(laptop: Laptop) {
        db.query {
            LaptopTable.insert { laptopTable ->
                laptopTable[model] = laptop.model
                laptopTable[imageUrl] = laptop.imageUrl
                laptopTable[releaseDate] = laptop.releaseDate
                laptopTable[series] = laptop.series
                laptopTable[marketPriceStart] = laptop.marketPriceStart
                laptopTable[marketPriceEnd] = laptop.marketPriceEnd
                laptopTable[processorPlatforms] = laptop.processorPlatforms
                laptopTable[processors] = laptop.processors
                laptopTable[graphics] = laptop.graphics
                laptopTable[maxRam] = laptop.maxRam
                laptopTable[displayRes] = laptop.displayRes
                laptopTable[touchScreen] = laptop.touchScreen
                laptopTable[screenSize] = laptop.screenSize
                laptopTable[backlitKb] = laptop.backlitKb
                laptopTable[fingerPrintReader] = laptop.fingerPrintReader
                laptopTable[kbType] = laptop.kbType
                laptopTable[dualBatt] = laptop.dualBatt
                laptopTable[internalBatt] = laptop.internalBatt
                laptopTable[externalBatt] = laptop.externalBatt
                laptopTable[psrefLink] = laptop.psrefLink
                laptopTable[biosVersion] = laptop.biosVersion
                laptopTable[knownIssues] = laptop.knownIssues
                laptopTable[knownIssuesLinks] = laptop.knownIssuesLinks
                laptopTable[displaysSupported] = laptop.displaysSupported
                laptopTable[otherMods] = laptop.otherMods
                laptopTable[otherModsLinks] = laptop.otherModsLinks
                laptopTable[biosLockIn] = laptop.biosLockIn
                laptopTable[ports] = laptop.ports
            }
        }
    }

    override suspend fun updateLaptop(laptop: Laptop) {
        db.query {
            LaptopTable.update(
                where = {
                    LaptopTable.model.eq(laptop.model)
                }
            ) { laptopTable ->
                laptopTable[model] = laptop.model
                laptopTable[imageUrl] = laptop.imageUrl
                laptopTable[releaseDate] = laptop.releaseDate
                laptopTable[series] = laptop.series
                laptopTable[marketPriceStart] = laptop.marketPriceStart
                laptopTable[marketPriceEnd] = laptop.marketPriceEnd
                laptopTable[processorPlatforms] = laptop.processorPlatforms
                laptopTable[processors] = laptop.processors
                laptopTable[graphics] = laptop.graphics
                laptopTable[maxRam] = laptop.maxRam
                laptopTable[displayRes] = laptop.displayRes
                laptopTable[touchScreen] = laptop.touchScreen
                laptopTable[screenSize] = laptop.screenSize
                laptopTable[backlitKb] = laptop.backlitKb
                laptopTable[fingerPrintReader] = laptop.fingerPrintReader
                laptopTable[kbType] = laptop.kbType
                laptopTable[dualBatt] = laptop.dualBatt
                laptopTable[internalBatt] = laptop.internalBatt
                laptopTable[externalBatt] = laptop.externalBatt
                laptopTable[psrefLink] = laptop.psrefLink
                laptopTable[biosVersion] = laptop.biosVersion
                laptopTable[knownIssues] = laptop.knownIssues
                laptopTable[knownIssuesLinks] = laptop.knownIssuesLinks
                laptopTable[displaysSupported] = laptop.displaysSupported
                laptopTable[otherMods] = laptop.otherMods
                laptopTable[otherModsLinks] = laptop.otherModsLinks
                laptopTable[biosLockIn] = laptop.biosLockIn
                laptopTable[ports] = laptop.ports
            }
        }
    }

    override suspend fun deleteLaptop(model: String) {
        db.query {
            LaptopTable.deleteWhere { LaptopTable.model.eq(model) }
        }
    }

    override suspend fun deleteAllLaptops() {
        db.query {
            LaptopTable.deleteAll()
        }
    }
}