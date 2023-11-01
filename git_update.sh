# Filename: git_update.sh
# This file should be sourced

#! usr/bin/bash
echo "update to git"

chmod u+x git_update.sh

git add .
git commit -m "V3 Scheduler added for CityWeather trending"
git push --set-upstream Weather master





