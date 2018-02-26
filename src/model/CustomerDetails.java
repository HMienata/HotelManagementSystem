package model;

public class CustomerDetails extends Customer {

	private final String paymentMethod;

	private final String phoneNumber;

	public CustomerDetails(int id, String name, String paymentMethod, String phoneNumber) {
		super(id, name);
		this.paymentMethod = paymentMethod;
		this.phoneNumber = phoneNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
