application {
    mainClass.set("com.drakkar.reloading.MainKt")
}

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("-Xms256m", "-Xmx1024m")
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