#!/bin/sh

#Input is projext.xml
INPUT_XML=$1
ReportPath=$2

RHPATH=`pwd`
SOAPUI_PATH=$RHPATH/soapui-pro-4.5.1/bin

$SOAPUI_PATH/testrunner.sh -a -f$ReportPath -R"TestCase Report" -FPDF $INPUT_XML
#echo $SOAPUI_PATH/testrunner.sh -a -f$ReportPath -R"TestCase Report" -FPDF $INPUT_XML
