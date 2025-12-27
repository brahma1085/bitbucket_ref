package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;


public class VehicleApplnForm extends ActionForm{
	String maketype,dealername,licenseno,vehicletype,vehiclefor,vehiclearea,vehicleparked,othervehicle,validity1;
	public String getValidity1() {
		return validity1;
	}
	public void setValidity1(String validity1) {
		this.validity1 = validity1;
	}
	double cost;
	public String getMaketype() {
		return maketype;
	}
	public void setMaketype(String maketype) {
		this.maketype = maketype;
	}
	public String getDealername() {
		return dealername;
	}
	public void setDealername(String dealername) {
		this.dealername = dealername;
	}
	public String getLicenseno() {
		return licenseno;
	}
	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}
	public String getVehicletype() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	public String getVehiclefor() {
		return vehiclefor;
	}
	public void setVehiclefor(String vehiclefor) {
		this.vehiclefor = vehiclefor;
	}
	public String getVehiclearea() {
		return vehiclearea;
	}
	public void setVehiclearea(String vehiclearea) {
		this.vehiclearea = vehiclearea;
	}
	public String getVehicleparked() {
		return vehicleparked;
	}
	public void setVehicleparked(String vehicleparked) {
		this.vehicleparked = vehicleparked;
	}
	public String getOthervehicle() {
		return othervehicle;
	}
	public void setOthervehicle(String othervehicle) {
		this.othervehicle = othervehicle;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}