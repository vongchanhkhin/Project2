package com.javaweb.custom.entity;

import com.javaweb.custom.annotation.ColumnCustom;
import com.javaweb.custom.annotation.EntityCustom;
import com.javaweb.custom.annotation.TableCustom;

@EntityCustom
@TableCustom(name = "building")
public class BuildingJDBCEntity {

	@ColumnCustom(name = "name")
	private String name;

	@ColumnCustom(name = "street")
	private String street;

	@ColumnCustom(name = "ward")
	private String ward;

	@ColumnCustom(name = "numberofbasement")
	private Long numberOfBasement;

	@ColumnCustom(name = "floorarea")
	private Long floorArea;

	@ColumnCustom(name = "direction")
	private String direction;

	@ColumnCustom(name = "level")
	private String level;

	@ColumnCustom(name = "rentprice")
	private Long rentPrice;

	@ColumnCustom(name = "servicefee")
	private String serviceFee;

	@ColumnCustom(name = "brokeragefee")
	private Long brokerageFee;

	@ColumnCustom(name = "managername")
	private String managerName;

	@ColumnCustom(name = "managerphonenumber")
	private String managerPhoneNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public Long getNumberOfBasement() {
		return numberOfBasement;
	}

	public void setNumberOfBasement(Long numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}

	public Long getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Long floorArea) {
		this.floorArea = floorArea;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Long rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Long getBrokerageFee() {
		return brokerageFee;
	}

	public void setBrokerageFee(Long brokerageFee) {
		this.brokerageFee = brokerageFee;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}

}
