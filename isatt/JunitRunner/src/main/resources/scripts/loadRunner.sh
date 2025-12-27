#!/bin/sh

INPUT_XML=$1
LoadTestLimitInSec=$2
ThreadCount=$3
ReportPath=$4

RHPATH=`pwd`

SOAPUI_PATH=$RHPATH/soapui-pro-4.5.1/bin

$SOAPUI_PATH/loadtestrunner.sh -m $LoadTestLimitInSec -n $ThreadCount -r -f$ReportPath -o -R"LoadTest Report" -FPDF $INPUT_XML