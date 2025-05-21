# Pokedex

## Signing configuration (signingConfigs)

To sign the app for both debug and release builds, create local property files (do NOT commit these files to the repository):

- `keystore.debug.properties` for debug
- `keystore.release.properties` for release

Set the path to these files in your `local.properties` file (which should not be committed to the repo):

```
debugKeystoreProps=/path/to/your/keystore.debug.properties
releaseKeystoreProps=/path/to/your/keystore.release.properties
```

If you do not set the path, the default will be `keystore.debug.properties` and `keystore.release.properties` in the project root.

### Properties file format

Example for `keystore.debug.properties`:
```
storeFile=/path/to/your/debug.keystore
storePassword=debugpassword
keyAlias=debugkey
keyPassword=debugkeypassword
```

Example for `keystore.release.properties`:
```
storeFile=/path/to/your/release.keystore
storePassword=releasepassword
keyAlias=releasekey
keyPassword=releasekeypassword
```

## Build types: Debug vs Release

- The **debug** build uses the app name "Pokedex Dev" and the `applicationId` ends with `.dev` (e.g., `com.davidgarcia.pokedex.dev`).
- The **release** build uses the app name "Pokedex" and the standard `applicationId` (e.g., `com.davidgarcia.pokedex`).
- The app name is managed via resource files:
    - `app/src/main/res/values/strings.xml` contains the name for release (`Pokedex`).
    - `app/src/debug/res/values/strings.xml` overrides the name for debug (`Pokedex Dev`).

You do not need to modify code to change the app name for debug; simply edit the appropriate resource file.
