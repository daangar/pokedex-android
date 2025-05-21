plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.davidgarcia.pokedex.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
}

dependencies {
    implementation(project(":core"))
} 