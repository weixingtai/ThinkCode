plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.think.design"
    compileSdk = properties["compileSdk"].toString().toInt()

    defaultConfig {
        applicationId = "com.think.design"
        minSdk = properties["minSdk"].toString().toInt()
        targetSdk = properties["compileSdk"].toString().toInt()
        versionCode = properties["versionCode"].toString().toInt()
        versionName = properties["versionName"].toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main") {
            val resDir = mutableSetOf<String>()
            resDir.add("animation")
            resDir.add("appbar")
            resDir.add("badge")
            resDir.add("base")
            resDir.add("behavior")
            resDir.add("bottomappbar")
            resDir.add("bottomnavigation")
            resDir.add("bottomsheet")
            resDir.add("button")
            resDir.add("canvas")
            resDir.add("card")
            resDir.add("carousel")
            resDir.add("checkbox")
            resDir.add("chip")
            resDir.add("circularreveal")
            resDir.add("color")
            resDir.add("datepicker")
            resDir.add("dialog")
            resDir.add("divider")
            resDir.add("dockedtoolbar")
            resDir.add("drawable")
            resDir.add("elevation")
            resDir.add("expandable")
            resDir.add("floatingactionbutton")
            resDir.add("home")
            resDir.add("imageview")
            resDir.add("internal")
            resDir.add("listitem")
            resDir.add("loadingindicator")
            resDir.add("materialswitch")
            resDir.add("math")
            resDir.add("motion")
            resDir.add("navigation")
            resDir.add("navigationrail")
            resDir.add("overflow")
            resDir.add("progressindicator")
            resDir.add("radiobutton")
            resDir.add("resources")
            resDir.add("ripple")
            resDir.add("search")
            resDir.add("shadow")
            resDir.add("shape")
            resDir.add("sidesheet")
            resDir.add("slider")
            resDir.add("snackbar")
            resDir.add("stateful")
            resDir.add("tabs")
            resDir.add("textfield")
            resDir.add("textview")
            resDir.add("theme")
            resDir.add("timepicker")
            resDir.add("tooltip")
            resDir.add("transformation")
            resDir.add("transition")
            resDir.forEach {
                res.directories += "src/main/java/com/think/design/$it/res"
            }
        }
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
    implementation(project(":base"))
    implementation(project(":common"))
    implementation(project(":theme"))
}