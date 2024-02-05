plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    api("javax.inject:javax.inject:1")
}