
val kspVersion: String by project
plugins {
    kotlin("jvm")
}

group = "com.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    api("javax.inject:javax.inject:1")
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
}