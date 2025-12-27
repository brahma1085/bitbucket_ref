#see .bash_profile sample - to be saves as "~/.bash_profile", quit and restart terminal app to run this file after save.

#set in .bash_profile
#export BLD_HOME=
#export WORKSPACE_HOME=

This_Script_Home=`dirname $0`
echo "This_Script_Home $This_Script_Home"

echo "bld $BLD_HOME"
echo "w home $WORKSPACE_HOME "
cd  $This_Script_Home/../../../
cp -f $This_Script_Home/pomAssembly.xml pomAssembly.xml
mvn -f ./pomAssembly.xml clean install -Dmaven.test.skip=true
rm ./pomAssembly.xml