package model;

public class BranchLocation {

	private final String street;

	private final String houseNumber;

	private final String postalCode;

	private final float aggregatedPrice;

	public BranchLocation(String street, String houseNumber, String postalCode, float aggregatedPrice) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.aggregatedPrice = aggregatedPrice;
	}

	public String getStreet() {
		return street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public float getAggregatedPrice() {
		return aggregatedPrice;
	}

}
