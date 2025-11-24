# CodaiApp — Running the Android app

This document explains how to run the app from the terminal, via VS Code tasks, or using an emulator / device.

## Prerequisites

- JDK 11 or 17 installed
- Android SDK + Android Studio (recommended)
- Add these to your `~/.zshrc` or shell configuration:

```bash
export ANDROID_SDK_ROOT="$HOME/Library/Android/sdk"
export PATH="$PATH:$ANDROID_SDK_ROOT/emulator:$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin"
```

- Confirm the SDK tools are installed and on PATH: `sdkmanager`, `avdmanager`, `emulator`, and `adb`

## Quick run from terminal

1. Make the run script executable (only once):

```bash
chmod +x ./scripts/run-android.sh
```

2. If no device is connected, set an AVD name and run:

```bash
# set the AVD environment variable, or create a new AVD in Android Studio / avdmanager
export AVD_NAME="Pixel_7_API_33"
./scripts/run-android.sh
### Auto create AVD (optional)

If you don't have an AVD yet, we added a helper that creates and starts an AVD automatically:

```bash
# Optional: set AVD_NAME to change the AVD name
export AVD_NAME=Pixel_7_API_33
./scripts/setup-emulator.sh
```

You can also run this from the Task: `Create Emulator (auto)` in the VS Code Task list.

```

3. To run with a connected physical device (USB debugging enabled):

```bash
./scripts/run-android.sh
```

## VS Code Tasks

This workspace includes several VS Code tasks you can use from the Command Palette (Tasks: Run Task):

- `Gradle: Clean` — runs `./gradlew clean`
- `Gradle: Assemble Debug` — builds debug APK
- `Install Debug (device/emulator)` — installs the debug APK to any connected device or emulator
- `Start Emulator` — starts an emulator using `$AVD_NAME` (env var)
- `Run App (install + launch)` — builds, installs, and launches the app

## Troubleshooting

- `adb` not found: ensure platform-tools are installed and in PATH
- `emulator` not found: ensure `emulator` is in PATH (SDK emulator installed)
- If you uninstall and reinstall Gradle or the wrapper, make sure `./gradlew` has execute permissions
- If `am start` fails, confirm the package name: `com.example.codaiapp` and activity `/MainActivity`

- If you see the error "Can't find Node.js binary 'node': path does not exist":
	- This happens because a VS Code launch configuration expected Node.js. The project doesn't need Node.js to run the Android app — you can use the VS Code tasks or the provided script.
	- Fix options:
		- Install Node.js (recommended if you plan to run other Node-based extensions):
			- Using Homebrew:
				```bash
				brew install node
				```
			- Using nvm (recommended if you manage multiple Node versions):
				```bash
				# install nvm (if not installed)
				curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.4/install.sh | bash
				# source your shell config, then install Node LTS
				nvm install --lts
				nvm use --lts
				```
		- Or simply run the app using VS Code Tasks / Terminal:
			- Command Palette → Tasks: Run Task → `Run App (install + launch)`
			- Or run `chmod +x ./scripts/run-android.sh && ./scripts/run-android.sh` in the terminal


## Build and debug in Android Studio

For full debugging (breakpoints, profiler), open this project in Android Studio and use Run / Debug as usual.

If you want me to add automated steps for GitHub Actions or CI, or add a VS Code Java debug configuration for attaching to processes, tell me what you prefer!

## Debugging from VS Code (Attach Java Debugger)

You can attach a Java debugger from VS Code to the running Android app using JDWP. This avoids using Node and will let you set breakpoints in Java code. Steps:

1. Install the Java Debugger extension (provided by Red Hat / Microsoft):
	- Search for "Debugger for Java" and "Language Support for Java" in the Extensions view and install the recommended Java extensions.
2. Run the app by using the VS Code task or the run script; the provided `preLaunchTask` will build/install and try to forward JDWP to port 8700:
	- `Run App (install + launch)` or run `./scripts/run-android.sh`.
3. Start the VS Code launch configuration `Attach Java Debugger (JDWP)` (F5). The configuration's preLaunchTask builds and installs, then the debugger attaches to `localhost:8700`.

Notes and troubleshooting:
- If the debugger fails to attach, make sure JDWP forwarding succeeded. The `scripts/run-android.sh` script attempts to forward JDWP automatically; if it couldn't find the PID, run manually:
  ```bash
  # get the PID
  PID=$(adb shell pidof com.example.codaiapp | tr -d '\r' | tr -d '\n')
  # forward jdwp
  adb forward tcp:8700 jdwp:$PID
  ```
- If you get "Cannot find Java debug extension" in VS Code, install the Java extension pack.
