package components;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import model.Customer;
import queries.IQuery;
import queries.LateCheckOut;
import ui.AbstractMenu;
import ui.QueryControl;

public class LateCheckOutComponent extends AbstractQueryComponent<List<Customer>> {

	public LateCheckOutComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	public String getDescription() {
		return "Query Customers Who Are Late To Check Out";
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.date("Enter Current Date (yyyy-mm-dd): "),
				QueryControl.integer("Enter Manager ID: ") };
	}

	@Override
	protected IQuery<List<Customer>> createQuery(JFormattedTextField[] textFields) {
		String Date = textFields[0].getText().replace("-", "");
		int ManagerID = (int) textFields[1].getValue();
		return new LateCheckOut(Date, ManagerID);
	}

	@Override
	protected List<List<String>> parseData(List<Customer> l) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList("Customer ID", "Name"));
		for (int i = 0; i < l.size(); i++) {
			Customer b = l.get(i);
			data.add(Arrays.asList(Integer.toString(b.getId()), b.getName()));
		}
		return data;
	}

}
