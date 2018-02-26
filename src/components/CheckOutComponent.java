package components;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import queries.CheckOut;
import queries.IQuery;
import ui.AbstractMenu;
import ui.QueryControl;

public class CheckOutComponent extends AbstractQueryComponent<Integer> {

	public CheckOutComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.integer("Enter Confirmation Number:  ") };
	}

	@Override
	protected IQuery<Integer> createQuery(JFormattedTextField[] textFields) {
		int ConfirmationID = (int) textFields[0].getValue();
		return new CheckOut(ConfirmationID);
	}

	@Override
	public String getDescription() {
		return "Check Out";
	}

	@Override
	protected List<List<String>> parseData(Integer t) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList(Integer.toString(t)));
		return data;
	}

	@Override
	protected void displayData(List<List<String>> t) {
		String message = String.format("Successful checked out. The cost of your stay was: %s", t.get(0).get(0));
		JOptionPane.showMessageDialog(mainFrame, message, "Query Successful", JOptionPane.INFORMATION_MESSAGE);
		render();
	}

}
