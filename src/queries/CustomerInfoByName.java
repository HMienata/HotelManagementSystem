package queries;

public class CustomerInfoByName extends AbstractCustomerInfoQuery {

	private final String name;

	public CustomerInfoByName(String name, String[] selected) {
		super(selected);
		this.name = name;
	}

	@Override
	protected String getQueryDefinition(String formattedAttributes) {
		return String.format("SELECT %s FROM Customer WHERE Name = '%s'", formattedAttributes, name);
	}

}