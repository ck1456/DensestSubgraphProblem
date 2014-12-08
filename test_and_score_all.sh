#!/bin/bash

datadir="data/"
solndir="soln/"
invoke="java -jar LemonFinder.jar"
rm -rf "${solndir}"

for n in 0 1 2 3 4 5 6  7 8 9 10 11 
  do
  infile="${datadir}input_${n}.txt"
  outfile="${solndir}output_${n}.txt"
  paramfile="${datadir}param_${n}.txt"
  echo "${infile}"
  ${invoke} ${infile} ${outfile}

  java -jar ./spec/evaluate.jar "${paramfile}" "${outfile}"
done
echo

