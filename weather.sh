# Filename: weather.sh
# This file should be sourced

#! usr/bin/bash
echo "Weather"

# Show commands
#set -x

chmod u+x weather.sh

cd e:/GitHub/weather/V2/java-be
mvn clean install
cd composite
mvn spring-boot:run &
cd ..
cd location
mvn spring-boot:run &
cd ..
cd conditions
mvn spring-boot:run &

# wait ctrl c
# idle waiting for abort from user
( trap exit SIGINT ; read -r -d '' _ </dev/tty )

kill %1
kill %2
kill %3

sleep 2
clear
cd




