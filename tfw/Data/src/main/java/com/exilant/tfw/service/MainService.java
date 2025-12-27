package com.exilant.tfw.service;

import java.sql.Timestamp;
import java.util.List;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Actions;
import com.exilant.tfw.pojo.AgentDetails;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.AppFunctionality;
import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.ExcelImport;
import com.exilant.tfw.pojo.GenericData;
import com.exilant.tfw.pojo.Roles;
import com.exilant.tfw.pojo.Runner;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerBackup;
import com.exilant.tfw.pojo.SchedulerLaneDetails;
import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.Screen;
import com.exilant.tfw.pojo.UserRoles;
import com.exilant.tfw.pojo.Users;
import com.exilant.tfw.pojo.UsersApplicationMapping;
import com.exilant.tfw.pojo.def.DataSets;
import com.exilant.tfw.pojo.def.ScheduledJobs;
import com.exilant.tfw.pojo.input.TestData;

/**
 * Main Service class.
 */

/**
 * @author mohammedfirdos
 * 
 */
public interface MainService {

	/**
	 * Gets the actions.
	 * 
	 * @return the actions
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Actions> getActions() throws ServiceException;

	/**
	 * Gets the agent details.
	 * 
	 * @return the agent details
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AgentDetails> getAgentDetails() throws ServiceException;

	/**
	 * Gets the agent details by id.
	 * 
	 * @param agentDetailsId
	 *            the agent details id
	 * @return the agent details by id
	 * @throws ServiceException
	 *             the service exception
	 */
	AgentDetails getAgentDetailsById(int agentDetailsId) throws ServiceException;

	/**
	 * Gets the app feature.
	 * 
	 * @return the app feature
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFeature> getAppFeature() throws ServiceException;

	/**
	 * Gets the app functionality.
	 * 
	 * @return the app functionality
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFunctionality> getAppFunctionality() throws ServiceException;

	/**
	 * Insert application.
	 * 
	 * @param application
	 *            the application
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertApplication(Application application) throws ServiceException;
	
	long insertRunner(Runner runner) throws ServiceException;

	/**
	 * Gets the application.
	 * 
	 * @return the application
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Application> getApplication() throws ServiceException;

	/**
	 * Gets the generic data.
	 * 
	 * @return the generic data
	 * @throws ServiceException
	 *             the service exception
	 */
	List<GenericData> getGenericData() throws ServiceException;

	/**
	 * Gets the runner.
	 * 
	 * @return the runner
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Runner> getRunner() throws ServiceException;

	/**
	 * Gets the scheduler.
	 * 
	 * @return the scheduler
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Scheduler> getScheduler() throws ServiceException;

	/**
	 * Gets the scheduler lane details.
	 * 
	 * @return the scheduler lane details
	 * @throws ServiceException
	 *             the service exception
	 */
	List<SchedulerLaneDetails> getSchedulerLaneDetails() throws ServiceException;

	/**
	 * Gets the scheduler run details.
	 * 
	 * @return the scheduler run details
	 * @throws ServiceException
	 *             the service exception
	 */
	List<SchedulerRunDetails> getSchedulerRunDetails() throws ServiceException;

	/**
	 * Insert screen get key.
	 * 
	 * @param screen
	 *            the screen
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertScreenGetKey(Screen screen) throws ServiceException;

	/**
	 * Gets the screen.
	 * 
	 * @return the screen
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Screen> getScreen() throws ServiceException;

	/**
	 * Gets the app functionality id by name.
	 * 
	 * @param appId
	 *            the app id
	 * @param functionalName
	 *            the functional name
	 * @return the app functionality id by name
	 * @throws ServiceException
	 *             the service exception
	 */
	int getAppFunctionalityIdByName(int appId, String functionalName) throws ServiceException;

	/**
	 * Gets the scheduler with all data.
	 * 
	 * @param schedulerId
	 *            the scheduler id
	 * @return the scheduler with all data
	 * @throws ServiceException
	 *             the service exception
	 */
	Scheduler getSchedulerWithAllData(int schedulerId) throws ServiceException;

