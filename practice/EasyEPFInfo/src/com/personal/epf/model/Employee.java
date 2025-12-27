package com.personal.epf.model;

import java.io.Serializable;

/**
 * @author brahma
 * 
 *         The Class Employee. It binds the user data and it can be used as a
 *         data transfer object
 */
public class Employee implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The empid. */
	private int empid;

	/** The zip. */
	private int zip;

	/** The firstname. */
	private String firstname;

	/** The middlename. */
	private String middlename;

	/** The lastname. */
	private String lastname;

	/** The pwd. */
	private String pwd;

	/** The state. */
	private String state;

	/** The dob. */
	private String dob;

	/** The designation. */
	private String designation;

	/** The dayphone. */
	private String dayphone;

	/** The mobile. */
	private String mobile;

	/** The pfvalue. */
	private String pfvalue;

	/** The maritalstatus. */
	private String maritalstatus;

	/** The country. */
	private String country;

	/** The gender. */
	private String gender;

	/** The address. */
	private String address;

	/** The email. */
	private String email;

	/** The city. */
	private String city;

	/** The salary. */
	private String salary;

	/**
	 * Gets the empid.
	 * 
	 * @return the empid
	 */
	public int getEmpid() {
		return empid;
	}

	/**
	 * Sets the empid.
	 * 
	 * @param empid
	 *            the new empid
	 */
	public void setEmpid(int empid) {
		this.empid = empid;
	}

	/**
	 * Gets the zip.
	 * 
	 * @return the zip
	 */
	public int getZip() {
		return zip;
	}

	/**
	 * Sets the zip.
	 * 
	 * @param zip
	 *            the new zip
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}

	/**
	 * Gets the firstname.
	 * 
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the firstname.
	 * 
	 * @param firstname
	 *            the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the middlename.
	 * 
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * Sets the middlename.
	 * 
	 * @param middlename
	 *            the new middlename
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * Gets the lastname.
	 * 
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the lastname.
	 * 
	 * @param lastname
	 *            the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password.
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the new password.
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the date of birth.
	 * 
	 * @return the date of birth.
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * Sets the date of birth.
	 * 
	 * @param date
	 *            of birth. the new date of birth.
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * Gets the designation.
	 * 
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * Sets the designation.
	 * 
	 * @param designation
	 *            the new designation
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * Gets the dayphone.
	 * 
	 * @return the dayphone
	 */
	public String getDayphone() {
		return dayphone;
	}

	/**
	 * Sets the dayphone.
	 * 
	 * @param dayphone
	 *            the new dayphone
	 */
	public void setDayphone(String dayphone) {
		this.dayphone = dayphone;
	}

	/**
	 * Gets the mobile number
	 * 
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile number.
	 * 
	 * @param mobile
	 *            the new mobile number
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * Gets the pfvalue.
	 * 
	 * @return the pfvalue
	 */
	public String getPfvalue() {
		return pfvalue;
	}

	/**
	 * Sets the pfvalue.
	 * 
	 * @param pfvalue
	 *            the new pfvalue
	 */
	public void setPfvalue(String pfvalue) {
		this.pfvalue = pfvalue;
	}

	/**
	 * Gets the maritalstatus.
	 * 
	 * @return the maritalstatus
	 */
	public String getMaritalstatus() {
		return maritalstatus;
	}

	/**
	 * Sets the maritalstatus.
	 * 
	 * @param maritalstatus
	 *            the new maritalstatus
	 */
	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	/**
	 * Gets the country.
	 * 
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 * 
	 * @param country
	 *            the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the gender.
	 * 
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 * 
	 * @param gender
	 *            the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the address.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * 
	 * @param address
	 *            the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the city.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 * 
	 * @param city
	 *            the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the salary.
	 * 
	 * @return the salary
	 */
	public String getSalary() {
		return salary;
	}

	/**
	 * Sets the salary.
	 * 
	 * @param salary
	 *            the new salary
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}

}
