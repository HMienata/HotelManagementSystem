package components;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import model.RoomWithAmenities;
import queries.IQuery;
import queries.RoomAmenities;
import ui.AbstractMenu;
import ui.QueryControl;

public class RoomAmenitiesComponent extends AbstractQueryComponent<RoomWithAmenities> {

	public RoomAmenitiesComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.integer("Enter Room Number:  "), QueryControl.text("Enter Street: "),
				QueryControl.text("Enter House Number: "), QueryControl.text("Enter Postal Code: ") };
	}

	@Override
	protected IQuery<RoomWithAmenities> createQuery(JFormattedTextField[] textFields) {
		int RoomNumber = (int) textFields[0].getValue();
		String Street = textFields[1].getText();
		String HouseNumber = textFields[2].getText();
		String PostalCode = textFields[3].getText();
		return new RoomAmenities(RoomNumber, Street, HouseNumber, PostalCode);
	}

	@Override
	public String getDescription() {
		return "Query Room Amenities";
	}

	@Override
	protected List<List<String>> parseData(RoomWithAmenities t) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList("RoomType", "RoomPrice", "InternetAccess", "Kitchen", "SatelliteTV"));
		data.add(
				Arrays.asList(t.getRoomType(), Integer.toString(t.getRoomPrice()), formatAmenity(t.getInternetAccess()),
						formatAmenity(t.getKitched()), formatAmenity(t.getSatelliteTV())));
		return data;
	}

	private String formatAmenity(int included) {
		return included == 1 ? "Included" : "Not included";
	}

}
