import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.code.think"
    compileSdk = properties["compileSdk"].toString().toInt()

    defaultConfig {
        applicationId = "com.code.think"
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_18)
        }
    }

}

dependencies {
    implementation(project(":module_base"))
    implementation(project(":module_common"))
    compileOnly(libs.hilt.compiler)
    compileOnly(libs.room.compiler)
    compileOnly(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}