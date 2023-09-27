# Filename: git_update.sh
# This file should be sourced

#! usr/bin/bash
echo "update to git"

chmod u+x git_update.sh

git add .
git commit -m "Initial upload"
git push --set-upstream Weather master





