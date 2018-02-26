package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Reservation;

public class GetAllReservations extends AbstractQuery<List<Reservation>> {

	@Override
	protected List<Reservation> parseResult(ResultSet rs) throws SQLException {
		List<Reservation> reservations = new ArrayList<>();
		while (rs.next()) {
			Reservation r = new Reservation(rs.getInt("ConfirmationID"), 
					rs.getString("StartDate"), rs.getString("EndDate"), rs.getInt("RoomNumber"), rs.getString("Street"),
					rs.getString("HouseNumber"), rs.getString("PostalCode"), rs.getInt("CustomerID"));
			reservations.add(r);
		}
		return reservations;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format("SELECT * FROM Reservation");
	}

}
