plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.devtools.ksp)
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
            resDir.add("adaptive")
            resDir.add("application")
            resDir.add("application/attrs")
            resDir.add("application/legacymultidex")
            resDir.add("application/scope")
            resDir.add("application/theme")
            resDir.add("assets")
            resDir.add("bottomappbar")
            resDir.add("bottomnav")
            resDir.add("bottomsheet")
            resDir.add("button")
            resDir.add("card")
            resDir.add("carousel")
            resDir.add("checkbox")
            resDir.add("chip")
            resDir.add("color")
            resDir.add("datepicker")
            resDir.add("dialog")
            resDir.add("divider")
            resDir.add("draggable")
            resDir.add("dockedtoolbar")
            resDir.add("elevation")
            resDir.add("imageview")
            resDir.add("fab")
            resDir.add("feature")
            resDir.add("floatingtoolbar")
            resDir.add("font")
            resDir.add("internal")
            resDir.add("loadingindicator")
            resDir.add("listitem")
            resDir.add("main")
            resDir.add("materialswitch")
            resDir.add("menu")
            resDir.add("musicplayer")
            resDir.add("navigationrail")
            resDir.add("navigationdrawer")
            resDir.add("overflow")
            resDir.add("preferences")
            resDir.add("progressindicator")
            resDir.add("radiobutton")
            resDir.add("search")
            resDir.add("shapetheming")
            resDir.add("sidesheet")
            resDir.add("slider")
            resDir.add("snackbar")
            resDir.add("tableofcontents")
            resDir.add("tabs")
            resDir.add("textfield")
            resDir.add("themeswitcher")
            resDir.add("timepicker")
            resDir.add("topappbar")
            resDir.add("transition")
            resDir.add("windowpreferences")
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

    api(libs.dagger)
    api(libs.dagger.android)
    api(libs.dagger.android.support)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)
}