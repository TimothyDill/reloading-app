plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.drakkar"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin test support
    testImplementation(kotlin("test"))

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

application {
    // Update if your main class differs
    mainClass.set("com.drakkar.reloading.core.simulation.MainKt")
}

tasks.withType<Test> {
    testLogging {
        events("passed", "failed", "skipped", "standardOut", "standardError")
        showExceptions = true
        showCauses = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

kotlin {
    jvmToolchain(21)
}