# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# --- Hilt (Dagger) ---
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.ViewModelProvider$Factory { *; }
-dontwarn dagger.**
-dontwarn javax.inject.**
-dontwarn dagger.hilt.**

# --- Retrofit ---
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# --- Moshi ---
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**
-keepclassmembers class * {
    @com.squareup.moshi.* <fields>;
}

# --- Coil ---
-keep class coil.** { *; }
-dontwarn coil.**

# --- Navigation Compose ---
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**

# --- Coroutines ---
-dontwarn kotlinx.coroutines.**

# --- LeakCanary (solo debug, pero por si acaso) ---
-assumenosideeffects class com.squareup.leakcanary.** { *; }
-dontwarn com.squareup.leakcanary.**