package components;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import model.Reservation;
import queries.GetAllReservations;
import queries.IQuery;
import queries.ModifyReservation;
import ui.AbstractMenu;
import ui.QueryControl;

public class ModifyReservationComponent extends AbstractQueryComponent<List<Reservation>> {

	public ModifyReservationComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.date("Enter New Check-In Date (yyyy-mm-dd): "),
				QueryControl.date("Enter New Check-out Date (yyyy-mm-dd): "),
				QueryControl.integer("Enter Confirmation Number: "), QueryControl.integer("Enter Customer ID: ") };
	}

	@Override
	protected IQuery<List<Reservation>> createQuery(JFormattedTextField[] textFields) {
		String CheckIn = textFields[0].getText().replace("-", "");
		String Checkout = textFields[1].getText().replace("-", "");
		int ConfirmationID = (int) textFields[2].getValue();
		int custID = (int) textFields[3].getValue();

		return (con) -> {
			new ModifyReservation(CheckIn, Checkout, ConfirmationID, custID).execute(con);
			return new GetAllReservations().execute(con);
		};
	
	}

	@Override
	public String getDescription() {
		return "Modify Reservation";
	}

	@Override
	protected List<List<String>> parseData(List<Reservation> t) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList("ConfirmationID", "StartDate", "EndDate", "RoomNumber", "Street", "HouseNumber",
				"PostalCode", "CustomerID"));
		for (int i = 0; i < t.size(); i++) {
			Reservation r = t.get(i);
			data.add(Arrays.asList(Integer.toString(r.getConfirmationID()), r.getStartDate(), r.getEndDate(),
					Integer.toString(r.getRoomNumber()), r.getStreet(), r.getHouseNumber(), r.getPostalCode(),
					Integer.toString(r.getCustomerID())));
		}
		return data;
	}

}
