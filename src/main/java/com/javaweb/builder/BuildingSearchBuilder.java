package com.javaweb.builder;

import java.util.ArrayList;
import java.util.List;

public class BuildingSearchBuilder {
	private String name;
	private String street;
	private String ward;
	private Long districtId;
	private Long numberOfBasement;
	private Long floorArea;
	private List<String> typeCodes = new ArrayList<>();
	private String managerName;
	private String managerPhoneNumber;
	private Long rentPriceFrom;
	private Long rentPriceTo;
	private Long areaFrom;
	private Long areaTo;
	private Long staffId;

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

	public Long getDistrictId() {
		return districtId;
	}

	public Long getNumberOfBasement() {
		return numberOfBasement;
	}

	public Long getFloorArea() {
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

	public Long getRentPriceFrom() {
		return rentPriceFrom;
	}

	public Long getRentPriceTo() {
		return rentPriceTo;
	}

	public Long getAreaFrom() {
		return areaFrom;
	}

	public Long getAreaTo() {
		return areaTo;
	}

	public Long getStaffId() {
		return staffId;
	}

	public static class Builder {
		private String name;
		private String street;
		private String ward;
		private Long districtId;
		private Long numberOfBasement;
		private Long floorArea;
		private List<String> typeCodes = new ArrayList<>();
		private String managerName;
		private String managerPhoneNumber;
		private Long rentPriceFrom;
		private Long rentPriceTo;
		private Long areaFrom;
		private Long areaTo;
		private Long staffId;

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

		public Builder setDistrictId(Long districtId) {
			this.districtId = districtId;
			return this;
		}

		public Builder setNumberOfBasement(Long numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
			return this;
		}

		public Builder setFloorArea(Long floorArea) {
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

		public Builder setRentPriceFrom(Long rentPriceFrom) {
			this.rentPriceFrom = rentPriceFrom;
			return this;
		}

		public Builder setRentPriceTo(Long rentPriceTo) {
			this.rentPriceTo = rentPriceTo;
			return this;
		}

		public Builder setAreaFrom(Long areaFrom) {
			this.areaFrom = areaFrom;
			return this;
		}

		public Builder setAreaTo(Long areaTo) {
			this.areaTo = areaTo;
			return this;
		}

		public Builder setStaffId(Long staffId) {
			this.staffId = staffId;
			return this;
		}

		public BuildingSearchBuilder build() {
			return new BuildingSearchBuilder(this);
		}
	}
}
