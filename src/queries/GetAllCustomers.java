package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CustomerDetails;

public class GetAllCustomers extends AbstractQuery<List<CustomerDetails>> {

	@Override
	protected List<CustomerDetails> parseResult(ResultSet rs) throws SQLException {
		List<CustomerDetails> customers = new ArrayList<>();
		while (rs.next()) {
			CustomerDetails c = new CustomerDetails(rs.getInt("CustomerID"), rs.getString("Name"),
					rs.getString("PaymentMethod"), rs.getString("PhoneNumber"));
			customers.add(c);
		}
		return customers;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format("SELECT * FROM Customer");
	}

}
