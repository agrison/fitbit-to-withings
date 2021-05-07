./gradlew build

native-image -H:EnableURLProtocols=https --verbose -jar build/libs/fitbit-to-withings-1.0-SNAPSHOT-all.jar fitbit-to-withings --no-server --no-fallback

/usr/bin/time -v ./fitbit-to-withings

