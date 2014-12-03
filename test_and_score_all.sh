#!/bin/bash

datadir="data/"
solndir="soln/"
invoke="java -jar LemonFinder.jar"
rm -rf "${solndir}"

for p in 0 1 2 3 
do
  for n in 0 1 2
  do
  infile="${datadir}problem${p}_input_${n}.txt"
  outfile="${solndir}problem${p}_output_${n}.txt"
  paramfile="${datadir}problem${p}_param_${n}.txt"
  echo "${infile}"
  ${invoke} ${infile} ${outfile}

  java -jar ./spec/evaluate.jar "${paramfile}" "${outfile}"
  done
done
echo

