echo OFF
echo Started Replacing Jars...
echo ==============================================================
set source=E:\WS\iSATT\iSATT_1.0
set destination=C:\Java

echo Source = %source% . Destination = %destination%

del %destination%\TomcatAgent\shared\ApiRunner-1.jar /f
del %destination%\TomcatAgent\shared\TfwCore-0.0.1.jar /f
del %destination%\TomcatAgent\shared\TfwData-0.0.1.jar /f
del %destination%\TomcatAgent\shared\GRXDeviceIDGenPlugIn-0.0.1.jar /f
del %destination%\TomcatAgent\shared\HttpRunner-1.jar /f
del %destination%\TomcatAgent\shared\JavaAPIRunner-0.0.1-SNAPSHOT.jar /f
del %destination%\TomcatAgent\shared\JunitRunner-0.0.1-SNAPSHOT.jar /f
del %destination%\TomcatAgent\shared\MT-1.jar /f
del %destination%\TomcatAgent\shared\RAFT-1.jar /f
del %destination%\TomcatAgent\shared\SA-1.jar /f
del %destination%\TomcatAgent\shared\SQ-0.0.1-SNAPSHOT.jar /f
del %destination%\TomcatAgent\shared\TfwUtils-0.0.1.jar /f
del %destination%\TomcatAgent\shared\Verifiers-1.jar /f

del %destination%\TomcatCore\shared\ApiRunner-1.jar /f
del %destination%\TomcatCore\shared\TfwCore-0.0.1.jar /f
del %destination%\TomcatCore\shared\TfwData-0.0.1.jar /f
del %destination%\TomcatCore\shared\GRXDeviceIDGenPlugIn-0.0.1.jar /f
del %destination%\TomcatCore\shared\HttpRunner-1.jar /f
del %destination%\TomcatCore\shared\JavaAPIRunner-0.0.1-SNAPSHOT.jar /f
del %destination%\TomcatCore\shared\JunitRunner-0.0.1-SNAPSHOT.jar /f
del %destination%\TomcatCore\shared\MT-1.jar /f
del %destination%\TomcatCore\shared\RAFT-1.jar /f
del %destination%\TomcatCore\shared\SA-1.jar /f
del %destination%\TomcatCore\shared\SQ-0.0.1-SNAPSHOT.jar /f
del %destination%\TomcatCore\shared\TfwUtils-0.0.1.jar /f
del %destination%\TomcatCore\shared\Verifiers-1.jar /f

echo Deleted the necessary Jars file
echo ==============================================================

xcopy %source%\ApiRunner\target\ApiRunner-1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\Core\target\TfwCore-0.0.1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\Data\target\TfwData-0.0.1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\GRXDeviceIDGenPlugIn\target\GRXDeviceIDGenPlugIn-0.0.1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\HttpRunner\target\HttpRunner-1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\JavaAPIRunner\target\JavaAPIRunner-0.0.1-SNAPSHOT.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\JunitRunner\target\JunitRunner-0.0.1-SNAPSHOT.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\MobileRunner\target\MT-1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\RaftRunner\target\RAFT-1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\SeleniumRunner\target\SA-1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\SquishRunner\target\SQ-0.0.1-SNAPSHOT.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\Utils\target\TfwUtils-0.0.1.jar %destination%\TomcatCore\shared\ /y
xcopy %source%\Verifiers\target\Verifiers-1.jar %destination%\TomcatCore\shared\ /y

echo Jar Files Copied to Core
echo ==============================================================

xcopy %source%\ApiRunner\target\ApiRunner-1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\Core\target\TfwCore-0.0.1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\Data\target\TfwData-0.0.1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\GRXDeviceIDGenPlugIn\target\GRXDeviceIDGenPlugIn-0.0.1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\HttpRunner\target\HttpRunner-1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\JavaAPIRunner\target\JavaAPIRunner-0.0.1-SNAPSHOT.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\JunitRunner\target\JunitRunner-0.0.1-SNAPSHOT.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\MobileRunner\target\MT-1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\RaftRunner\target\RAFT-1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\SeleniumRunner\target\SA-1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\SquishRunner\target\SQ-0.0.1-SNAPSHOT.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\Utils\target\TfwUtils-0.0.1.jar %destination%\TomcatAgent\shared\ /y
xcopy %source%\Verifiers\target\Verifiers-1.jar %destination%\TomcatAgent\shared\ /y

echo Jar Files Copied to Agent
echo ==============================================================

echo done