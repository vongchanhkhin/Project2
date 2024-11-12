package com.javaweb.builder;

import java.util.ArrayList;
import java.util.List;

public class BuildingSearchBuilder {
	private String name;
	private String street;
	private String ward;
	private Integer districtId;
	private Integer numberOfBasement;
	private Integer floorArea;
	private List<String> typeCodes = new ArrayList<>();
	private String managerName;
	private String managerPhoneNumber;
	private Integer rentPriceFrom;
	private Integer rentPriceTo;
	private Integer areaFrom;
	private Integer areaTo;
	private Integer staffId;

	private BuildingSearchBuilder(Builder builder) {
		this.name = builder.name;
		this.street = builder.street;
		this.ward = builder.ward;
		this.districtId = builder.districtId;
		this.numberOfBasement = builder.numberOfBasement;
		this.floorArea = builder.floorArea;
		this.typeCodes = builder.typeCodes;
		this.managerName = builder.managerName;
		this.managerPhoneNumber = builder.managerPhoneNumber;
		this.rentPriceFrom = builder.rentPriceFrom;
		this.rentPriceTo = builder.rentPriceTo;
		this.areaFrom = builder.areaFrom;
		this.areaTo = builder.areaTo;
		this.staffId = builder.staffId;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getWard() {
		return ward;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}

	public Integer getFloorArea() {
		return floorArea;
	}

	public List<String> getTypeCodes() {
		return typeCodes;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}

	public Integer getRentPriceFrom() {
		return rentPriceFrom;
	}

	public Integer getRentPriceTo() {
		return rentPriceTo;
	}

	public Integer getAreaFrom() {
		return areaFrom;
	}

	public Integer getAreaTo() {
		return areaTo;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public static class Builder {
		private String name;
		private String street;
		private String ward;
		private Integer districtId;
		private Integer numberOfBasement;
		private Integer floorArea;
		private List<String> typeCodes = new ArrayList<>();
		private String managerName;
		private String managerPhoneNumber;
		private Integer rentPriceFrom;
		private Integer rentPriceTo;
		private Integer areaFrom;
		private Integer areaTo;
		private Integer staffId;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setStreet(String street) {
			this.street = street;
			return this;
		}

		public Builder setWard(String ward) {
			this.ward = ward;
			return this;
		}

		public Builder setDistrictId(Integer districtId) {
			this.districtId = districtId;
			return this;
		}

		public Builder setNumberOfBasement(Integer numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
			return this;
		}

		public Builder setFloorArea(Integer floorArea) {
			this.floorArea = floorArea;
			return this;
		}

		public Builder setTypeCodes(List<String> typeCodes) {
			this.typeCodes = typeCodes;
			return this;
		}

		public Builder setManagerName(String managerName) {
			this.managerName = managerName;
			return this;
		}

		public Builder setManagerPhoneNumber(String managerPhoneNumber) {
			this.managerPhoneNumber = managerPhoneNumber;
			return this;
		}

		public Builder setRentPriceFrom(Integer rentPriceFrom) {
			this.rentPriceFrom = rentPriceFrom;
			return this;
		}

		public Builder setRentPriceTo(Integer rentPriceTo) {
			this.rentPriceTo = rentPriceTo;
			return this;
		}

		public Builder setAreaFrom(Integer areaFrom) {
			this.areaFrom = areaFrom;
			return this;
		}

		public Builder setAreaTo(Integer areaTo) {
			this.areaTo = areaTo;
			return this;
		}

		public Builder setStaffId(Integer staffId) {
			this.staffId = staffId;
			return this;
		}

		public BuildingSearchBuilder build() {
			return new BuildingSearchBuilder(this);
		}
	}
}
