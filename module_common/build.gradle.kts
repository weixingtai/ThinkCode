import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.code.common"
    compileSdk = properties["compileSdk"].toString().toInt()

    defaultConfig {
        minSdk = properties["minSdk"].toString().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        enable
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
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.viewbinding)
    api(libs.material)
    api(libs.activity.ktx)
    api(libs.fragment.ktx)
    api(libs.kotlin.stdlib)
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.core)
    api(libs.lifecycle.extensions)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.hilt.android)
    api(libs.datastore)
    api(libs.multidex)
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.gson)
    api(libs.okhttp)
    api(libs.logging.interceptor)
    api(libs.room.runtime)
    api(libs.room.ktx)
    api(libs.room.rxjava2)
    api(libs.room.guava)
    api(libs.tinypinyin)
    api(libs.glide)
    api(libs.glide.compiler)
    compileOnly(libs.hilt.compiler)
    compileOnly(libs.room.compiler)
    compileOnly(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}