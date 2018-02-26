package components;

import java.sql.Connection;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import queries.CheckIn;
import queries.IQuery;
import ui.AbstractMenu;
import ui.QueryControl;

public class CheckInComponent extends AbstractQueryComponent<Void> {

	public CheckInComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.integer("Enter Confirmation Number: "),
				QueryControl.integer("Enter Customer ID: "), QueryControl.integer("Enter Rental Cost: ") };
	}

	@Override
	protected void displayData(List<List<String>> d) {
		JOptionPane.showMessageDialog(mainFrame, "Successfully checked in. Rental data was added to the database.",
				"Query Successful", JOptionPane.INFORMATION_MESSAGE);
		render();
	}

	@Override
	protected IQuery<Void> createQuery(JFormattedTextField[] textFields) {
		int confirmID = (int) textFields[0].getValue();
		int custID = (int) textFields[1].getValue();
		int cost = (int) textFields[2].getValue();
		return new CheckIn(confirmID, custID, cost);
	}

	@Override
	public String getDescription() {
		return "Check In";
	}

	@Override
	protected List<List<String>> parseData(Void t) {
		return null;
	}

}