	/**
	 * Gets the all screens.
	 * 
	 * @return the all screens
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Screen> getAllScreens() throws ServiceException;

	/**
	 * Gets the app feature filter by app functionality id.
	 * 
	 * @param appFeatureID
	 *            the app feature id
	 * @return the app feature filter by app functionality id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFunctionality> getAppFeatureFilterByAppFunctionalityID(int appFeatureID) throws ServiceException;

	/**
	 * Gets the app feature filter by app functionality iditap.
	 * 
	 * @param appFeatureID
	 *            the app feature id
	 * @return the app feature filter by app functionality iditap
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFeature> getAppFeatureFilterByAppFunctionalityIDITAP(int appFeatureID) throws ServiceException;

	/**
	 * Gets the app functionality filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the app functionality filter by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFunctionality> getAppFunctionalityFilterByAppId(int appId) throws ServiceException;

	/**
	 * Gets the screen filter by app feature id.
	 * 
	 * @param screenId
	 *            the screen id
	 * @return the screen filter by app feature id
	 * @throws ServiceException
	 *             the service exception
	 */
	AppFeature getScreenFilterByAppFeatureId(int screenId) throws ServiceException;

	/**
	 * Insert scheduler run details.
	 * 
	 * @param schedulerRunDetails
	 *            the scheduler run details
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertSchedulerRunDetails(SchedulerRunDetails schedulerRunDetails) throws ServiceException;

	/**
	 * Insert scheduler run details get key.
	 * 
	 * @param schedulerRunDetails
	 *            the scheduler run details
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertSchedulerRunDetailsGetKey(SchedulerRunDetails schedulerRunDetails) throws ServiceException;

	/**
	 * Update scheduler run details.
	 * 
	 * @param schedulerRunDetails
	 *            the scheduler run details
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateSchedulerRunDetails(SchedulerRunDetails schedulerRunDetails) throws ServiceException;

	/**
	 * Insert scheduler get key.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertSchedulerGetKey(Scheduler scheduler) throws ServiceException;

	/**
	 * Insert scheduler.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertScheduler(Scheduler scheduler) throws ServiceException;

	/**
	 * Insert scheduler lane details.
	 * 
	 * @param schedulerLaneDetails
	 *            the scheduler lane details
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long insertSchedulerLaneDetails(SchedulerLaneDetails schedulerLaneDetails) throws ServiceException;

	/**
	 * Insert application get key.
	 * 
	 * @param application
	 *            the application
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertApplicationGetKey(Application application) throws ServiceException;

	/**
	 * Gets the application by id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the application by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Application getApplicationById(int appId) throws ServiceException;

	/**
	 * Update application.
	 * 
	 * @param application
	 *            the application
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateApplication(final Application application) throws ServiceException;
	
	long updateRunner(final Runner runner) throws ServiceException;

	/**
	 * Insert app functionality get key.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertAppFunctionalityGetKey(AppFunctionality appFunctionality) throws ServiceException;

	/**
	 * Gets the app functionality by id.
	 * 
	 * @param functionalId
	 *            the functional id
	 * @return the app functionality by id
	 * @throws ServiceException
	 *             the service exception
	 */
	AppFunctionality getAppFunctionalityById(int functionalId) throws ServiceException;

	/**
	 * Update app functionality.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateAppFunctionality(final AppFunctionality appFunctionality) throws ServiceException;

	/**
	 * Insert app feature get key.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertAppFeatureGetKey(AppFeature appFeature) throws ServiceException;

	/**
	 * Gets the app feature by id.
	 * 
	 * @param appFeatureId
	 *            the app feature id
	 * @return the app feature by id
	 * @throws ServiceException
	 *             the service exception
	 */
	AppFeature getAppFeatureById(int appFeatureId) throws ServiceException;

	/**
	 * Update app feature.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateAppFeature(final AppFeature appFeature) throws ServiceException;

	/**
	 * Gets the all app features by functional id.
	 * 
	 * @param appFunctionalityID
	 *            the app functionality id
	 * @return the all app features by functional id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFeature> getAllAppFeaturesByFunctionalId(int appFunctionalityID) throws ServiceException;

	/**
	 * Gets the screen by id.
	 * 
	 * @param screenId
	 *            the screen id
	 * @return the screen by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Screen getScreenById(int screenId) throws ServiceException;

	/**
	 * Update screen.
	 * 
	 * @param screen
	 *            the screen
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateScreen(final Screen screen) throws ServiceException;

	/**
	 * Insert read plan data.
	 * 
	 * @param excelImport
	 *            the excel import
	 * @throws ServiceException
	 *             the service exception
	 */
	void insertReadPlanData(ExcelImport excelImport) throws ServiceException;

