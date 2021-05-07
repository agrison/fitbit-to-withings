import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21-2"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    application
}


group = "me.grison"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("commons-io:commons-io:2.8.0")
    implementation("de.vandermeer:asciitable:0.3.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.21-2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.21-2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.21-2")
    compile("com.github.kittinunf.fuel:fuel-gson:2.0.1")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
    include("**/*")
    include("**/*.kotlin_builtins")
}