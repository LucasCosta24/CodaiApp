#!/usr/bin/env bash
set -euo pipefail

# Script to build, install and start the debug app on a device/emulator.
# Usage: AVD_NAME=Pixel_7_API_33 ./scripts/run-android.sh

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

GRADLEW="$ROOT_DIR/gradlew"
if [ ! -x "$GRADLEW" ]; then
  chmod +x "$GRADLEW" || true
fi

if ! command -v adb >/dev/null 2>&1; then
  echo "adb not found. Please make sure Android SDK platform-tools are installed and on PATH."
  exit 1
fi

DEVICE_COUNT=$(adb devices | sed '1d' | grep -v '^$' | wc -l)

if [ "$DEVICE_COUNT" -eq 0 ]; then
  echo "No device found. Attempting to start emulator..."
  if command -v emulator >/dev/null 2>&1; then
    if [ -z "${AVD_NAME:-}" ]; then
      echo "AVD_NAME not set; defaulting to Pixel_7_API_33"
      AVD_NAME="Pixel_7_API_33"
    fi
    # Try to start the AVD or create it if it doesn't exist
    # If setup-emulator script is available, use it to ensure components are installed and the AVD exists
    if [ -x "${ROOT_DIR}/scripts/setup-emulator.sh" ]; then
      echo "Using setup-emulator.sh to ensure AVD exists and start it: AVD=$AVD_NAME"
      AVD_NAME="$AVD_NAME" "$ROOT_DIR/scripts/setup-emulator.sh"
    else
      echo "Starting emulator $AVD_NAME..."
      emulator -avd "$AVD_NAME" -no-snapshot -no-boot-anim &
    fi
    echo "Waiting for emulator to appear..."
    adb wait-for-device
    echo "Waiting for device to finish booting..."
    until adb shell getprop sys.boot_completed | grep -m 1 '1' >/dev/null 2>&1; do
      sleep 1
    done
    echo "Emulator ready."
  else
    echo "emulator command not available; ensure the Android SDK emulator is installed and on PATH." 
    exit 3
  fi
else
  echo "Device connected. Proceeding..."
fi

# Build and install
"$GRADLEW" clean assembleDebug
"$GRADLEW" installDebug

# Launch the app
PACKAGE_NAME="com.example.codaiapp"
ACTIVITY_NAME=".MainActivity"

echo "Launching $PACKAGE_NAME/$ACTIVITY_NAME"
adb shell am start -n $PACKAGE_NAME/$ACTIVITY_NAME

# Try to forward the app's JDWP port so the Java debugger can attach
echo "Forwarding JDWP for package $PACKAGE_NAME to localhost:8700 (if available)..."
APP_PID=$(adb shell pidof $PACKAGE_NAME | tr -d '\r' | tr -d '\n' || true)
if [ -n "$APP_PID" ] && [ "$APP_PID" != "\n" ]; then
  echo "Found PID $APP_PID, forwarding jdwp:$APP_PID -> tcp:8700"
  adb forward tcp:8700 jdwp:$APP_PID || echo "adb forward failed"
else
  echo "Could not find PID for $PACKAGE_NAME; skipping jdwp forward. If you plan to debug, make sure the app is running and retry."
fi

echo "Done."
