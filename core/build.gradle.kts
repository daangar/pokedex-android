plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.davidgarcia.pokedex.core"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
} 