	/**
	 * Insert actions get key.
	 * 
	 * @param actions
	 *            the actions
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertActionsGetKey(Actions actions) throws ServiceException;

	/**
	 * Gets the all actions.
	 * 
	 * @return the all actions
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Actions> getAllActions() throws ServiceException;

	/**
	 * Update scheduler time.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateSchedulerTime(Scheduler scheduler) throws ServiceException;

	/**
	 * Fetch scheduler by time.
	 * 
	 * @param scheduledTime
	 *            the scheduled time
	 * @return the list
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Scheduler> fetchSchedulerByTime(Timestamp scheduledTime) throws ServiceException;

	/**
	 * Update screen data.
	 * 
	 * @param screen
	 *            the screen
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateScreenData(Screen screen) throws ServiceException;

	/**
	 * Gets the all function names by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all function names by test case id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFunctionality> getAllFunctionNamesByTestCaseId(int testCaseId) throws ServiceException;

	/**
	 * Gets the all feature names by test case id.
	 * 
	 * @param testCaseId
	 *            the test case id
	 * @return the all feature names by test case id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFeature> getAllFeatureNamesByTestCaseId(int testCaseId) throws ServiceException;

	/**
	 * Gets the scheduler lane details by schedule id.
	 * 
	 * @param scheduleId
	 *            the schedule id
	 * @return the scheduler lane details by schedule id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<SchedulerLaneDetails> getSchedulerLaneDetailsByScheduleId(int scheduleId) throws ServiceException;

	/**
	 * Gets the data sets by scheduler id by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the data sets by scheduler id by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<DataSets> getDataSetsBySchedulerIdByAppId(int appId) throws ServiceException;

	/**
	 * Gets the scheduler filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the scheduler filter by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Scheduler> getSchedulerFilterByAppID(int appId) throws ServiceException;

	/**
	 * Gets the scheduler lane details by app id.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the scheduler lane details by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<SchedulerLaneDetails> getSchedulerLaneDetailsByAppId(int AppId) throws ServiceException;

	/**
	 * Update scheduler status.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateSchedulerStatus(Scheduler scheduler) throws ServiceException;

	/**
	 * Update app functional data.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateAppFunctionalData(AppFunctionality appFunctionality) throws ServiceException;

	/**
	 * Gets the app functionality obj by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the app functionality obj by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFunctionality> getAppFunctionalityObjByAppId(int appId) throws ServiceException;

	/**
	 * Gets the screens by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the screens by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Screen> getScreensByAppId(int appId) throws ServiceException;

	/**
	 * Update app feature data.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateAppFeatureData(AppFeature appFeature) throws ServiceException;

	/**
	 * Gets the test data with param count.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test data with param count
	 * @throws ServiceException
	 *             the service exception
	 */
	List<TestData> getTestDataWithParamCount(int appId) throws ServiceException;

	/**
	 * Gets the plan names by schedule id.
	 * 
	 * @param scheduleIds
	 *            the schedule ids
	 * @return the plan names by schedule id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<DataSets> getPlanNamesByScheduleId(List<Integer> scheduleIds) throws ServiceException;

	/**
	 * Gets the scheduler by id.
	 * 
	 * @param schedulerId
	 *            the scheduler id
	 * @return the scheduler by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Scheduler getSchedulerById(int schedulerId) throws ServiceException;

	/**
	 * Update scheduler.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateScheduler(Scheduler scheduler) throws ServiceException;

	/**
	 * Update scheduler lane details.
	 * 
	 * @param schedulerLaneDetails
	 *            the scheduler lane details
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateSchedulerLaneDetails(final SchedulerLaneDetails schedulerLaneDetails) throws ServiceException;

	/**
	 * Gets the actions by id.
	 * 
	 * @param actionsId
	 *            the actions id
	 * @return the actions by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Actions getActionsById(int actionsId) throws ServiceException;

	/**
	 * Gets the runner by id.
	 * 
	 * @param runnerId
	 *            the runner id
	 * @return the runner by id
	 * @throws ServiceException
	 *             the service exception
	 */
	Runner getRunnerById(int runnerId) throws ServiceException;

	/**
	 * Update scheduler failure count with time.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateSchedulerFailureCountWithTime(Scheduler scheduler) throws ServiceException;

	/**
	 * Insert scheduler get key.
	 * 
	 * @param schedulerBackup
	 *            the scheduler backup
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertSchedulerGetKey(SchedulerBackup schedulerBackup) throws ServiceException;

	/**
	 * Update scheduler.
	 * 
	 * @param schedulerBackup
	 *            the scheduler backup
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long updateScheduler(SchedulerBackup schedulerBackup) throws ServiceException;

	/**
	 * Delete scheduler by id.
	 * 
	 * @param schedulerId
	 *            the scheduler id
	 * @return the long
	 * @throws ServiceException
	 *             the service exception
	 */
	long deleteSchedulerById(int schedulerId) throws ServiceException;

