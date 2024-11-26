plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "microservices"
include("chats")
include("support")
include("core")
include("gradle-ext")
