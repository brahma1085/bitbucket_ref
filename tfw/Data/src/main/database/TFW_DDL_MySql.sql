---- version 001-12-Aug-2013:end

-- BEGIN TABLE APPLICATION
CREATE TABLE APPLICATION
(
   AppID            INT            NOT NULL,
   AppName          VARCHAR(50),
   Description      VARCHAR(200),
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE APPLICATION
   ADD CONSTRAINT pk_application
   PRIMARY KEY (AppID);

alter table APPLICATION modify AppID INT NOT NULL AUTO_INCREMENT;
-- END TABLE APPLICATION

-- BEGIN TABLE Actions
CREATE TABLE Actions
(
   ActionId         INT            NOT NULL,
   ActionName       VARCHAR(50)    NOT NULL,
   Description      VARCHAR(200),
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE Actions
   ADD CONSTRAINT pk_actions
   PRIMARY KEY (ActionId);

alter table Actions modify ActionId INT    NOT NULL AUTO_INCREMENT;
-- END TABLE Actions

-- BEGIN TABLE AgentDetails
CREATE TABLE AgentDetails
(
   AgentId          INT            NOT NULL,
   AgentName        VARCHAR(50)    NOT NULL,
   IP               VARCHAR(20),
   Port             INT,
   Instance         VARCHAR(50),
   TestPlanID       INT            NOT NULL,
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE AgentDetails
   ADD CONSTRAINT pk_agentdetails
   PRIMARY KEY (AgentId);

CREATE INDEX AgectDetails_TestPlan_FK
   ON AgentDetails (TestPlanID ASC);

alter table AgentDetails modify AgentId INT NOT NULL AUTO_INCREMENT;
-- END TABLE AgentDetails

-- BEGIN TABLE ConditionGroup
CREATE TABLE ConditionGroup
(
   CondGrpId        INT            NOT NULL,
   CondGrpName      VARCHAR(50)    NOT NULL,
   Description      VARCHAR(200),
   AppID            INT            NOT NULL,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE ConditionGroup
   ADD CONSTRAINT pk_conditiongroup
   PRIMARY KEY (CondGrpId);

CREATE INDEX ConditionGroup_APPLICATION_FK
   ON ConditionGroup (AppID ASC);

alter table ConditionGroup modify CondGrpId INT NOT NULL AUTO_INCREMENT;
-- END TABLE ConditionGroup

-- BEGIN TABLE Conditions
CREATE TABLE Conditions
(
   CondId           INT            NOT NULL,
   CondName         VARCHAR(50),
   Description      VARCHAR(200),
   Expression       VARCHAR(500),
   CondGrpId        INT            NOT NULL,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE Conditions
   ADD CONSTRAINT pk_conditions
   PRIMARY KEY (CondId);

CREATE INDEX Condition_ConditionGroup_FK
   ON Conditions (CondGrpId ASC);

alter table Conditions modify CondId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Conditions

-- BEGIN TABLE Feature
CREATE TABLE Feature
(
   FeatureId        INT            NOT NULL,
   FeatureName      VARCHAR(50)    NOT NULL,
   Description      VARCHAR(200),
   FunctionalId     INT            NOT NULL,
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE Feature
   ADD CONSTRAINT pk_feature
   PRIMARY KEY (FeatureId);

CREATE INDEX Feature_Functional_FK
   ON Feature (FunctionalId ASC);

alter table Feature modify FeatureId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Feature

-- BEGIN TABLE Functional
CREATE TABLE Functional
(
   FunctionalId     INT            NOT NULL,
   FunctionalName   VARCHAR(50)    NOT NULL,
   Description      VARCHAR(200),
   AppID            INT            NOT NULL,
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE Functional
   ADD CONSTRAINT pk_functional
   PRIMARY KEY (FunctionalId);

CREATE INDEX Functional_APPLICATION_FK
   ON Functional (AppID ASC);

alter table Functional modify FunctionalId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Functional

-- BEGIN TABLE IdentifierType
CREATE TABLE IdentifierType
(
   IdentifierTypeId     INT            NOT NULL,
   IndentifierTypeName  VARCHAR(50),
   Description          VARCHAR(200),
   AppID                INT,
   CreatedBy            VARCHAR(100),
   CreatedDateTime      DATE,
   UpdatedBy            VARCHAR(100),
   UpdatedDateTime      DATE
)
ENGINE=MyISAM;

ALTER TABLE IdentifierType
   ADD CONSTRAINT pk_identifiertype
   PRIMARY KEY (IdentifierTypeId);

CREATE INDEX IdentifierType_APPLICATION_FK
   ON IdentifierType (AppID ASC);

alter table IdentifierType modify IdentifierTypeId INT NOT NULL AUTO_INCREMENT;
-- END TABLE IdentifierType

-- BEGIN TABLE ObjectGroup
CREATE TABLE ObjectGroup
(
   ObjectGroupId    INT            NOT NULL,
   ObjectGroupName  VARCHAR(200)   NOT NULL,
   Description      VARCHAR(200),
   AppID            INT            NOT NULL,
   ScreenId         INT            NOT NULL,
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE ObjectGroup
   ADD CONSTRAINT pk_objectgroup
   PRIMARY KEY (ObjectGroupId);

CREATE INDEX ObjectGroup_APPLICATION_FK
   ON ObjectGroup (AppID ASC);

CREATE INDEX ObjectGroup_Screen_FK
   ON ObjectGroup (ScreenId ASC);

alter table ObjectGroup modify ObjectGroupId INT NOT NULL AUTO_INCREMENT;
-- END TABLE ObjectGroup

-- BEGIN TABLE ObjectType
CREATE TABLE ObjectType
(
   ObjectTypeId     INT            NOT NULL,
   ObjectTypeName   VARCHAR(50),
   Description      VARCHAR(200),
   AppID            INT,
   DefaultActionId  INT,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE ObjectType
   ADD CONSTRAINT pk_objecttype
   PRIMARY KEY (ObjectTypeId);

CREATE INDEX ObjectType_tst_Actions_FK
   ON ObjectType (DefaultActionId ASC);

CREATE INDEX ObjectType_Actions_FK
   ON ObjectType (DefaultActionId ASC);

CREATE INDEX ObjectType_tst_APPLICATION_FK
   ON ObjectType (AppID ASC);

CREATE INDEX ObjectType_APPLICATION_FK
   ON ObjectType (AppID ASC);

alter table ObjectType modify ObjectTypeId INT NOT NULL AUTO_INCREMENT;
-- END TABLE ObjectType

-- BEGIN TABLE Objects
CREATE TABLE Objects
(
   ObjectId          INT             NOT NULL,
   ObjName           VARCHAR(50),
   Description       VARCHAR(200),
   ObjectGroupId     INT             NOT NULL,
   ObjectTypeId      INT             NOT NULL,
   IdentifierTypeId  INT             NOT NULL,
   Identifier        VARCHAR(4000),
   AppID             INT,
   CreatedBy         VARCHAR(100),
   CreatedDateTime   DATE,
   UpdatedBy         VARCHAR(100),
   UpdatedDateTime   DATE
)
ENGINE=MyISAM;

ALTER TABLE Objects
   ADD CONSTRAINT pk_objects
   PRIMARY KEY (ObjectId);

CREATE INDEX Objects_ObjectGroup_FK
   ON Objects (ObjectGroupId ASC);

CREATE INDEX Objects_IdentifierType_FK
   ON Objects (IdentifierTypeId ASC);

CREATE INDEX Objects_APPLICATION_FK
   ON Objects (AppID ASC);

CREATE INDEX Objects_ObjectType_FK
   ON Objects (ObjectTypeId ASC);

alter table Objects modify ObjectId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Objects

-- BEGIN TABLE Param
CREATE TABLE Param
(
   ParamId          INT            NOT NULL,
   ParamName        VARCHAR(200)   NOT NULL,
   Description      VARCHAR(200),
   OrderBy          INT,
   ParamGroupId     INT            NOT NULL,
   ObjectId         INT            NOT NULL,
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE Param
   ADD CONSTRAINT pk_param
   PRIMARY KEY (ParamId);

CREATE INDEX Param_ParamGroup_FK
   ON Param (ParamGroupId ASC);

CREATE INDEX Param_Objects_FK
   ON Param (ObjectId ASC);

alter table Param modify ParamId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Param

-- BEGIN TABLE ParamGroup
CREATE TABLE ParamGroup
(
   ParamGroupId     INT            NOT NULL,
   ParamGroupName   VARCHAR(200),
   Description      VARCHAR(200),
   Tag              VARCHAR(50),
   AppID            INT            NOT NULL,
   TestScenarioId   INT            NOT NULL,
   TestCaseID       INT            NOT NULL,
   TestStepID       INT            NOT NULL,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE ParamGroup
   ADD CONSTRAINT pk_paramgroup
   PRIMARY KEY (ParamGroupId);

CREATE INDEX ParamGroup_APPLICATION_FK
   ON ParamGroup (AppID ASC);

alter table ParamGroup modify ParamGroupId INT NOT NULL AUTO_INCREMENT;
-- END TABLE ParamGroup

-- BEGIN TABLE Runner
CREATE TABLE Runner
(
   RunnerId         INT            NOT NULL,
   RunnerName       VARCHAR(50)    NOT NULL,
   Description      VARCHAR(200),
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE Runner
   ADD CONSTRAINT pk_runner
   PRIMARY KEY (RunnerId);

alter table Runner modify RunnerId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Runner

-- BEGIN TABLE Screen
CREATE TABLE Screen
(
   ScreenId         INT            NOT NULL,
   ScreenName       VARCHAR(50)    NOT NULL,
   Description      VARCHAR(200),
   AppID            INT            NOT NULL,
   CreatedBy        VARCHAR(100)   NOT NULL,
   CreatedDateTime  DATE           NOT NULL,
   UpdatedBy        VARCHAR(100)   NOT NULL,
   UpdatedDateTime  DATE           NOT NULL
)
ENGINE=MyISAM;

ALTER TABLE Screen
   ADD CONSTRAINT pk_screen
   PRIMARY KEY (ScreenId);

alter table Screen modify ScreenId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Screen

-- BEGIN TABLE Suite_Scenario_Mapping
CREATE TABLE Suite_Scenario_Mapping
(
   SuiteScenarioId  INT            NOT NULL,
   TestSuiteId      INT            NOT NULL,
   TestScenarioId   INT            NOT NULL,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE Suite_Scenario_Mapping
   ADD CONSTRAINT pk_suite_scenario_mapping
   PRIMARY KEY (SuiteScenarioId);

CREATE INDEX SuiteScenarioMap_Suite_FK
   ON Suite_Scenario_Mapping (TestSuiteId ASC);

CREATE INDEX SuiteScenarioMap_Scenario_FK
   ON Suite_Scenario_Mapping (TestScenarioId ASC);

alter table Suite_Scenario_Mapping modify SuiteScenarioId INT NOT NULL AUTO_INCREMENT;
-- END TABLE Suite_Scenario_Mapping

-- BEGIN TABLE TestCase
CREATE TABLE TestCase
(
   TestCaseID         INT            NOT NULL,
   ClassificationTag  VARCHAR(500),
   CaseName           VARCHAR(50),
   RunnerId           INT            NOT NULL,
   Description        VARCHAR(200),
   Active             INT,
   Positive           INT,
   OrderBy            INT,
   TestSuiteID        INT            NOT NULL,
   CreatedBy          VARCHAR(100),
   CreatedDateTime    DATE,
   UpdatedBy          VARCHAR(100),
   UpdatedDateTime    DATE
)
ENGINE=MyISAM;

ALTER TABLE TestCase
   ADD CONSTRAINT pk_testcase
   PRIMARY KEY (TestCaseId);

CREATE INDEX TestCase_Runner_FK
   ON TestCase (RunnerId ASC);

CREATE INDEX TestCase_TestSuite_FK
   ON TestCase (TestSuiteID ASC);

alter table TestCase modify TestCaseID INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestCase

-- BEGIN TABLE TestCondData
CREATE TABLE TestCondData
(
   TestCondDataId   INT            NOT NULL,
   TestPlanID       INT            NOT NULL,
   ConditionId      INT,
   ParamId          INT            NOT NULL,
   ParamValue       VARCHAR(200),
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE TestCondData
   ADD CONSTRAINT pk_testconddata
   PRIMARY KEY (TestCondDataId);

CREATE INDEX TestCondData_Parameter_FK
   ON TestCondData (ParamId ASC);

CREATE INDEX TestCondData_TestPlan_FK
   ON TestCondData (TestPlanID ASC);

alter table TestCondData modify TestCondDataId INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestCondData

-- BEGIN TABLE TestParamData
CREATE TABLE TestParamData
(
   TestParamDataId  INT            NOT NULL,
   AppID            INT            NOT NULL,
   TestSet          VARCHAR(50),
   TestCaseID       INT            NOT NULL,
   TestStepID       INT            NOT NULL,
   ParamId          INT            NOT NULL,
   ParamValue       VARCHAR(500),
   ValueBig         BLOB,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE TestParamData
   ADD CONSTRAINT pk_testparamdata
   PRIMARY KEY (TestParamDataId);

CREATE INDEX TestParamData_TestStep_FK
   ON TestParamData (TestStepID ASC);

CREATE INDEX TestParamData_TestCase_FK
   ON TestParamData (TestCaseID ASC);

CREATE INDEX TestParamData_Parameter_FK
   ON TestParamData (ParamId ASC);

CREATE INDEX TestParamData_APPLICATION_FK
   ON TestParamData (AppID ASC);

alter table TestParamData modify TestParamDataId INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestParamData

-- BEGIN TABLE TestPlan
CREATE TABLE TestPlan
(
   TestPlanID       INT            NOT NULL,
   PlanName         VARCHAR(50),
   Description      VARCHAR(200),
   PreCondition     INT,
   PostCondition    INT,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE TestPlan
   ADD CONSTRAINT pk_testplan
   PRIMARY KEY (TestPlanId);

CREATE INDEX TestPlan_ConditionGroup_FK
   ON TestPlan (PreCondition ASC);

CREATE INDEX TestPlan_ConditionGroup_FKv2
   ON TestPlan (PostCondition ASC);

alter table TestPlan modify TestPlanID INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestPlan

-- BEGIN TABLE TestPlan_TestScenario_Map
CREATE TABLE TestPlan_TestScenario_Map
(
   TestPlanTestScenarioId  INT            NOT NULL,
   TestPlanId              INT            NOT NULL,
   TestScenarioId          INT            NOT NULL,
   CreatedBy               VARCHAR(100),
   CreatedDateTime         DATE,
   UpdatedBy               VARCHAR(100),
   UpdatedDateTime         DATE
)
ENGINE=MyISAM;

ALTER TABLE TestPlan_TestScenario_Map
   ADD CONSTRAINT pk_testplan_testscenario_map
   PRIMARY KEY (TestPlanTestScenarioId);

CREATE INDEX Plan_Scenario_Map_Scenario_FK
   ON TestPlan_TestScenario_Map (TestScenarioId ASC);

CREATE INDEX Plan_Scenario_Map_Plan_FK
   ON TestPlan_TestScenario_Map (TestPlanId ASC);

alter table TestPlan_TestScenario_Map modify TestPlanTestScenarioId INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestPlan_TestScenario_Map

-- BEGIN TABLE TestReport
CREATE TABLE TestReport
(
   TestReportID     INT            NOT NULL,
   TestRunID        INT            NOT NULL,
   ReportDetails    VARCHAR(200),
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE TestReport
   ADD CONSTRAINT pk_testreport
   PRIMARY KEY (TestReportId);

CREATE INDEX TestReport_TestRunDetails_FK
   ON TestReport (TestRunID ASC);

alter table TestReport modify TestReportID INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestReport

-- BEGIN TABLE TestRunData
CREATE TABLE TestRunData
(
   TestRunDataId     INT            NOT NULL,
   TestPlanID        INT            NOT NULL,
   TestSuiteID       INT            NOT NULL,
   TestCaseID        INT            NOT NULL,
   TestStepID        INT            NOT NULL,
   ExceptionMessage  VARCHAR(200),
   Status            VARCHAR(10),
   CreatedBy         VARCHAR(100),
   CreatedDateTime   DATE,
   UpdatedBy         VARCHAR(100),
   UpdatedDateTime   DATE
)
ENGINE=MyISAM;

ALTER TABLE TestRunData
   ADD CONSTRAINT pk_testrundata
   PRIMARY KEY (TestRunDataId);

CREATE INDEX TestRunData_TestStep_FK
   ON TestRunData (TestStepID ASC);

CREATE INDEX TestRunData_TestPlan_FK
   ON TestRunData (TestPlanID ASC);

CREATE INDEX TestRunData_TestCase_FK
   ON TestRunData (TestCaseID ASC);

CREATE INDEX TestRunData_TestSuite_FK
   ON TestRunData (TestSuiteID ASC);

alter table TestRunData modify TestRunDataId INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestRunData

-- BEGIN TABLE TestRunDetails
CREATE TABLE TestRunDetails
(
   TestRunID            INT            NOT NULL,
   TestPlanID           INT            NOT NULL,
   RunTime              DATE,
   Status               CHAR(1),
   NotificationDetails  VARCHAR(200),
   OS                   VARCHAR(50)    NOT NULL,
   BuildVersion         VARCHAR(50),
   MachineId            VARCHAR(50)    NOT NULL,
   CreatedBy            VARCHAR(100),
   CreatedDateTime      DATE,
   UpdatedBy            VARCHAR(100),
   UpdatedDateTime      DATE
)
ENGINE=MyISAM;

ALTER TABLE TestRunDetails
   ADD CONSTRAINT pk_testrundetails
   PRIMARY KEY (TestRunID);

CREATE INDEX TestRunDetails_TestPlan_FK
   ON TestRunDetails (TestPlanID ASC);

alter table TestRunDetails modify TestRunID INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestRunDetails

-- BEGIN TABLE TestScenario
CREATE TABLE TestScenario
(
   TestScenarioId    INT            NOT NULL,
   TestScenarioName  VARCHAR(500),
   Description       VARCHAR(500),
   AppID             INT            NOT NULL,
   OrderBy           INT,
   CreatedBy         VARCHAR(100),
   CreatedDateTime   DATE,
   UpdatedBy         VARCHAR(100),
   UpdatedDateTime   DATE
)
ENGINE=MyISAM;

ALTER TABLE TestScenario
   ADD CONSTRAINT pk_testscenario
   PRIMARY KEY (TestScenarioID);

CREATE INDEX TestScenario_APPLICATION_FK
   ON TestScenario (AppID ASC);

alter table TestScenario modify TestScenarioId INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestScenario

-- BEGIN TABLE TestScenario_ParamData_Map
CREATE TABLE TestScenario_ParamData_Map
(
   TestScenarioParamDataId  INT            NOT NULL,
   TestScenarioId           INT            NOT NULL,
   TestParamDataId          INT            NOT NULL,
   CreatedBy                VARCHAR(100),
   CreatedDateTime          DATE,
   UpdatedBy                VARCHAR(100),
   UpdatedDateTime          DATE
)
ENGINE=MyISAM;

ALTER TABLE TestScenario_ParamData_Map
   ADD CONSTRAINT pk_testscenario_paramdata_map
   PRIMARY KEY (TestScenarioParamDataId);

CREATE INDEX Scenario_Scenario_FK
   ON TestScenario_ParamData_Map (TestScenarioId ASC);

CREATE INDEX ScenarioParamData_FK
   ON TestScenario_ParamData_Map (TestParamDataId ASC);

alter table TestScenario_ParamData_Map modify TestScenarioParamDataId INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestScenario_ParamData_Map

-- BEGIN TABLE TestStep
CREATE TABLE TestStep
(
   TestStepID       INT            NOT NULL,
   StepName         VARCHAR(50),
   Description      VARCHAR(200),
   TestStepType     VARCHAR(100),
   ActionId         INT,
   Active           INT,
   OrderBy          INT,
   PreCondition     INT,
   PostCondition    INT,
   InputParam       INT,
   OutputParam      INT,
   TestCaseId       INT            NOT NULL,
   RunnerId         INT,
   ExpectedResult   VARCHAR(50),
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE TestStep
   ADD CONSTRAINT pk_teststep
   PRIMARY KEY (TestStepId);

CREATE INDEX TestStep_ConditionGroup_FKv1
   ON TestStep (PostCondition ASC);

CREATE INDEX TestStep_ParamGroup_FK
   ON TestStep (InputParam ASC);

CREATE INDEX TestStep_TestCase_FK
   ON TestStep (TestCaseId ASC);

CREATE INDEX TestStep_ConditionGroup_FK
   ON TestStep (PreCondition ASC);

CREATE INDEX TestStep_Runner_FK
   ON TestStep (RunnerId ASC);

CREATE INDEX TestStep_Actions_FK
   ON TestStep (ActionId ASC);

CREATE INDEX TestStep_ParamGroup_FKv1
   ON TestStep (OutputParam ASC);

alter table TestStep modify TestStepID INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestStep

-- BEGIN TABLE TestSuite
CREATE TABLE TestSuite
(
   TestSuiteID      INT            NOT NULL,
   SuiteName        VARCHAR(50),
   Description      VARCHAR(200),
   AppID            INT            NOT NULL,
   FunctionalId     INT            NOT NULL,
   FeatureId        INT            NOT NULL,
   OrderBy          INT,
   CreatedBy        VARCHAR(100),
   CreatedDateTime  DATE,
   UpdatedBy        VARCHAR(100),
   UpdatedDateTime  DATE
)
ENGINE=MyISAM;

ALTER TABLE TestSuite
   ADD CONSTRAINT pk_testsuite
   PRIMARY KEY (TestSuiteId);

CREATE INDEX TestSuite_APPLICATION_FK
   ON TestSuite (AppID ASC);

CREATE INDEX TestSuite_Feature_FK
   ON TestSuite (FeatureId ASC);

CREATE INDEX TestSuite_Functional_FK
   ON TestSuite (FunctionalId ASC);

alter table TestSuite modify TestSuiteID INT NOT NULL AUTO_INCREMENT;
-- END TABLE TestSuite

---- version 001-12-Aug-2013:end

---- version 002-13-Aug-2013:start

CREATE  TABLE ID_GEN (
  Id_Gen INT NOT NULL DEFAULT 0 ,
  Name VARCHAR(20) NULL ,
  Id BIGINT NULL DEFAULT 0 ,
  PRIMARY KEY (Id_Gen) ,
  UNIQUE INDEX Id_UNIQUE (Id ASC) )
ENGINE = InnoDB
COMMENT = 'For Id generation';


ALTER TABLE ID_GEN 
ADD UNIQUE INDEX Name_UNIQUE (Name ASC) ;


---- version 002-13-Aug-2013:end


---- version 003-29-Aug-2013:start

CREATE  TABLE TestStep_ParamGroup_Mapping (
  TestStepParamGroupID INT(11) NOT NULL AUTO_INCREMENT ,
  TestStepID INT(11) NOT NULL ,
  ParamGroupID INT(11) NOT NULL ,
  TestCaseID INT(11) NOT NULL ,
  TestScenarioId INT(11) NOT NULL ,
  CreatedBy varchar(100) DEFAULT NULL,
  CreatedDateTime date DEFAULT NULL,
  UpdatedBy varchar(100) DEFAULT NULL,
  UpdatedDateTime date DEFAULT NULL,
  PRIMARY KEY (TestStepParamGroupID) ,
  INDEX TestStep_FK_1 (TestStepID) ,
  INDEX ParamGroup_FK_1 (ParamGroupID) ,
  CONSTRAINT TestStep_FK_1
    FOREIGN KEY (TestStepID)
    REFERENCES TestStep (TestStepID)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT ParamGroup_FK_1
    FOREIGN KEY (ParamGroupID)
    REFERENCES ParamGroup (ParamGroupID)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = MyISAM
DEFAULT CHARACTER SET = latin1;
