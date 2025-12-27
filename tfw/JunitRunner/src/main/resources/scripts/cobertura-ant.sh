#!/bin/sh
cd $3
echo `pwd`
echo $1 $2 $3
ant -f $1 $2