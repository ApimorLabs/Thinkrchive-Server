package work.racka.repository

import org.jetbrains.exposed.sql.ResultRow
import work.racka.data.model.Admin
import work.racka.data.model.Laptop
import work.racka.data.model.request.LaptopRequest
import work.racka.data.model.response.LaptopResponse
import work.racka.data.table.AdminTable
import work.racka.data.table.LaptopTable

object Converters {
    fun rowToAdmin(row: ResultRow): Admin {
        return Admin(
            _id = row[AdminTable.email],
            email = row[AdminTable.email],
            username = row[AdminTable.username],
            hashPassword = row[AdminTable.hashPassword],
            salt = ""
        )
    }

    fun rowToLaptopResponse(row: ResultRow): LaptopResponse {
        return LaptopResponse(
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

    fun Laptop.asLaptopResponse(): LaptopResponse {
        return LaptopResponse(
            model = this.model,
            imageUrl = this.imageUrl,
            releaseDate = this.releaseDate,
            series = this.series,
            marketPriceStart = this.marketPriceStart,
            marketPriceEnd = this.marketPriceEnd,
            processorPlatforms = this.processorPlatforms,
            processors = this.processors,
            graphics = this.graphics,
            maxRam = this.maxRam,
            displayRes = this.displayRes,
            touchScreen = this.touchScreen,
            screenSize = this.screenSize,
            backlitKb = this.backlitKb,
            fingerPrintReader = this.fingerPrintReader,
            kbType = this.kbType,
            dualBatt = this.dualBatt,
            internalBatt = this.internalBatt,
            externalBatt = this.externalBatt,
            psrefLink = this.psrefLink,
            biosVersion = this.biosVersion,
            knownIssues = this.knownIssues,
            knownIssuesLinks = this.knownIssuesLinks,
            displaysSupported = this.displaysSupported,
            otherMods = this.otherMods,
            otherModsLinks = this.otherModsLinks,
            biosLockIn = this.biosLockIn,
            ports = this.ports
        )
    }

    fun LaptopRequest.asDbLaptop(): Laptop {
        return Laptop(
            _id = this.model,
            imageUrl = this.imageUrl,
            releaseDate = this.releaseDate,
            series = this.series,
            marketPriceStart = this.marketPriceStart,
            marketPriceEnd = this.marketPriceEnd,
            processorPlatforms = this.processorPlatforms,
            processors = this.processors,
            graphics = this.graphics,
            maxRam = this.maxRam,
            displayRes = this.displayRes,
            touchScreen = this.touchScreen,
            screenSize = this.screenSize,
            backlitKb = this.backlitKb,
            fingerPrintReader = this.fingerPrintReader,
            kbType = this.kbType,
            dualBatt = this.dualBatt,
            internalBatt = this.internalBatt,
            externalBatt = this.externalBatt,
            psrefLink = this.psrefLink,
            biosVersion = this.biosVersion,
            knownIssues = this.knownIssues,
            knownIssuesLinks = this.knownIssuesLinks,
            displaysSupported = this.displaysSupported,
            otherMods = this.otherMods,
            otherModsLinks = this.otherModsLinks,
            biosLockIn = this.biosLockIn,
            ports = this.ports
        )
    }
}
