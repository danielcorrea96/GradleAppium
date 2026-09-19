#!/bin/bash
# Uso:
#   ./run-browserstack.sh [apk]  (por defecto calculator-monolithic.apk)
# Requiere env:
#   BROWSERSTACK_USERNAME, BROWSERSTACK_ACCESS_KEY
# Opcional:
#   BROWSERSTACK_DEVICE="Samsung Galaxy S23" BROWSERSTACK_OS="13.0" BS_APP=bs://...
set -e

APK="${1:-calculator-monolithic.apk}"
DEVICE="${BROWSERSTACK_DEVICE:-Samsung Galaxy S23}"
OS_VERSION="${BROWSERSTACK_OS:-13.0}"

if [[ -z "$BROWSERSTACK_USERNAME" || -z "$BROWSERSTACK_ACCESS_KEY" ]]; then
  echo "ERROR: define BROWSERSTACK_USERNAME y BROWSERSTACK_ACCESS_KEY"
  echo "  export BROWSERSTACK_USERNAME=tu_usuario"
  echo "  export BROWSERSTACK_ACCESS_KEY=tu_key"
  exit 1
fi

if [[ -z "$BS_APP" ]]; then
  if [[ ! -f "$APK" ]]; then
    echo "ERROR: no existe $APK. Pasa la ruta o define BS_APP=bs://..."
    exit 1
  fi
  echo "Subiendo $APK a BrowserStack..."
  RESP=$(curl -s -u "$BROWSERSTACK_USERNAME:$BROWSERSTACK_ACCESS_KEY" \
    -X POST https://api-cloud.browserstack.com/app-automate/upload \
    -F file=@"$APK")
  echo "$RESP"
  BS_APP=$(echo "$RESP" | python3 -c "import sys,json; print(json.load(sys.stdin).get('app_url',''))")
  if [[ -z "$BS_APP" || "$BS_APP" == "None" ]]; then
    echo "ERROR: no se obtuvo app_url. Revisa credenciales/APK."
    exit 1
  fi
  echo "$BS_APP" > bs-app-url.txt
  echo "App subida: $BS_APP"
else
  echo "Usando app existente: $BS_APP"
fi

echo "Ejecutando tests en BrowserStack ($DEVICE / $OS_VERSION)..."
./gradlew clean test \
  -Dexecution=browserstack \
  -DdeviceName="$DEVICE" \
  -DosVersion="$OS_VERSION" \
  -Dapp="$BS_APP"
