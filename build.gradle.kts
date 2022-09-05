import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val hikariVersion: String by project
val postgresVersion: String by project
val koinVersion: String by project
val kmongoVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("io.ktor.plugin") version "2.1.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "work.racka"
version = "1.0.0"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}
with(tasks) {
    create("stage") {
        dependsOn("installDist")
    }
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-locations:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    // Postgres
    implementation("org.postgresql:postgresql:$postgresVersion")
    // Hikari
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    // KMongo
    implementation("org.litote.kmongo:kmongo:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")

    // Koin core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
}