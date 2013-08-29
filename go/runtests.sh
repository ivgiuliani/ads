#!/bin/bash

MODULES=(
  lists
)

export GOPATH=(pwd)

pushd src/ads
for module in $MODULES
do
  pushd $module
  go test -v
  popd
done
popd
