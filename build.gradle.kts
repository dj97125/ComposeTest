// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
//        maven { url("https://jitpack.io") }
//        maven { url("https://plugins.gradle.org/m2/") }
//        maven { url("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.21")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")


    }

}
plugins {
    id("com.android.application") version("7.1.1") apply false
    id("com.android.library") version("7.1.1") apply false
    id("org.jetbrains.kotlin.android") version("1.9.0") apply false
    id("com.google.dagger.hilt.android") version("2.42") apply false
    id("com.squareup.sqldelight") version("1.5.4") apply false
    id("org.jetbrains.kotlin.jvm") version("1.7.20") apply false
//    id("com.google.devtools.ksp") version("1.8.21-1.0.11") apply false
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}