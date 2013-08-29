#!/bin/bash

MODULES=(
  sorting
  lists
)

export GOPATH=(pwd)

pushd src/ads > /dev/null
for module in ${MODULES[@]}
do
  echo "--------------------------------------------------"
  echo "Module: $module"
  echo "--------------------------------------------------"

  pushd $module > /dev/null
  go test -v
  popd > /dev/null
done
popd > /dev/null
