package components;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import model.Reservation;
import queries.IQuery;
import queries.MakeReservation;
import ui.AbstractMenu;
import ui.QueryControl;

public class ReserveRoomComponent extends AbstractQueryComponent<Reservation> {

	public ReserveRoomComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.date("Enter Start Date (yyyy-mm-dd)"),
				QueryControl.date("Enter End Date (yyyy-mm-dd): "), QueryControl.integer("Enter Room Number: "),
				QueryControl.text("Enter Street: "), QueryControl.text("Enter House Number: "),
				QueryControl.text("Enter Postal Code: "), QueryControl.integer("Enter Customer ID: ") };
	}

	@Override
	protected IQuery<Reservation> createQuery(JFormattedTextField[] textFields) {
		String StartDate = textFields[0].getText().replace("-", "");
		String EndDate = textFields[1].getText().replace("-", "");
		int RoomNumber = (int) textFields[2].getValue();
		String Street = textFields[3].getText();
		String HouseNumber = textFields[4].getText();
		String PostalCode = textFields[5].getText();
		int CustomerID = (int) textFields[6].getValue();

		return new MakeReservation(StartDate, EndDate, RoomNumber, Street, HouseNumber, PostalCode, CustomerID);
	}

	@Override
	public String getDescription() {
		return "Make a Reservation";
	}

	@Override
	protected List<List<String>> parseData(Reservation t) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList("ConfirmationID", "StartDate", "EndDate", "RoomNumber", "Street", "HouseNumber",
				"PostalCode", "CustomerID"));
		data.add(Arrays.asList(Integer.toString(t.getConfirmationID()), t.getStartDate(), t.getEndDate(),
				Integer.toString(t.getRoomNumber()), t.getStreet(), t.getHouseNumber(), t.getPostalCode(),
				Integer.toString(t.getCustomerID())));
		return data;
	}
}
