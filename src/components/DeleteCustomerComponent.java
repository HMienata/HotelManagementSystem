package components;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import model.CustomerDetails;
import queries.DeleteCustomer;
import queries.GetAllCustomers;
import queries.IQuery;
import ui.AbstractMenu;
import ui.QueryControl;

public class DeleteCustomerComponent extends AbstractQueryComponent<List<CustomerDetails>> {

	public DeleteCustomerComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.integer("Enter Customer ID:  ") };
	}

	@Override
	protected IQuery<List<CustomerDetails>> createQuery(JFormattedTextField[] textFields) {
		int customerID = (int) textFields[0].getValue();
		return (con) -> {
			new DeleteCustomer(customerID).execute(con);
			return new GetAllCustomers().execute(con);
		};
	}

	@Override
	public String getDescription() {
		return "Delete Customer";
	}

	@Override
	protected List<List<String>> parseData(List<CustomerDetails> t) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList("CustomerID", "Name", "PaymentMethod", "PhoneNumber"));
		for (int i = 0; i < t.size(); i++) {
			CustomerDetails c = t.get(i);
			data.add(Arrays.asList(Integer.toString(c.getId()), c.getName(), c.getPaymentMethod(), c.getPhoneNumber()));
		}
		return data;
	}

}
