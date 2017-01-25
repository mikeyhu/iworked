#!/usr/bin/env bash
cd "${0%/*}"
LOCATION=$(pwd)
cd ~
java -jar ${LOCATION}/target/uberjar/iworked-0.1.0-SNAPSHOT-standalone.jar "$@"
