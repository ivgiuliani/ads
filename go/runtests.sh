#!/bin/bash

MODULES=(
  dictionary
  lists
  sorting
)

# benchmarks are disabled by default since they take quite a while to run,
# if you want to enable them run the test runner with BENCHMARK=true, i.e.:
# env BENCHMARK=true ./runtests.sh
: ${BENCHMARK:=false}

export GOPATH=(pwd)

if [ "$#" -eq 0 ]; then
  TEST_MODULES=${MODULES[@]}
else
  TEST_MODULES=( $@ )
fi

OPT=""
if $BENCHMARK; then
  OPT="$OPT -bench ."
fi

pushd src/ads > /dev/null
for module in ${TEST_MODULES[@]}
do
  echo "--------------------------------------------------"
  echo "Module: $module"
  echo "--------------------------------------------------"

  pushd $module > /dev/null
  go test -v $OPT
  popd > /dev/null
done
popd > /dev/null
