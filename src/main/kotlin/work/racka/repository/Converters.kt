package work.racka.repository

import org.jetbrains.exposed.sql.ResultRow
import work.racka.data.model.Admin
import work.racka.data.model.Laptop
import work.racka.data.table.AdminTable
import work.racka.data.table.LaptopTable

object Converters {
    fun rowToAdmin(row: ResultRow?): Admin? {
        row?.let {
            return Admin(
                email = row[AdminTable.email],
                username = row[AdminTable.username],
                hashPassword = row[AdminTable.hashPassword]
            )
        }
        return null
    }

    fun rowToLaptop(row: ResultRow?): Laptop? {
        row?.let {
            return Laptop(
                model = row[LaptopTable.model],
                imageUrl = row[LaptopTable.imageUrl],
                releaseDate = row[LaptopTable.releaseDate],
                series = row[LaptopTable.series],
                marketPriceStart = row[LaptopTable.marketPriceStart],
                marketPriceEnd = row[LaptopTable.marketPriceEnd],
                processorPlatforms = row[LaptopTable.processorPlatforms],
                processors = row[LaptopTable.processors],
                graphics = row[LaptopTable.graphics],
                maxRam = row[LaptopTable.maxRam],
                displayRes = row[LaptopTable.displayRes],
                touchScreen = row[LaptopTable.touchScreen],
                screenSize = row[LaptopTable.screenSize],
                backlitKb = row[LaptopTable.backlitKb],
                fingerPrintReader = row[LaptopTable.fingerPrintReader],
                kbType = row[LaptopTable.kbType],
                dualBatt = row[LaptopTable.dualBatt],
                internalBatt = row[LaptopTable.internalBatt],
                externalBatt = row[LaptopTable.externalBatt],
                psrefLink = row[LaptopTable.psrefLink],
                biosVersion = row[LaptopTable.biosVersion],
                knownIssues = row[LaptopTable.knownIssues],
                knownIssuesLinks = row[LaptopTable.knownIssuesLinks],
                displaysSupported = row[LaptopTable.displaysSupported],
                otherMods = row[LaptopTable.otherMods],
                otherModsLinks = row[LaptopTable.otherModsLinks],
                biosLockIn = row[LaptopTable.biosLockIn],
                ports = row[LaptopTable.ports]
            )
        }
        return null
    }
}