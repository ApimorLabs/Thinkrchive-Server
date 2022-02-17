package work.racka.data.table

import org.jetbrains.exposed.sql.Table

object LaptopTable: Table() {
    val model = text("model")
    val imageUrl = text("imageUrl")
    val releaseDate = text("releaseDate")
    val series = text("series")
    val marketPriceStart = integer("marketPriceStart")
    val marketPriceEnd = integer("marketPriceEnd")
    val processorPlatforms = text("processorPlatforms")
    val processors = text("processors")
    val graphics = text("graphics")
    val maxRam = text("maxRam")
    val displayRes = text("displayRes")
    val touchScreen = text("touchScreen")
    val screenSize = text("screenSize")
    val backlitKb = text("backlitKb")
    val fingerPrintReader = text("fingerPrintReader")
    val kbType = text("kbType")
    val dualBatt = text("dualBatt")
    val internalBatt = text("internalBatt")
    val externalBatt = text("externalBatt")
    val psrefLink = text("psrefLink")
    val biosVersion = text("biosVersion")
    val knownIssues = text("knownIssues")
    val knownIssuesLinks = text("knownIssuesLinks")
    val displaysSupported = text("displaysSupported")
    val otherMods = text("otherMods")
    val otherModsLinks = text("otherModsLinks")
    val biosLockIn = text("biosLockIn")
    val ports = text("ports")

    override val primaryKey: PrimaryKey?
        get() = PrimaryKey(model)
}