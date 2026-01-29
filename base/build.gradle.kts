plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.think.base"
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
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.activity)
    api(libs.androidx.viewbinding)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.gridlayout)
    api(libs.androidx.activity.ktx)
    api(libs.androidx.fragment.ktx)
    api(libs.androidx.multidex)
    api(libs.androidx.window)
    api(libs.androidx.window.java)
    api(libs.androidx.preference)
    api(libs.androidx.recyclerview.selection)
    api(libs.kotlin.stdlib)
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.core)
    api(libs.lifecycle.extensions)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.viewmodel.savedstate)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.common.java8)
    api(libs.material.icons.core)
    api(libs.material.icons.extended)
    api(libs.material)
    api(libs.viewpager2)
    api(libs.hilt.android)
    api(libs.guava)
    api(libs.datastore)
    api(libs.room.runtime)
    api(libs.room.ktx)
    api(libs.room.rxjava2)
    api(libs.room.guava)
    api(libs.navigation.fragment.ktx)
    api(libs.navigation.ui.ktx)
    api(libs.paging)
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.okhttp)
    api(libs.okhttp.logging.interceptor)
    api(libs.persistent.cookie.lar)
    api(libs.junit)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(libs.tinypinyin)
    api(libs.glide)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)
    ksp(libs.glide.compiler)
}