	/**
	 * Gets the scheduler backup by id.
	 * 
	 * @param schedulerBackupId
	 *            the scheduler backup id
	 * @return the scheduler backup by id
	 * @throws ServiceException
	 *             the service exception
	 */
	SchedulerBackup getSchedulerBackupById(int schedulerBackupId) throws ServiceException;

	/**
	 * Gets the scheduler by status.
	 * 
	 * @return the scheduler by status
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Scheduler> getSchedulerByStatus() throws ServiceException;

	/**
	 * Gets the all apps.
	 * 
	 * @return the all apps
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Application> getAllApps() throws ServiceException;

	/**
	 * Gets the screens by app id and feature id.
	 * 
	 * @param appId
	 *            the app id
	 * @param featureId
	 *            the feature id
	 * @return the screens by app id and feature id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Screen> getScreensByAppIdAndFeatureID(int appId, int featureId) throws ServiceException;

	/**
	 * Gets the screens feature id.
	 * 
	 * @param featureID
	 *            the feature id
	 * @return the screens feature id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<Screen> getScreensFeatureID(int featureID) throws ServiceException;

	/**
	 * Gets the app feature by functioanlity id.
	 * 
	 * @param functionalID
	 *            the functional id
	 * @return the app feature by functioanlity id
	 * @throws ServiceException
	 *             the service exception
	 */
	List<AppFeature> getAppFeatureByFunctioanlityID(int functionalID) throws ServiceException;

	/**
	 * Gets the scheduled jobs for app.
	 * 
	 * @param appId
	 *            the app id
	 * @return the scheduled jobs for app
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ScheduledJobs> getScheduledJobsForApp(int appId) throws ServiceException;

	/**
	 * Gets the scheduled running jobs for app.
	 * 
	 * @param appId
	 *            the app id
	 * @return the scheduled running jobs for app
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ScheduledJobs> getScheduledRunningJobsForApp(int appId) throws ServiceException;

	/**
	 * Gets the scheduled not run jobs for app.
	 * 
	 * @param appId
	 *            the app id
	 * @return the scheduled not run jobs for app
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ScheduledJobs> getScheduledNotRunJobsForApp(int appId) throws ServiceException;

	/**
	 * Gets the scheduled completed jobs for app.
	 * 
	 * @param appId
	 *            the app id
	 * @return the scheduled completed jobs for app
	 * @throws ServiceException
	 *             the service exception
	 */
	List<ScheduledJobs> getScheduledCompletedJobsForApp(int appId) throws ServiceException;

	/**
	 * Gets the application for flow chart.
	 *
	 * @param appId the app id
	 * @return the application for flow chart
	 * @throws ServiceException the service exception
	 */
	Application getApplicationForFlowChart(int appId) throws ServiceException;
	
	List<Roles> getRoles() throws ServiceException;

	int insertUsersGetKey(Users users) throws ServiceException;

	boolean checkAvailability(String username) throws ServiceException;

	int insertUsersApplicationMappingGetKey(UsersApplicationMapping usersApplicationMapping) throws ServiceException;

	int insertUserRolesGetKey(UserRoles userRoles) throws ServiceException;

	List<UsersApplicationMapping> getApplicationByUserId(int userId) throws ServiceException;

	int getUserIdByUserName(String userName) throws ServiceException;

	Users getUsersByName(String username) throws ServiceException;

	List<String> getUserRoleById(int userId) throws ServiceException;
	
	Users getUserByUserId(int userId) throws ServiceException;
	
	String getPasswordByUsernameEmailID(String username, String emailID) throws ServiceException;

	int getIncorrectPasswordCount(String username) throws ServiceException;
	
	long updateIncorrectPasswordCount(String username, int incorrectPasswordCount) throws ServiceException;
	
	String getEmailByUsername(String username) throws ServiceException;
	
	long updatePasswordByUsername(String username, String newPassword) throws ServiceException;
	
	int insertRolesGetKey(Roles roles) throws ServiceException;
	
	Roles getRoleByID(int roleID) throws ServiceException;

	long updateRoles(Roles roles) throws ServiceException;
	
	int getUserIdByRole(String roleName) throws ServiceException;

	List<Users> getAllUsers() throws ServiceException;

	long updateUser(Users users) throws ServiceException;

	List<String> getUserRoleFilterByUserId(int userId) throws ServiceException;

	List<Integer> getApplicationsByRoleAndUserID(int userID, String role) throws ServiceException;

	int getApplicationIDByName(String appName) throws ServiceException;
	
}