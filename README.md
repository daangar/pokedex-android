# Pokedex

A simple Pokedex application built with modern Android development practices, including Compose, Hilt, Retrofit, Moshi, Room, and Kotlin Flow, following a Clean Architecture pattern.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Cloning the Repository](#cloning-the-repository)
- [Building the Project](#building-the-project)
  - [Importing into Android Studio](#importing-into-android-studio)
  - [Setting up Signing Configurations](#setting-up-signing-configurations)
  - [Building](#building)
- [Running the App](#running-the-app)
  - [Build Variants](#build-variants)
- [Project Structure](#project-structure)
- [Key Libraries Used](#key-libraries-used)

## Getting Started

### Prerequisites

- Android Studio (latest stable version recommended)
- JDK 17 (usually bundled with Android Studio)

### Cloning the Repository

```bash
git clone https://github.com/daangar/Pokedex.git # Replace with the actual repository URL
cd Pokedex
```

## Building the Project

### Importing into Android Studio

1.  Open Android Studio.
2.  Click on "Open" (or "Open an Existing Project").
3.  Navigate to the cloned `Pokedex` directory and select it.
4.  Android Studio will automatically sync the Gradle project. This might take a few minutes.

### Setting up Signing Configurations

To sign the app for both debug and release builds, you need to set up keystore properties.

1.  **Create Keystore Property Files:**
    You will need two property files. By default, the application expects them in the project's root directory:
    *   `keystore.debug.properties`
    *   `keystore.release.properties`

    These files **should NOT be committed** to the version control system (they are already in `.gitignore`).

2.  **Configure Paths in `local.properties` (Optional):**
    If you want to store your keystore property files elsewhere, you can specify their paths in your `local.properties` file (this file should also not be committed):

    ```properties
    debugKeystoreProps=/path/to/your/keystore.debug.properties
    releaseKeystoreProps=/path/to/your/keystore.release.properties
    ```
    If these lines are not present in `local.properties`, the build will look for `keystore.debug.properties` and `keystore.release.properties` in the project's root directory.

    **Example `local.properties` content:**
    ```properties
    # SDK location (usually managed by Android Studio)
    # sdk.dir=/Users/yourusername/Library/Android/sdk

    # Keystore properties file paths (if not in project root)
    # debugKeystoreProps=/Users/yourusername/keystores/pokedex_debug.properties
    # releaseKeystoreProps=/Users/yourusername/keystores/pokedex_release.properties
    ```

3.  **Property File Format:**
    Both `keystore.debug.properties` and `keystore.release.properties` should follow this format:

    ```properties
    storeFile=/path/to/your/keystore_file.jks_or_keystore
    storePassword=your_store_password
    keyAlias=your_key_alias
    keyPassword=your_key_password
    ```

    **Example `keystore.debug.properties`:**
    Create a file named `keystore.debug.properties` in your project root (or the custom path defined in `local.properties`) with content similar to this, replacing placeholder values with your actual debug keystore details:
    ```properties
    storeFile=/path/to/your/debug.keystore
    storePassword=your_debug_store_password
    keyAlias=your_debug_key_alias
    keyPassword=your_debug_key_password
    ```

    **Example `keystore.release.properties`:**
    Similarly, create `keystore.release.properties` with your release keystore details:
    ```properties
    storeFile=/path/to/your/release.keystore
    storePassword=your_release_store_password
    keyAlias=your_release_key_alias
    keyPassword=your_release_key_password
    ```
    **Important:** You must generate your own Java Keystore files (e.g., `debug.keystore`, `release.keystore`) and populate these `.properties` files with the correct paths and credentials. These files are essential for building signed APKs/AABs. For local development and running debug builds, ensure your `debug.keystore` and corresponding properties are correctly set up.

### Building

Once the project is synced and signing configurations are set up:
1.  Go to `Build` > `Make Project` in Android Studio.
2.  Alternatively, use the Gradle panel: `Pokedex` > `Tasks` > `build` > `build`.

## Running the App

1.  Select your desired run configuration (usually `app`).
2.  Choose an emulator or connect a physical device.
3.  Click the "Run" button (green play icon) in Android Studio.

### Build Variants

The project is configured with two main build types:

-   **`debug`**:
    -   App Name: "Pokedex Dev" (configurable in `app/src/debug/res/values/strings.xml`)
    -   Application ID Suffix: `.dev` (e.g., `com.davidgarcia.pokedex.dev`)
    -   Not minified.
    -   Includes LeakCanary for memory leak detection.
-   **`release`**:
    -   App Name: "Pokedex" (configurable in `app/src/main/res/values/strings.xml`)
    -   Application ID: (e.g., `com.davidgarcia.pokedex`)
    -   Minified using R8 for smaller app size and obfuscation.

You can select the active build variant from the "Build Variants" tool window in Android Studio (usually found in the bottom-left or side panel).

## Project Structure

The project follows a multi-module Clean Architecture approach:

-   `app`: The main application module, responsible for UI, dependency injection setup, and tying everything together.
-   `core`: Contains common utilities, base classes, and constants shared across other modules.
-   `data`: Implements the data layer, including data sources (remote and local), repositories, and data mapping.
-   `domain`: Contains the business logic, use cases, and domain models.
-   `presentation`: Handles the presentation logic (ViewModels) and UI elements (Compose). It depends on the `domain` layer.

## Key Libraries Used

-   [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI
-   [Kotlin](https://kotlinlang.org/) as the primary programming language
-   [Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-overview.html) for asynchronous programming
-   [Hilt](https://dagger.dev/hilt/) for dependency injection
-   [Retrofit](https://square.github.io/retrofit/) for networking
-   [Moshi](https://github.com/square/moshi) for JSON parsing
-   [Room](https://developer.android.com/training/data-storage/room) for local database (planned, not fully implemented)
-   [Coil](https://coil-kt.github.io/coil/) for image loading
-   [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for in-app navigation
-   [LeakCanary](https://square.github.io/leakcanary/) for memory leak detection in debug builds
-   [Accompanist Libraries](https://google.github.io/accompanist/) for various Compose utilities

This README should provide a good starting point for anyone looking to work with the Pokedex project.
