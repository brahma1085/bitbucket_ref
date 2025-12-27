#!/bin/sh
#ant -f config/xml/checkout-build.xml checkout
#$1 ->url,$2->username,$3->password,$4>co location

echo $1
echo $2
echo $3
echo $4

svn --username $2 --password $3 --no-auth-cache checkout $1 /$4 <<EOF 2>/dev/null
echo complete