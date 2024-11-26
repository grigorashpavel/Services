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

        outputDir.writeText("""
               object Config {
                   const val DB_USERNAME = "${project.providers.environmentVariable("DB_USERNAME").orElse("").get()}"
                   const val DB_PASSWORD = "${project.providers.environmentVariable("DB_PASSWORD").orElse("").get()}"
                   const val DB_URL = "${project.providers.environmentVariable("DB_URL").orElse("").get()}"
               }
           """.trimIndent())
    }
}

subprojects {
    group = "ru.pasha"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        the<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension>().apply {
            jvmToolchain(17)
        }
    }

    tasks.register(name = "generateConfig", type = ConfigGeneratingTask::class)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.server.partial.content)
    implementation(libs.ktor.server.openapi)
    implementation(libs.ktor.server.websockets)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.postgresql)
    implementation(libs.h2)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
