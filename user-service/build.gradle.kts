plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "ru.pasha"
version = "0.0.1"

abstract class ConfigGeneratingTask : DefaultTask() {
    @OutputFile
    val outputDir: File = project.file("${project.buildDir}/generated/source/config/Config.kt")

    @TaskAction
    fun generate() {
        val isExist = outputDir.parentFile.exists()
        if (!isExist) {
            outputDir.parentFile.mkdirs()
        }

        project.providers.environmentVariable("DB_USERNAME").orElse("").get()

        outputDir.writeText("""
               object Config {
                   const val DB_USERNAME = "${project.providers.environmentVariable("DB_USERNAME").orElse("").get()}"
                   const val DB_PASSWORD = "${project.providers.environmentVariable("DB_PASSWORD").orElse("").get()}"
                   const val DB_URL = "${project.providers.environmentVariable("DB_URL").orElse("").get()}"
                   
                   const val JWT_SECRET = "${project.providers.environmentVariable("JWT_SECRET").orElse("").get()}"
                   const val JWT_ISS = "${project.providers.environmentVariable("JWT_ISS").orElse("").get()}"
               }
           """.trimIndent())
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    tasks.register(name = "generateConfig", type = ConfigGeneratingTask::class)
    tasks.named("compileKotlin") {
        dependsOn("generateConfig")
    }
    sourceSets["main"].java.srcDir("$buildDir/generated/source/config")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.websockets)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.postgresql)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.partial.content)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.resources)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
