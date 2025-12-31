import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.think.code"
    compileSdk = properties["compileSdk"].toString().toInt()

    defaultConfig {
        applicationId = "com.think.code"
        minSdk = properties["minSdk"].toString().toInt()
        targetSdk = properties["compileSdk"].toString().toInt()
        versionCode = properties["versionCode"].toString().toInt()
        versionName = properties["versionName"].toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
    dataBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":theme"))
    implementation(project(":language"))
    implementation(project(":design"))
}