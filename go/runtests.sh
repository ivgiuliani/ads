#!/bin/bash

MODULES=(
  dictionary
  lists
  sorting
)

export GOPATH=(pwd)

if [ "$#" -eq 0 ]; then
  TEST_MODULES=${MODULES[@]}
else
  TEST_MODULES=( $@ )
fi

pushd src/ads > /dev/null
for module in ${TEST_MODULES[@]}
do
  echo "--------------------------------------------------"
  echo "Module: $module"
  echo "--------------------------------------------------"

  pushd $module > /dev/null
  go test -v
  popd > /dev/null
done
popd > /dev/null
