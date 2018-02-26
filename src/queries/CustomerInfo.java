package queries;

public class CustomerInfo extends AbstractCustomerInfoQuery {

	private final int id;

	public CustomerInfo(int id, String[] selected) {
		super(selected);
		this.id = id;
	}

	@Override
	protected String getQueryDefinition(String formattedAttributes) {
		return String.format("SELECT %s FROM Customer WHERE CustomerID = %d", formattedAttributes, id);
	}

}
