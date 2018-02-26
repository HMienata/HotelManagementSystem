package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import model.Reservation;

public class ModifyReservation extends AbstractQuery<Reservation>{
	
	private String checkIn;
	private String checkout;
	private int confirmationID;
	private int custID;

	public ModifyReservation(String checkIn, String checkout, int confirmationID, int custID) {
		this.checkIn = checkIn;
		this.checkout = checkout;
		this.confirmationID = confirmationID;
		this.custID = custID;
	}

	@Override
	public Reservation execute(Connection con) throws SQLException {
		boolean exists = new FindReservation(confirmationID, custID).execute(con);
		if (!exists) {
			throw new SQLException("Failed to find reservation with given Confirmation and Customer ID's");
		}
		// Call checkDates helper to determine if start time after current time
		boolean timeGood = checkDates();
		if(!timeGood) {
			throw new SQLException("Error: Check In time must be after Current time");
		}
		super.execute(con);
		con.commit();
		Reservation r = new GetCorrespondingReservation(confirmationID).execute(con);
		return r;
	}
	
	@Override
	protected Reservation parseResult(ResultSet rs) throws SQLException {
		return null;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format("UPDATE Reservation SET StartDate = to_date(%s, 'yyyymmdd'), "
				+ "EndDate = to_date(%s, 'yyyymmdd') WHERE ConfirmationID = %d", checkIn, checkout, confirmationID);
	}
	
	
	// Since we cannot perform a check on the current time without a trigger in SQL, we 
	// create a helper which checks that the input startDate is before the current date.
	protected boolean checkDates() {
		GregorianCalendar startDate = new GregorianCalendar(Integer.parseInt(checkIn.substring(0, 4)),
				Integer.parseInt(checkIn.substring(4, 6)),
				Integer.parseInt(checkIn.substring(6, 8)));
		if(!startDate.after(new GregorianCalendar())) {
			return false;
		}
		return true;
		
	}
	
	
	private class GetCorrespondingReservation  extends AbstractQuery<Reservation>{

		private int confID;
		
		private GetCorrespondingReservation(int confirmationID) {
			confID = confirmationID;
		}
		
		@Override
		protected Reservation parseResult(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			rs.next();
			Reservation r = new Reservation(rs.getInt("ConfirmationID"), 
					rs.getString("StartDate"), rs.getString("EndDate"), rs.getInt("RoomNumber"), rs.getString("Street"),
					rs.getString("HouseNumber"), rs.getString("PostalCode"), rs.getInt("CustomerID"));
			return r;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("SELECT * FROM Reservation WHERE ConfirmationID = %d", confID);
		}
		
	}

	class FindReservation extends AbstractQuery<Boolean> {

		private final int confirmID;

		private final int custID;

		public FindReservation(int confirmID, int custID) {
			this.confirmID = confirmID;
			this.custID = custID;
		}

		@Override
		protected Boolean parseResult(ResultSet rs) throws SQLException {
			rs.next();
			return rs.getInt("Count") != 0;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("SELECT COUNT(*) AS COUNT FROM Reservation "
					+ "WHERE ConfirmationID = %d AND CustomerID = %d", confirmID, custID);
		}

	}


}

