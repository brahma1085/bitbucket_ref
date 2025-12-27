package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.Screen;

/**
 * The Class Param.
 *
 * @author mohammedfirdos
 */
public class Param implements Serializable {

	/** Param Entity. */
	private static final long serialVersionUID = 1L;

	/** The param id. */
	private int paramID;
	
	/** The param name. */
	private String paramName;
	
	/** The description. */
	private String description;
	
	/** The sort order. */
	private int sortOrder;
	
	/** The param group id. */
	private int paramGroupID;
	
	/** The object id. */
	private int objectID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The objects. */
	private Objects objects;
	
	/** The param group. */
	private ParamGroup paramGroup;
	
	/** The application. */
	private Application application;
	
	/** The test param data. */
	private TestParamData testParamData;

	/** The object type. */
	private List<ObjectType> objectType = new ArrayList<ObjectType>();
	
	/** The screen. */
	private List<Screen> screen = new ArrayList<Screen>();
	
	/** The object group. */
	private List<ObjectGroup> objectGroup = new ArrayList<ObjectGroup>();
	
	/** The object list. */
	private List<Objects> objectList = new ArrayList<Objects>();
	
	/** The screen obj. */
	private Screen screenObj = new Screen();
	
	/** The object type obj. */
	private ObjectType objectTypeObj = new ObjectType();
	
	/** The identifier type obj. */
	private IdentifierType identifierTypeObj = new IdentifierType();

	/**
	 * Gets the identifier type obj.
	 *
	 * @return the identifier type obj
	 */
	public IdentifierType getIdentifierTypeObj() {
		return identifierTypeObj;
	}

	/**
	 * Sets the identifier type obj.
	 *
	 * @param identifierTypeObj the new identifier type obj
	 */
	public void setIdentifierTypeObj(IdentifierType identifierTypeObj) {
		this.identifierTypeObj = identifierTypeObj;
	}

	/**
	 * Gets the object type obj.
	 *
	 * @return the object type obj
	 */
	public ObjectType getObjectTypeObj() {
		return objectTypeObj;
	}

	/**
	 * Sets the object type obj.
	 *
	 * @param objectTypeObj the new object type obj
	 */
	public void setObjectTypeObj(ObjectType objectTypeObj) {
		this.objectTypeObj = objectTypeObj;
	}

	/**
	 * Gets the screen obj.
	 *
	 * @return the screen obj
	 */
	public Screen getScreenObj() {
		return screenObj;
	}

	/**
	 * Sets the screen obj.
	 *
	 * @param screenObj the new screen obj
	 */
	public void setScreenObj(Screen screenObj) {
		this.screenObj = screenObj;
	}

	/**
	 * Gets the object list.
	 *
	 * @return the object list
	 */
	public List<Objects> getObjectList() {
		return objectList;
	}

	/**
	 * Sets the object list.
	 *
	 * @param objectList the new object list
	 */
	public void setObjectList(List<Objects> objectList) {
		this.objectList = objectList;
	}

	/**
	 * Gets the object group.
	 *
	 * @return the object group
	 */
	public List<ObjectGroup> getObjectGroup() {
		return objectGroup;
	}

	/**
	 * Sets the object group.
	 *
	 * @param objectGroup the new object group
	 */
	public void setObjectGroup(List<ObjectGroup> objectGroup) {
		this.objectGroup = objectGroup;
	}

	/**
	 * Gets the screen.
	 *
	 * @return the screen
	 */
	public List<Screen> getScreen() {
		return screen;
	}

	/**
	 * Sets the screen.
	 *
	 * @param screen the new screen
	 */
	public void setScreen(List<Screen> screen) {
		this.screen = screen;
	}

	/**
	 * Gets the test param data.
	 *
	 * @return the test param data
	 */
	public TestParamData getTestParamData() {
		return testParamData;
	}

	/**
	 * Sets the test param data.
	 *
	 * @param testParamData the new test param data
	 */
	public void setTestParamData(TestParamData testParamData) {
		this.testParamData = testParamData;
	}

	/** The identifier type. */
	private List<IdentifierType> identifierType = new ArrayList<IdentifierType>(0);

	/**
	 * Gets the identifier type.
	 *
	 * @return the identifier type
	 */
	public List<IdentifierType> getIdentifierType() {
		return identifierType;
	}

	/**
	 * Sets the identifier type.
	 *
	 * @param identifierTypeList the new identifier type
	 */
	public void setIdentifierType(List<IdentifierType> identifierTypeList) {
		this.identifierType = identifierTypeList;
	}

	/**
	 * Gets the object type.
	 *
	 * @return the object type
	 */
	public List<ObjectType> getObjectType() {
		return objectType;
	}

	/**
	 * Sets the object type.
	 *
	 * @param objectType the new object type
	 */
	public void setObjectType(List<ObjectType> objectType) {
		this.objectType = objectType;
	}

	/**
	 * Gets the application.
	 *
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Sets the application.
	 *
	 * @param application the new application
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * Gets the param group.
	 *
	 * @return the param group
	 */
	public ParamGroup getParamGroup() {
		return paramGroup;
	}

	/**
	 * Sets the param group.
	 *
	 * @param paramGroup the new param group
	 */
	public void setParamGroup(ParamGroup paramGroup) {
		this.paramGroup = paramGroup;
	}

	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public Objects getObjects() {
		return objects;
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects to set
	 */
	public void setObjects(Objects objects) {
		this.objects = objects;
	}

	/**
	 * Gets the param id.
	 *
	 * @return the paramID
	 */
	public int getParamID() {
		return paramID;
	}

	/**
	 * Sets the param id.
	 *
	 * @param paramID the paramID to set
	 */
	public void setParamID(int paramID) {
		this.paramID = paramID;
	}

	/**
	 * Gets the param name.
	 *
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * Sets the param name.
	 *
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the sort order.
	 *
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * Sets the sort order.
	 *
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * Gets the param group id.
	 *
	 * @return the paramGroupID
	 */
	public int getParamGroupID() {
		return paramGroupID;
	}

	/**
	 * Sets the param group id.
	 *
	 * @param paramGroupID the paramGroupID to set
	 */
	public void setParamGroupID(int paramGroupID) {
		this.paramGroupID = paramGroupID;
	}

	/**
	 * Gets the object id.
	 *
	 * @return the objectID
	 */
	public int getObjectID() {
		return objectID;
	}

	/**
	 * Sets the object id.
	 *
	 * @param objectID the objectID to set
	 */
	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date time.
	 *
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Sets the created date time.
	 *
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date time.
	 *
	 * @return the updatedDateTime
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * Sets the updated date time.
	 *
	 * @param updatedDateTime the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Param [ParamID=" + paramID + ", ParamName=" + paramName + ", Description=" + description + ", SortOrder=" + sortOrder + ", ParamGroupID="
				+ paramGroupID + ", ObjectID=" + objectID + "]";
	}

}
