import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
}

fun loadKeystoreProps(path: String): Properties {
    val props = Properties()
    val file = rootProject.file(path)
    if (file.exists()) {
        props.load(file.inputStream())
    }
    return props
}

fun getLocalProperty(key: String): String? {
    val localPropsFile = rootProject.file("local.properties")
    if (localPropsFile.exists()) {
        val props = Properties()
        props.load(localPropsFile.inputStream())
        return props.getProperty(key)
    }
    return null
}

val debugKeystorePropsPath = getLocalProperty("debugKeystoreProps") ?: "keystore.debug.properties"
val releaseKeystorePropsPath = getLocalProperty("releaseKeystoreProps") ?: "keystore.release.properties"

val debugKeystoreProps = loadKeystoreProps(debugKeystorePropsPath)
val releaseKeystoreProps = loadKeystoreProps(releaseKeystorePropsPath)

android {
    namespace = "com.davidgarcia.pokedex"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.davidgarcia.pokedex"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(debugKeystoreProps["storeFile"]?.toString() ?: "debug.keystore")
            storePassword = debugKeystoreProps["storePassword"]?.toString() ?: "android"
            keyAlias = debugKeystoreProps["keyAlias"]?.toString() ?: "androiddebugkey"
            keyPassword = debugKeystoreProps["keyPassword"]?.toString() ?: "android"
        }
        maybeCreate("release")
        getByName("release") {
            storeFile = file(releaseKeystoreProps["storeFile"]?.toString() ?: "release.keystore")
            storePassword = releaseKeystoreProps["storePassword"]?.toString() ?: "android"
            keyAlias = releaseKeystoreProps["keyAlias"]?.toString() ?: "androidreleasekey"
            keyPassword = releaseKeystoreProps["keyPassword"]?.toString() ?: "android"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            applicationIdSuffix = ".dev"
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":presentation"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.coil.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    debugImplementation(libs.leakcanary.android)
}

kapt {
    correctErrorTypes = true
}

kotlin {
    jvmToolchain(17)
}