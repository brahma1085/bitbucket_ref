This_Script_Home=`dirname $0`
echo "This_Script_Home $This_Script_Home"
sh $This_Script_Home/../../../../Core/src/main/scripts/setUp.sh
echo "bld $BLD_HOME"
echo "w home $WORKSPACE_HOME "

mvn -f $This_Script_Home/pomSema.xml clean install -Dmaven.test.skip=true