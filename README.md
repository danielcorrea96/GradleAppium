# GradleAppium

Modern Appium + Serenity BDD test automation project using the **Screenplay Pattern** with **JUnit 5**.

## Technologies

| Component | Version |
|---|---|
| **Gradle** | 8.5 |
| **Java** | 17 (Temurin) |
| **Appium** | 2.19.0 |
| **Appium Java Client** | 10.1.1 |
| **Serenity BDD** | 5.3.9 |
| **JUnit 5** | 6.0.3 |
| **AssertJ** | 3.27.3 |
| **UiAutomator2** | 4.2.5 |

## Architecture

This project follows the **Screenplay Pattern** for test automation:

```
src/test/java/com/appium/
├── AppTest.java              # Main test class (JUnit 5)
├── tasks/
│   └── PerformSum.java      # Screenplay Task (user action)
├── ui/
│   └── CalculatorPage.java  # Screen Object (element definitions)
└── questions/
    └── TheResult.java       # Screenplay Question (assertion)
```

### Screenplay Pattern Elements

- **Actor** — Represents a user/persona executing the scenario
- **Task** — Encapsulates a user action (e.g., `PerformSum`)
- **Question** — Encapsulates a verification/assertion (e.g., `TheResult`)
- **Target** — Defines UI element locators (e.g., `CalculatorPage.BUTTON_2`)
- **Ability** — Represents a capability the Actor possesses (e.g., `BrowseTheWeb`)

## Prerequisites

### Required Tools

- **Java 17+** (Temurin recommended)
- **Node.js** 18+
- **Android SDK** (API 35+)
- **Appium 2.x**
- **Android Emulator** or physical device

### Setup

1. **Install Java 17:**
   ```bash
   brew install --cask temurin@17
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   ```

2. **Install Appium:**
   ```bash
   npm install -g appium
   appium driver install uiautomator2
   ```

3. **Start the Android Emulator:**
   ```bash
   emulator -avd Medium_Phone_API_36.0 -no-window &
   adb devices  # verify device is connected
   ```

4. **Verify environment:**
   ```bash
   java -version
   appium --version
   adb devices
   ```

## Project Structure

```
GradleAppium/
├── build.gradle              # Gradle build configuration
├── settings.gradle            # Project settings
├── gradle.properties          # Build properties
├── gradle/wrapper/           # Gradle wrapper (8.5)
├── appium.config.json         # Appium 2.x configuration
├── calculadora.apk            # Android Calculator APK
├── src/test/java/com/appium/ # Test source code
│   ├── AppTest.java
│   ├── tasks/
│   ├── ui/
│   └── questions/
└── README.md
```

## Running Tests

### Start the emulator (if not running):
```bash
emulator -avd Medium_Phone_API_36.0 -no-window &
```

### Run all tests:
```bash
./gradlew test
```

### Run a specific test:
```bash
./gradlew test --tests "com.appium.AppTest"
```

### Run with Serenity reports:
```bash
./gradlew test aggregate
# Reports generated in: target/site/serenity/
```

### Clean build:
```bash
./gradlew clean test
```

## Cross-Platform (Android + iOS)

To extend this project for iOS:

1. **Install xcuitest driver:**
   ```bash
   appium driver install xcuitest
   ```

2. **Install Xcode** and configure the iOS Simulator.

3. **Create an iOS test class** with iOS-specific capabilities:
   ```java
   caps.setCapability("platformName", "iOS");
   caps.setCapability("appium:automationName", "XCUITest");
   caps.setCapability("appium:deviceName", "iPhone 16");
   ```

4. **Use the same Task/Question classes** — only the `Target` locators change per platform.

## Key Files

| File | Purpose |
|---|---|
| `build.gradle` | Build configuration with Serenity BOM, Appium Client, JUnit 5 |
| `appium.config.json` | Appium 2.x capabilities configuration |
| `src/test/java/com/appium/AppTest.java` | Entry point test class |
| `src/test/java/com/appium/tasks/PerformSum.java` | Task defining the "perform sum" action |
| `src/test/java/com/appium/ui/CalculatorPage.java` | UI element definitions (locators) |
| `src/test/java/com/appium/questions/TheResult.java` | Question for result verification |

## Migration Notes

This project was modernized from:
- Gradle 4.3 → Gradle 8.5
- Serenity BDD 2.0.x → Serenity BDD 5.3.9
- JUnit 4 → JUnit 5
- `jcenter()` → `mavenCentral()`
- Old Eclipse project files → Gradle-only
- Legacy `@Managed`, `@SerenityTest` annotations → Modern JUnit 5 + Screenplay API

## License

Apache License 2.0
