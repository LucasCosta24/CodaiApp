#!/usr/bin/env bash
set -euo pipefail

# Creates and starts an AVD if it does not exist.
# Usage: AVD_NAME=Pixel_7_API_33 ./scripts/setup-emulator.sh

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

DEFAULT_AVD_NAME="Pixel_7_API_33"
AVD_NAME="${AVD_NAME:-$DEFAULT_AVD_NAME}"

ANDROID_SDK_ROOT="${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}"
export ANDROID_SDK_ROOT
export PATH="$PATH:$ANDROID_SDK_ROOT/emulator:$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin"

function missing() {
  echo "[ERROR] $1 is required but not found."
}

# Check required tools
if ! command -v sdkmanager >/dev/null 2>&1; then
  missing "sdkmanager"
  echo "Please install Android SDK command-line tools and ensure sdkmanager is on PATH."
  exit 1
fi

if ! command -v avdmanager >/dev/null 2>&1; then
  missing "avdmanager"
  echo "Ensure Android SDK command-line tools are installed."
  exit 1
fi

if ! command -v emulator >/dev/null 2>&1; then
  missing "emulator"
  echo "Install the Android Emulator using SDK Manager."
  exit 1
fi

# Determine ABI
ARCH="$(uname -m)"
ABI="x86_64"
if [[ "$ARCH" == "arm64" || "$ARCH" == "aarch64" ]]; then
  ABI="arm64-v8a"
fi

IMAGE_NAME="system-images;android-33;google_apis;${ABI}"

# Ensure platform-tools and emulator installed
echo "Installing SDK components (platform-tools, emulator, cmdline-tools, and system image if missing) ..."
# Accept licenses via yes
yes | sdkmanager --install "platform-tools" "emulator" "cmdline-tools;latest" "$IMAGE_NAME" || true

# Check if AVD already exists
AVD_LIST=$(avdmanager list avd 2>/dev/null || true)
if echo "$AVD_LIST" | grep -q "Name: $AVD_NAME"; then
  echo "AVD $AVD_NAME already exists."
else
  echo "Creating AVD $AVD_NAME ..."
  # Create AVD using 'pixel' device if available, else default
  DEVICE_LIST=$(avdmanager list device)
  DEVICE_NAME="pixel"
  if echo "$DEVICE_LIST" | grep -q "pixel"; then
    DEVICE_NAME="pixel"
  else
    # fallback to the first device in the list
    DEVICE_NAME=$(echo "$DEVICE_LIST" | grep -oP "^\s*\K\S+" | head -n 1 | tr -d '\r' | tr -d '\n')
  fi

  echo "Using device: $DEVICE_NAME"
  # Create the AVD. Use sdcard and default skin.
  echo "no" | avdmanager create avd -n "$AVD_NAME" -k "$IMAGE_NAME" -d "$DEVICE_NAME" --force || true
fi

# Start emulator
echo "Starting emulator $AVD_NAME..."
emulator -avd "$AVD_NAME" -no-snapshot -no-boot-anim &

# Wait for boot complete
adb wait-for-device
until adb shell getprop sys.boot_completed | grep -m 1 '1' >/dev/null 2>&1; do
  sleep 1
done

echo "Emulator $AVD_NAME ready."
