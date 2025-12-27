/*
SQLyog Ultimate v9.51 
MySQL - 5.1.63-community : Database - projectautomation
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`projectautomation` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `projectautomation`;

/*Table structure for table `actions` */

DROP TABLE IF EXISTS `actions`;

CREATE TABLE `actions` (
  `ActionID` int(11) NOT NULL,
  `ActionName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ActionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `agentdetails` */

DROP TABLE IF EXISTS `agentdetails`;

CREATE TABLE `agentdetails` (
  `AgentID` int(11) NOT NULL,
  `AgentName` varchar(50) DEFAULT NULL,
  `IP` varchar(20) DEFAULT NULL,
  `Port` int(11) DEFAULT NULL,
  `MachineDetails` varchar(100) DEFAULT NULL,
  `Protocol` varchar(50) DEFAULT NULL,
  `Status` char(1) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`AgentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `appfeature` */

DROP TABLE IF EXISTS `appfeature`;

CREATE TABLE `appfeature` (
  `FeatureID` int(11) NOT NULL,
  `FeatureName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `FunctionalID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`FeatureID`),
  KEY `AppFeature_Screen_FKv1` (`FunctionalID`),
  CONSTRAINT `AppFeature_Screen_FKv1` FOREIGN KEY (`FunctionalID`) REFERENCES `appfunctionality` (`FunctionalID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `appfunctionality` */

DROP TABLE IF EXISTS `appfunctionality`;

CREATE TABLE `appfunctionality` (
  `FunctionalID` int(11) NOT NULL,
  `FunctionalName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`FunctionalID`),
  KEY `Functional_APPLICATION_FK` (`AppID`),
  CONSTRAINT `Functional_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `application` */

DROP TABLE IF EXISTS `application`;

CREATE TABLE `application` (
  `AppID` int(11) NOT NULL,
  `AppName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `casestepmapping` */

DROP TABLE IF EXISTS `casestepmapping`;

CREATE TABLE `casestepmapping` (
  `CaseStepMappingID` int(11) NOT NULL,
  `TestCaseID` int(11) NOT NULL,
  `TestStepID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`CaseStepMappingID`,`TestCaseID`,`TestStepID`),
  KEY `CaseStepMapping_TestCase_FK` (`TestCaseID`),
  KEY `CaseStepMapping_TestStep` (`TestStepID`),
  CONSTRAINT `CaseStepMapping_TestStep` FOREIGN KEY (`TestStepID`) REFERENCES `teststep` (`TestStepID`),
  CONSTRAINT `CaseStepMapping_TestCase_FK` FOREIGN KEY (`TestCaseID`) REFERENCES `testcase` (`TestCaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `conditiongroup` */

DROP TABLE IF EXISTS `conditiongroup`;

CREATE TABLE `conditiongroup` (
  `ConditionGroupID` int(11) NOT NULL,
  `ConditionGroupName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ConditionGroupID`),
  KEY `ConditionGroup_APPLICATION_FK` (`AppID`),
  CONSTRAINT `ConditionGroup_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `conditions` */

DROP TABLE IF EXISTS `conditions`;

CREATE TABLE `conditions` (
  `ConditionID` int(11) NOT NULL,
  `ConditionName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Expression` varchar(500) DEFAULT NULL,
  `ConditionGroupID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ConditionID`),
  KEY `Condition_ConditionGroup_FK` (`ConditionGroupID`),
  CONSTRAINT `Condition_ConditionGroup_FK` FOREIGN KEY (`ConditionGroupID`) REFERENCES `conditiongroup` (`ConditionGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `genericdata` */

DROP TABLE IF EXISTS `genericdata`;

CREATE TABLE `genericdata` (
  `ID` int(11) NOT NULL,
  `KeyName` varchar(200) DEFAULT NULL,
  `Value` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `GenericData_Application_FK` (`AppID`),
  CONSTRAINT `GenericData_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `identifiertype` */

DROP TABLE IF EXISTS `identifiertype`;

CREATE TABLE `identifiertype` (
  `IdentifierTypeID` int(11) NOT NULL,
  `IdentifierTypeName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`IdentifierTypeID`),
  KEY `IdentifierType_APPLICATION_FK` (`AppID`),
  CONSTRAINT `IdentifierType_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `laneresults` */

DROP TABLE IF EXISTS `laneresults`;

CREATE TABLE `laneresults` (
  `LaneResultsID` int(11) NOT NULL,
  `ScheduleLaneID` int(11) NOT NULL,
  `AgentID` int(11) NOT NULL,
  `BuildVersion` varchar(200) DEFAULT NULL,
  `OS` varchar(200) DEFAULT NULL,
  `FailureDetails` varchar(500) DEFAULT NULL,
  `TestRunID` int(11) NOT NULL,
  `Result` tinyint(1) DEFAULT NULL,
  `StartDateTime` timestamp NULL DEFAULT NULL,
  `EndDateTime` timestamp NULL DEFAULT NULL,
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`LaneResultsID`),
  KEY `LaneResults_SchedulerRunDetails_FK` (`TestRunID`),
  KEY `TFWLaneResults_AgentDetails_FK` (`AgentID`),
  KEY `TFWLaneResults_Lane_FK` (`ScheduleLaneID`),
  CONSTRAINT `LaneResults_SchedulerRunDetails_FK` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TFWLaneResults_AgentDetails_FK` FOREIGN KEY (`AgentID`) REFERENCES `agentdetails` (`AgentID`),
  CONSTRAINT `TFWLaneResults_Lane_FK` FOREIGN KEY (`ScheduleLaneID`) REFERENCES `schedulerlanedetails` (`ScheduleLaneID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `objectgroup` */

DROP TABLE IF EXISTS `objectgroup`;

CREATE TABLE `objectgroup` (
  `ObjectGroupID` int(11) NOT NULL,
  `ObjectGroupName` varchar(200) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `ScreenID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ObjectGroupID`),
  KEY `ObjectGroup_APPLICATION_FK` (`AppID`),
  KEY `ObjectGroup_Screen_FK` (`ScreenID`),
  CONSTRAINT `ObjectGroup_Screen_FK` FOREIGN KEY (`ScreenID`) REFERENCES `screen` (`ScreenID`),
  CONSTRAINT `ObjectGroup_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `objects` */

DROP TABLE IF EXISTS `objects`;

CREATE TABLE `objects` (
  `ObjectID` int(11) NOT NULL,
  `ObjectName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `ObjectGroupID` int(11) NOT NULL,
  `ObjectTypeID` int(11) NOT NULL,
  `IdentifierTypeID` int(11) NOT NULL,
  `AppID` int(11) NOT NULL,
  `Identifier` varchar(4000) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ObjectID`),
  KEY `Objects_Application_FK` (`AppID`),
  KEY `Objects_IdentifierType_FK` (`IdentifierTypeID`),
  KEY `Objects_ObjectGroup_FK` (`ObjectGroupID`),
  KEY `Objects_ObjectType_FK` (`ObjectTypeID`),
  CONSTRAINT `Objects_ObjectType_FK` FOREIGN KEY (`ObjectTypeID`) REFERENCES `objecttype` (`ObjectTypeID`),
  CONSTRAINT `Objects_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`),
  CONSTRAINT `Objects_IdentifierType_FK` FOREIGN KEY (`IdentifierTypeID`) REFERENCES `identifiertype` (`IdentifierTypeID`),
  CONSTRAINT `Objects_ObjectGroup_FK` FOREIGN KEY (`ObjectGroupID`) REFERENCES `objectgroup` (`ObjectGroupID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `objecttype` */

DROP TABLE IF EXISTS `objecttype`;

CREATE TABLE `objecttype` (
  `ObjectTypeID` int(11) NOT NULL,
  `ObjectTypeName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `ActionId` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ObjectTypeID`),
  KEY `ObjectType_APPLICATION_FKv1` (`ActionId`),
  CONSTRAINT `ObjectType_APPLICATION_FKv1` FOREIGN KEY (`ActionId`) REFERENCES `actions` (`ActionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `param` */

DROP TABLE IF EXISTS `param`;

CREATE TABLE `param` (
  `ParamID` int(11) NOT NULL,
  `ParamName` varchar(200) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `ParamGroupID` int(11) NOT NULL,
  `ObjectID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ParamID`),
  KEY `Parameter_Objects_FK` (`ObjectID`),
  KEY `Parameter_ParamGroup_FK` (`ParamGroupID`),
  CONSTRAINT `Parameter_ParamGroup_FK` FOREIGN KEY (`ParamGroupID`) REFERENCES `paramgroup` (`ParamGroupID`) ON DELETE CASCADE,
  CONSTRAINT `Parameter_Objects_FK` FOREIGN KEY (`ObjectID`) REFERENCES `objects` (`ObjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `paramgroup` */

DROP TABLE IF EXISTS `paramgroup`;

CREATE TABLE `paramgroup` (
  `ParamGroupID` int(11) NOT NULL,
  `ParamGroupName` varchar(200) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Tag` varchar(50) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `ObjectGroupID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ParamGroupID`),
  KEY `ParamGroup_APPLICATION_FK` (`AppID`),
  KEY `ParamGroup_ObjectGroup_FK` (`ObjectGroupID`),
  CONSTRAINT `ParamGroup_ObjectGroup_FK` FOREIGN KEY (`ObjectGroupID`) REFERENCES `objectgroup` (`ObjectGroupID`),
  CONSTRAINT `ParamGroup_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `plansuitemapping` */

DROP TABLE IF EXISTS `plansuitemapping`;

CREATE TABLE `plansuitemapping` (
  `PlanSuiteMappingID` int(11) NOT NULL,
  `TestPlanID` int(11) NOT NULL,
  `TestSuiteID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`PlanSuiteMappingID`,`TestPlanID`,`TestSuiteID`),
  KEY `TABLE_43_TestPlan_FK` (`TestPlanID`),
  KEY `TABLE_43_TestSuite_FK` (`TestSuiteID`),
  CONSTRAINT `TABLE_43_TestSuite_FK` FOREIGN KEY (`TestSuiteID`) REFERENCES `testsuite` (`TestSuiteID`),
  CONSTRAINT `TABLE_43_TestPlan_FK` FOREIGN KEY (`TestPlanID`) REFERENCES `testplan` (`TestPlanID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `replacement_strings` */

DROP TABLE IF EXISTS `replacement_strings`;

CREATE TABLE `replacement_strings` (
  `ID` int(11) NOT NULL,
  `AppID` int(11) NOT NULL,
  `Level` varchar(50) DEFAULT NULL,
  `Foreign_ID` varchar(50) DEFAULT NULL,
  `Name` varchar(500) DEFAULT NULL,
  `Value` varchar(500) DEFAULT NULL,
  `ENCRYPTED` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Replacement_Strings_Application_FK` (`AppID`),
  CONSTRAINT `Replacement_Strings_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `RoleID` int(11) NOT NULL,
  `Authority` varchar(50) DEFAULT NULL,
  `RolesDescription` varchar(200) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `runner` */

DROP TABLE IF EXISTS `runner`;

CREATE TABLE `runner` (
  `RunnerID` int(11) NOT NULL,
  `RunnerName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`RunnerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `runners_deviceid_gen` */

DROP TABLE IF EXISTS `runners_deviceid_gen`;

CREATE TABLE `runners_deviceid_gen` (
  `TableID` int(11) NOT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `StartRange` int(11) DEFAULT NULL,
  `EndRange` int(11) DEFAULT NULL,
  `DateAdded` date DEFAULT NULL,
  `ExtraID` varchar(50) DEFAULT NULL,
  `Info` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`TableID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `scenariocasemapping` */

DROP TABLE IF EXISTS `scenariocasemapping`;

CREATE TABLE `scenariocasemapping` (
  `ScenarioCaseMappingID` int(11) NOT NULL,
  `TestScenarioID` int(11) NOT NULL,
  `TestCaseID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ScenarioCaseMappingID`,`TestScenarioID`,`TestCaseID`),
  KEY `ScenarioCaseMapping_TestCase_FK` (`TestCaseID`),
  KEY `ScenarioCaseMapping_TestScenario_FK` (`TestScenarioID`),
  CONSTRAINT `ScenarioCaseMapping_TestScenario_FK` FOREIGN KEY (`TestScenarioID`) REFERENCES `testscenario` (`TestScenarioID`),
  CONSTRAINT `ScenarioCaseMapping_TestCase_FK` FOREIGN KEY (`TestCaseID`) REFERENCES `testcase` (`TestCaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `scheduler` */

DROP TABLE IF EXISTS `scheduler`;

CREATE TABLE `scheduler` (
  `ScheduleID` int(11) NOT NULL,
  `SchedulerName` varchar(500) DEFAULT NULL,
  `TestPlanID` int(11) NOT NULL,
  `TestDataID` int(11) NOT NULL,
  `AgentID` int(11) NOT NULL,
  `AppID` int(11) NOT NULL,
  `ScheduleTime` timestamp NULL DEFAULT NULL,
  `Status` varchar(1) DEFAULT NULL,
  `Frequency` varchar(200) DEFAULT NULL,
  `Notifications` varchar(500) DEFAULT NULL,
  `MultiLanes` char(1) DEFAULT NULL,
  `FailureCount` int(11) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ScheduleID`),
  KEY `Scheduler_AgentDetails_FK` (`AgentID`),
  KEY `Scheduler_Application_FK` (`AppID`),
  KEY `Scheduler_TestData_FK` (`TestDataID`),
  KEY `Scheduler_TestPlan_FK` (`TestPlanID`),
  CONSTRAINT `Scheduler_TestPlan_FK` FOREIGN KEY (`TestPlanID`) REFERENCES `testplan` (`TestPlanID`),
  CONSTRAINT `Scheduler_AgentDetails_FK` FOREIGN KEY (`AgentID`) REFERENCES `agentdetails` (`AgentID`),
  CONSTRAINT `Scheduler_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`),
  CONSTRAINT `Scheduler_TestData_FK` FOREIGN KEY (`TestDataID`) REFERENCES `testdata` (`TestDataID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `schedulerbackup` */

DROP TABLE IF EXISTS `schedulerbackup`;

CREATE TABLE `schedulerbackup` (
  `SchedulerbackupID` int(11) NOT NULL,
  `SchedulerName` varchar(500) DEFAULT NULL,
  `TestPlanID` int(11) DEFAULT NULL,
  `TestDataID` int(11) DEFAULT NULL,
  `AgentID` int(11) DEFAULT NULL,
  `AppID` int(11) DEFAULT NULL,
  `ScheduleTime` timestamp NULL DEFAULT NULL,
  `Status` varchar(1) DEFAULT NULL,
  `Frequency` varchar(200) DEFAULT NULL,
  `Notifications` varchar(500) DEFAULT NULL,
  `MultiLanes` char(1) DEFAULT NULL,
  `FailureCount` int(11) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`SchedulerbackupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `schedulerlanedetails` */

DROP TABLE IF EXISTS `schedulerlanedetails`;

CREATE TABLE `schedulerlanedetails` (
  `ScheduleLaneID` int(11) NOT NULL,
  `LaneUserName` varchar(50) DEFAULT NULL,
  `ScheduleID` int(11) NOT NULL,
  `AgentID` int(11) NOT NULL,
  `LaneType` varchar(50) DEFAULT NULL,
  `RunnerType` varchar(100) DEFAULT NULL,
  `Clones` int(11) DEFAULT NULL,
  `Iterations` int(11) DEFAULT NULL,
  `RampUpDelay` int(11) DEFAULT NULL,
  `Duration` int(11) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ScheduleLaneID`),
  KEY `SchedulerLaneDetails_AgentDetails_FK` (`AgentID`),
  KEY `SchedulerLaneDetails_Scheduler_FK` (`ScheduleID`),
  CONSTRAINT `SchedulerLaneDetails_Scheduler_FK` FOREIGN KEY (`ScheduleID`) REFERENCES `scheduler` (`ScheduleID`),
  CONSTRAINT `SchedulerLaneDetails_AgentDetails_FK` FOREIGN KEY (`AgentID`) REFERENCES `agentdetails` (`AgentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `schedulerrundetails` */

DROP TABLE IF EXISTS `schedulerrundetails`;

CREATE TABLE `schedulerrundetails` (
  `TestRunID` int(11) NOT NULL,
  `TestPlanID` int(11) NOT NULL,
  `TestDataID` int(11) NOT NULL,
  `ScheduleID` int(11) NOT NULL,
  `RunTime` date DEFAULT NULL,
  `Result` tinyint(1) DEFAULT NULL,
  `StartDateTime` timestamp NULL DEFAULT NULL,
  `EndDateTime` timestamp NULL DEFAULT NULL,
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  PRIMARY KEY (`TestRunID`),
  KEY `SchedulerRunDetails_Application_FK` (`AppID`),
  KEY `SchedulerRunDetails_TestPlan_FK` (`TestPlanID`),
  KEY `SchedulerRunDetails_Scheduler_FK` (`ScheduleID`),
  KEY `SchedulerRunDetails_TestData_FK` (`TestDataID`),
  CONSTRAINT `SchedulerRunDetails_TestData_FK` FOREIGN KEY (`TestDataID`) REFERENCES `testdata` (`TestDataID`),
  CONSTRAINT `SchedulerRunDetails_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`),
  CONSTRAINT `SchedulerRunDetails_Scheduler_FK` FOREIGN KEY (`ScheduleID`) REFERENCES `scheduler` (`ScheduleID`),
  CONSTRAINT `SchedulerRunDetails_TestPlan_FK` FOREIGN KEY (`TestPlanID`) REFERENCES `testplan` (`TestPlanID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `screen` */

DROP TABLE IF EXISTS `screen`;

CREATE TABLE `screen` (
  `ScreenID` int(11) NOT NULL,
  `ScreenName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `FeatureID` int(11) NOT NULL,
  `AppID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`ScreenID`),
  KEY `Screen_APPLICATION_FK` (`AppID`),
  KEY `Screen_AppFeature_FK` (`FeatureID`),
  CONSTRAINT `Screen_AppFeature_FK` FOREIGN KEY (`FeatureID`) REFERENCES `appfeature` (`FeatureID`),
  CONSTRAINT `Screen_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `suitescenariomapping` */

DROP TABLE IF EXISTS `suitescenariomapping`;

CREATE TABLE `suitescenariomapping` (
  `SuiteScenarioMappingID` int(11) NOT NULL,
  `TestSuiteID` int(11) NOT NULL,
  `TestScenarioID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`SuiteScenarioMappingID`,`TestSuiteID`,`TestScenarioID`),
  KEY `SuiteScenarioMapping_TestPlan_FKv1` (`TestScenarioID`),
  KEY `SuiteScenarioMapping_TestSuite_FK` (`TestSuiteID`),
  CONSTRAINT `SuiteScenarioMapping_TestSuite_FK` FOREIGN KEY (`TestSuiteID`) REFERENCES `testsuite` (`TestSuiteID`),
  CONSTRAINT `SuiteScenarioMapping_TestPlan_FKv1` FOREIGN KEY (`TestScenarioID`) REFERENCES `testscenario` (`TestScenarioID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `TaskID` int(11) NOT NULL,
  `TaskName` varchar(50) DEFAULT NULL,
  `LaneID` int(11) NOT NULL,
  `TestPlanXlsPath` varchar(200) DEFAULT NULL,
  `DataSet` varchar(50) DEFAULT NULL,
  `RepeatNo` int(11) DEFAULT NULL,
  `TagsToRun` varchar(200) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TaskID`),
  KEY `TABLE_32_Lane_FK` (`LaneID`),
  CONSTRAINT `TABLE_32_Lane_FK` FOREIGN KEY (`LaneID`) REFERENCES `schedulerlanedetails` (`ScheduleLaneID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `taskresult` */

DROP TABLE IF EXISTS `taskresult`;

CREATE TABLE `taskresult` (
  `TaskResultID` int(11) NOT NULL,
  `TaskID` int(11) NOT NULL,
  `LaneID` int(11) NOT NULL,
  `RunResultID` varchar(500) DEFAULT NULL,
  `ReportFilePath` varchar(500) DEFAULT NULL,
  `TestRunID` int(11) NOT NULL,
  `Result` tinyint(1) DEFAULT NULL,
  `StartDateTime` timestamp NULL DEFAULT NULL,
  `EndDateTime` timestamp NULL DEFAULT NULL,
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`TaskResultID`),
  KEY `TABLE_33_Task_FK` (`TaskID`),
  KEY `TaskResult_LaneResults_FKv1` (`TestRunID`),
  KEY `TaskResult_SchedulerLaneDetails_FK` (`LaneID`),
  CONSTRAINT `TABLE_33_Task_FK` FOREIGN KEY (`TaskID`) REFERENCES `task` (`TaskID`),
  CONSTRAINT `TaskResult_LaneResults_FKv1` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TaskResult_SchedulerLaneDetails_FK` FOREIGN KEY (`LaneID`) REFERENCES `schedulerlanedetails` (`ScheduleLaneID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `test_hits` */

DROP TABLE IF EXISTS `test_hits`;

CREATE TABLE `test_hits` (
  `LogTime` date DEFAULT NULL,
  `DeviceId` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testcase` */

DROP TABLE IF EXISTS `testcase`;

CREATE TABLE `testcase` (
  `TestCaseID` int(11) NOT NULL,
  `CaseName` varchar(500) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `ClassificationTag` varchar(500) DEFAULT NULL,
  `RunnerID` int(11) NOT NULL,
  `ConditionGroupID` int(11) NOT NULL,
  `FunctionalID` int(11) NOT NULL,
  `FeatureID` int(11) NOT NULL,
  `Active` char(1) DEFAULT NULL,
  `Positive` char(1) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestCaseID`),
  KEY `TestCase_AppFeature_FK` (`FeatureID`),
  KEY `TestCase_AppFunctionality_FK` (`FunctionalID`),
  KEY `TestCase_ConditionGroup_FK` (`ConditionGroupID`),
  KEY `TestCase_Runner_FK` (`RunnerID`),
  CONSTRAINT `TestCase_Runner_FK` FOREIGN KEY (`RunnerID`) REFERENCES `runner` (`RunnerID`),
  CONSTRAINT `TestCase_AppFeature_FK` FOREIGN KEY (`FeatureID`) REFERENCES `appfeature` (`FeatureID`),
  CONSTRAINT `TestCase_AppFunctionality_FK` FOREIGN KEY (`FunctionalID`) REFERENCES `appfunctionality` (`FunctionalID`),
  CONSTRAINT `TestCase_ConditionGroup_FK` FOREIGN KEY (`ConditionGroupID`) REFERENCES `conditiongroup` (`ConditionGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testcaseresult` */

DROP TABLE IF EXISTS `testcaseresult`;

CREATE TABLE `testcaseresult` (
  `TestCaseResultID` int(11) NOT NULL,
  `TestCaseID` int(11) NOT NULL,
  `TestScenarioID` int(11) NOT NULL,
  `TestRunID` int(11) NOT NULL,
  `Comment` varchar(4000) DEFAULT NULL,
  `EXCEPTION` varchar(4000) DEFAULT NULL,
  `Request` varchar(4000) DEFAULT NULL,
  `Response` varchar(4000) DEFAULT NULL,
  `Result` int(11) DEFAULT NULL,
  `StartDateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `EndDateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  `PercentageSkipCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`TestCaseResultID`),
  KEY `TestCaseResult_TestScenarioResult_FK` (`TestRunID`),
  KEY `TestCaseResult_TestCase_FK` (`TestCaseID`),
  KEY `TestCaseResult_TestScenario_FK` (`TestScenarioID`),
  CONSTRAINT `TestCaseResult_TestScenario_FK` FOREIGN KEY (`TestScenarioID`) REFERENCES `testscenario` (`TestScenarioID`),
  CONSTRAINT `TestCaseResult_SchedulerRunDetails_FK` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TestCaseResult_TestCase_FK` FOREIGN KEY (`TestCaseID`) REFERENCES `testcase` (`TestCaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testconditiondata` */

DROP TABLE IF EXISTS `testconditiondata`;

CREATE TABLE `testconditiondata` (
  `TestConditionDataID` int(11) NOT NULL,
  `TestDataID` int(11) NOT NULL,
  `ConditionGroupID` int(11) NOT NULL,
  `ConditionID` int(11) NOT NULL,
  `ConditionValue` varchar(200) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestConditionDataID`),
  KEY `TestData_FK` (`TestDataID`),
  KEY `TestCondition_FK` (`ConditionID`),
  KEY `TestCondData_ConditionGroup_FK` (`ConditionGroupID`),
  CONSTRAINT `TestCondData_TestData_FK` FOREIGN KEY (`TestDataID`) REFERENCES `testdata` (`TestDataID`),
  CONSTRAINT `TestCondData_ConditionGroup_FK` FOREIGN KEY (`ConditionGroupID`) REFERENCES `conditiongroup` (`ConditionGroupID`),
  CONSTRAINT `TestCondData_Conditions_FK` FOREIGN KEY (`ConditionID`) REFERENCES `conditions` (`ConditionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testdata` */

DROP TABLE IF EXISTS `testdata`;

CREATE TABLE `testdata` (
  `TestDataID` int(11) NOT NULL,
  `AppID` int(11) NOT NULL,
  `TestDataDescription` varchar(200) DEFAULT NULL,
  `Status` varchar(1) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestDataID`),
  KEY `TestData_Application_FK` (`AppID`),
  CONSTRAINT `TestData_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testparamdata` */

DROP TABLE IF EXISTS `testparamdata`;

CREATE TABLE `testparamdata` (
  `TestParamDataID` int(11) NOT NULL,
  `ParamGroupID` int(11) DEFAULT NULL,
  `TestDataID` int(11) NOT NULL,
  `ParamID` int(11) NOT NULL,
  `ParamValue` varchar(500) DEFAULT NULL,
  `ValueBig` varchar(500) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestParamDataID`),
  KEY `TestParamData_ParamGroup_FKv1` (`TestDataID`),
  CONSTRAINT `TestParamData_ParamGroup_FKv1` FOREIGN KEY (`TestDataID`) REFERENCES `testdata` (`TestDataID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testplan` */

DROP TABLE IF EXISTS `testplan`;

CREATE TABLE `testplan` (
  `TestPlanID` int(11) NOT NULL,
  `PlanName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `PreConditionGroupID` int(11) NOT NULL,
  `PostConditionGroupID` int(11) NOT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestPlanID`),
  KEY `TestPlan_ConditionGroup_FK` (`PreConditionGroupID`),
  KEY `TestPlan_ConditionGroup_FKv2` (`PostConditionGroupID`),
  KEY `AppID` (`AppID`),
  CONSTRAINT `AppID_Application11` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`),
  CONSTRAINT `TestPlan_ConditionGroup_FK` FOREIGN KEY (`PreConditionGroupID`) REFERENCES `conditiongroup` (`ConditionGroupID`),
  CONSTRAINT `TestPlan_ConditionGroup_FKv2` FOREIGN KEY (`PostConditionGroupID`) REFERENCES `conditiongroup` (`ConditionGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testplanresult` */

DROP TABLE IF EXISTS `testplanresult`;

CREATE TABLE `testplanresult` (
  `TestPlanResultID` int(11) NOT NULL,
  `TestPlanRunName` varchar(100) DEFAULT NULL,
  `TestPlanID` int(11) NOT NULL,
  `TaskID` int(11) NOT NULL,
  `TestRunID` int(11) NOT NULL,
  `Result` tinyint(1) DEFAULT NULL,
  `StartDateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `EndDateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`TestPlanResultID`),
  KEY `TestPlanResult_SchedulerRunDetails_FK` (`TestRunID`),
  KEY `TestPlanResult_TaskResult_FKv1` (`TestPlanID`),
  KEY `TestPlanResult_Task_FK` (`TaskID`),
  CONSTRAINT `TestPlanResult_Task_FK` FOREIGN KEY (`TaskID`) REFERENCES `task` (`TaskID`),
  CONSTRAINT `TestPlanResult_SchedulerRunDetails_FK` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TestPlanResult_TaskResult_FKv1` FOREIGN KEY (`TestPlanID`) REFERENCES `testplan` (`TestPlanID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testscenario` */

DROP TABLE IF EXISTS `testscenario`;

CREATE TABLE `testscenario` (
  `TestScenarioID` int(11) NOT NULL,
  `TestScenarioName` varchar(500) DEFAULT NULL,
  `Description` varchar(500) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestScenarioID`),
  KEY `TestScenario_Application_FK` (`AppID`),
  CONSTRAINT `TestScenario_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testscenarioresult` */

DROP TABLE IF EXISTS `testscenarioresult`;

CREATE TABLE `testscenarioresult` (
  `TestScenarioResultID` int(11) NOT NULL,
  `TestScenarioID` int(11) NOT NULL,
  `TestSuiteID` int(11) NOT NULL,
  `TestRunID` int(11) NOT NULL,
  `Result` tinyint(1) DEFAULT NULL,
  `StartDateTime` timestamp NULL DEFAULT NULL,
  `EndDateTime` timestamp NULL DEFAULT NULL,
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  `PercentageSkipCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`TestScenarioResultID`),
  KEY `TestScenarioResult_SchedulerRunDetails_FK` (`TestRunID`),
  KEY `TestScenarioResult_TestScenario_FK` (`TestScenarioID`),
  KEY `TestScenarioResult_TestSuite_FK` (`TestSuiteID`),
  CONSTRAINT `TestScenarioResult_SchedulerRunDetails_FK` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TestScenarioResult_TestScenario_FK` FOREIGN KEY (`TestScenarioID`) REFERENCES `testscenario` (`TestScenarioID`),
  CONSTRAINT `TestScenarioResult_TestSuite_FK` FOREIGN KEY (`TestSuiteID`) REFERENCES `testsuite` (`TestSuiteID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `teststep` */

DROP TABLE IF EXISTS `teststep`;

CREATE TABLE `teststep` (
  `TestStepID` int(11) NOT NULL,
  `StepName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `TestStepType` varchar(100) DEFAULT NULL,
  `ActionID` int(11) NOT NULL,
  `RunnerID` int(11) NOT NULL,
  `InputParamGroupID` int(11) NOT NULL,
  `OutputParamGroupID` int(11) DEFAULT NULL,
  `Active` char(1) DEFAULT NULL,
  `SortOrder` int(11) DEFAULT NULL,
  `PreConditionGroupID` int(11) DEFAULT NULL,
  `PostConditionGroupID` int(11) DEFAULT NULL,
  `ExpectedResult` varchar(50) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestStepID`),
  KEY `TestStep_Actions_FK` (`ActionID`),
  KEY `TestStep_ParamGroup` (`InputParamGroupID`),
  KEY `TestStep_Runner_FKv1` (`RunnerID`),
  CONSTRAINT `TestStep_Runner_FKv1` FOREIGN KEY (`RunnerID`) REFERENCES `runner` (`RunnerID`),
  CONSTRAINT `TestStep_Actions_FK` FOREIGN KEY (`ActionID`) REFERENCES `actions` (`ActionID`),
  CONSTRAINT `TestStep_ParamGroup` FOREIGN KEY (`InputParamGroupID`) REFERENCES `paramgroup` (`ParamGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `teststepresult` */

DROP TABLE IF EXISTS `teststepresult`;

CREATE TABLE `teststepresult` (
  `TestStepResultID` int(11) NOT NULL,
  `TestStepID` int(11) NOT NULL,
  `TestCaseID` int(11) NOT NULL,
  `TestRunID` int(11) NOT NULL,
  `Comment` varchar(4000) DEFAULT NULL,
  `EXCEPTION` varchar(4000) DEFAULT NULL,
  `Duration` int(11) DEFAULT NULL,
  `Request` varchar(4000) DEFAULT NULL,
  `Response` varchar(4000) DEFAULT NULL,
  `Result` int(11) DEFAULT NULL,
  `StartDateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `EndDateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`TestStepResultID`),
  KEY `TestStepResult_TestCaseResult_FK` (`TestCaseID`),
  KEY `TestStepResult_SchedulerRunDetails_FK` (`TestRunID`),
  KEY `TestStepResult_TestCaseResult_FKv1` (`TestStepID`),
  CONSTRAINT `TestStepResult_TestCase_FK` FOREIGN KEY (`TestCaseID`) REFERENCES `testcase` (`TestCaseID`),
  CONSTRAINT `TestStepResult_SchedulerRunDetails_FK` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TestStepResult_TestCaseResult_FKv1` FOREIGN KEY (`TestStepID`) REFERENCES `teststep` (`TestStepID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testsuite` */

DROP TABLE IF EXISTS `testsuite`;

CREATE TABLE `testsuite` (
  `TestSuiteID` int(11) NOT NULL,
  `SuiteName` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `AppID` int(11) NOT NULL,
  `SortOrder` int(1) DEFAULT NULL,
  `CreatedBy` varchar(100) DEFAULT NULL,
  `CreatedDateTime` date DEFAULT NULL,
  `UpdatedBy` varchar(100) DEFAULT NULL,
  `UpdatedDateTime` date DEFAULT NULL,
  PRIMARY KEY (`TestSuiteID`),
  KEY `TestSuite_Application_FKv1` (`AppID`),
  CONSTRAINT `TestSuite_APPLICATION_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`) ON DELETE CASCADE,
  CONSTRAINT `TestSuite_Application_FKv1` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `testsuiteresult` */

DROP TABLE IF EXISTS `testsuiteresult`;

CREATE TABLE `testsuiteresult` (
  `TestSuiteResultID` int(11) NOT NULL,
  `TestSuiteID` int(11) NOT NULL,
  `TestPlanID` int(11) NOT NULL,
  `TestRunID` int(11) NOT NULL,
  `Result` tinyint(1) DEFAULT NULL,
  `StartDateTime` timestamp NULL DEFAULT NULL,
  `EndDateTime` timestamp NULL DEFAULT NULL,
  `PercentagePassCount` int(11) DEFAULT NULL,
  `PercentageFailCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`TestSuiteResultID`),
  KEY `TestSuiteResult_SchedulerRunDetails_FK` (`TestRunID`),
  KEY `TestSuiteResult_TestPlanResult_FKv1` (`TestSuiteID`),
  KEY `TestSuiteResult_TestPlan_FK` (`TestPlanID`),
  CONSTRAINT `TestSuiteResult_SchedulerRunDetails_FK` FOREIGN KEY (`TestRunID`) REFERENCES `schedulerrundetails` (`TestRunID`),
  CONSTRAINT `TestSuiteResult_TestPlanResult_FKv1` FOREIGN KEY (`TestSuiteID`) REFERENCES `testsuite` (`TestSuiteID`),
  CONSTRAINT `TestSuiteResult_TestPlan_FK` FOREIGN KEY (`TestPlanID`) REFERENCES `testplan` (`TestPlanID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `user_roles` */

DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE `user_roles` (
  `USER_ROLE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `AUTHORITY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ROLE_ID`),
  KEY `user_roles_Users_FK` (`USER_ID`),
  CONSTRAINT `user_roles_Users_FK` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `USER_ID` int(11) NOT NULL,
  `USERNAME` varchar(500) DEFAULT NULL,
  `PASSWORD` varchar(500) DEFAULT NULL,
  `ENABLED` tinyint(1) DEFAULT NULL,
  `EMAIL_ID` varchar(100) DEFAULT NULL,
  `PASSWORD_COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `usersapplicationmapping` */

DROP TABLE IF EXISTS `usersapplicationmapping`;

CREATE TABLE `usersapplicationmapping` (
  `UsersApplicationMappingID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `AppID` int(11) NOT NULL,
  `AUTHORITY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`UsersApplicationMappingID`),
  KEY `UsersApplicationMapping_Application_FK` (`AppID`),
  KEY `UsersApplicationMapping_Users_FK` (`USER_ID`),
  CONSTRAINT `UsersApplicationMapping_Users_FK` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`),
  CONSTRAINT `UsersApplicationMapping_Application_FK` FOREIGN KEY (`AppID`) REFERENCES `application` (`AppID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* Procedure structure for procedure `scheduler_details` */

/*!50003 DROP PROCEDURE IF EXISTS  `scheduler_details` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `scheduler_details`(IN AppID INT(10))
BEGIN
        select s1.* from (select  s.SchedulerName, s.Scheduler_backupID as ScheduleID, @DateTIme as ScheduleTime, s.Frequency, s.Status, s.AppID, s.FailureCount,
        srd.Result, srd.StartDateTime, srd.EndDateTime, TIMESTAMPDIFF(SECOND, srd.StartDateTime, srd.EndDateTime) as DurationInSec, 
        tp.TestPlanID, tp.PlanName, td.TestDataID, td.TestDataDescription, ad.AgentName, ad.AgentID from Scheduler_backup s, 
        SchedulerRunDetails srd, TestPlan tp, TestData td, AgentDetails ad, Application a where s.TestPlanID=tp.TestPlanID and s.Scheduler_backupID=srd.ScheduleID and
        s.TestDataID=td.TestDataID and s.AgentID=ad.AgentID and s.AppID=a.AppID and s.AppID=AppID
        union (select  s11.SchedulerName, s11.ScheduleID, s11.ScheduleTime, s11.Frequency, s11.Status, s11.AppID, s11.FailureCount,
        srd11.Result, srd11.StartDateTime, srd11.EndDateTime, TIMESTAMPDIFF(SECOND, srd11.StartDateTime, srd11.EndDateTime) as DurationInSec11,
        tp11.TestPlanID, tp11.PlanName,
        td11.TestDataID, td11.TestDataDescription,
        ad11.AgentName, ad11.AgentID
        from Scheduler s11, SchedulerRunDetails srd11, TestPlan tp11, TestData td11, AgentDetails ad11, Application a11
        where s11.TestPlanID=tp11.TestPlanID and s11.TestDataID=td11.TestDataID and s11.AgentID=ad11.AgentID
        and s11.AppID=a11.AppID and s11.AppID=AppID)
        union (select s.SchedulerName, s.Scheduler_backupID  as ScheduleID, @DateTIme as ScheduleTime, s.Frequency, s.Status, s.AppID, s.FailureCount, 
        0 as Result, s.ScheduleTime as StartDateTime, s.ScheduleTime as EndDateTime, 0 as DurationInSec, 
        tp.TestPlanID, tp.PlanName, td.TestDataID, td.TestDataDescription, ad.AgentName, ad.AgentID
        from Scheduler_backup s, TestPlan tp, TestData td, AgentDetails ad
        where s.Scheduler_backupID not in (select distinct srd.ScheduleID from SchedulerRunDetails srd) 
        and s.AppID=AppID and s.TestPlanID=tp.TestPlanID and s.TestDataID=td.TestDataID and s.AgentID=ad.AgentID)) s1 
        group by s1.ScheduleID limit 30;
END */$$
DELIMITER ;

/* Procedure structure for procedure `scheduler_details_completed` */

/*!50003 DROP PROCEDURE IF EXISTS  `scheduler_details_completed` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `scheduler_details_completed`(IN AppID INT(10))
BEGIN
        select  s.SchedulerName, s.Scheduler_backupID as ScheduleID, s.ScheduleTime, s.Frequency, s.Status, s.AppID, s.FailureCount,
        srd.Result, srd.StartDateTime, srd.EndDateTime, TIMESTAMPDIFF(SECOND, srd.StartDateTime, srd.EndDateTime) as DurationInSec, 
        tp.TestPlanID, tp.PlanName, td.TestDataID, td.TestDataDescription, ad.AgentName, ad.AgentID from Scheduler_backup s, 
        SchedulerRunDetails srd, TestPlan tp, TestData td, AgentDetails ad, Application a where s.TestPlanID=tp.TestPlanID and s.Scheduler_backupID=srd.ScheduleID and
        s.TestDataID=td.TestDataID and s.AgentID=ad.AgentID and s.AppID=a.AppID and s.AppID=AppID;
END */$$
DELIMITER ;

/* Procedure structure for procedure `scheduler_details_running` */

/*!50003 DROP PROCEDURE IF EXISTS  `scheduler_details_running` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `scheduler_details_running`(IN AppID INT(10))
BEGIN
        select  s11.SchedulerName, s11.ScheduleID, s11.ScheduleTime, s11.Frequency, s11.Status, s11.AppID, s11.FailureCount,
        srd11.StartDateTime, tp11.TestPlanID, tp11.PlanName,
        td11.TestDataID, td11.TestDataDescription, ad11.AgentName, ad11.AgentID
        from Scheduler s11, SchedulerRunDetails srd11, TestPlan tp11, TestData td11, AgentDetails ad11, Application a11
        where s11.TestPlanID=tp11.TestPlanID and s11.TestDataID=td11.TestDataID and s11.AgentID=ad11.AgentID and s11.ScheduleID=srd11.ScheduleID
        and s11.AppID=a11.AppID and srd11.EndDateTime is null and s11.AppID=AppID;
END */$$
DELIMITER ;

/* Procedure structure for procedure `scheduler_details_scheduled` */

/*!50003 DROP PROCEDURE IF EXISTS  `scheduler_details_scheduled` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `scheduler_details_scheduled`(IN AppID INT(10))
BEGIN        
        select  s11.SchedulerName, s11.ScheduleID, s11.ScheduleTime, s11.Frequency, s11.Status, s11.AppID, s11.FailureCount,
        tp11.TestPlanID, tp11.PlanName,
        td11.TestDataID, td11.TestDataDescription,
        ad11.AgentName, ad11.AgentID
        from Scheduler s11, TestPlan tp11, TestData td11, AgentDetails ad11, Application a11
        where s11.TestPlanID=tp11.TestPlanID and s11.TestDataID=td11.TestDataID and s11.AgentID=ad11.AgentID
        and s11.AppID=a11.AppID and s11.AppID=AppID;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
