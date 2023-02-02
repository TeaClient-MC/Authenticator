import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "tk.teaclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")

    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("com.google.code.gson:gson:2.10")
}



tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
