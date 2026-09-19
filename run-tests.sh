#!/bin/bash
set -e

echo "Waiting for device to be ready..."
adb wait-for-device
adb shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done'
echo "Device is ready!"

echo "Installing calculator APKs..."
adb install-multiple calculator-base.apk calculator-split.apk || echo "APK install failed, continuing..."

echo "Starting Appium server in background..."
nohup appium > /tmp/appium.log 2>&1 &
APPIUM_PID=$!
echo "Appium PID: $APPIUM_PID"

echo "Waiting for Appium to be ready..."
for i in $(seq 1 30); do
  if curl -s http://localhost:4723/status > /dev/null 2>&1; then
    echo "Appium is ready!"
    break
  fi
  echo "Waiting for Appium... ($i/30)"
  sleep 2
done

echo "Running Gradle tests..."
./gradlew clean test --rerun-tasks

echo "Stopping Appium server..."
kill $APPIUM_PID 2>/dev/null || true
