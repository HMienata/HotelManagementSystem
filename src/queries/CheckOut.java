package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckOut extends AbstractQuery<Integer>{
	
	private int confirmationID;

	public CheckOut(int confirmationID) {
		this.confirmationID = confirmationID;
	}
	
	@Override
	public Integer execute(Connection con) throws SQLException {
		boolean rentalExists = new CheckRentalExists(confirmationID).execute(con);
		if (!rentalExists) {
			throw new SQLException("Failed to find existing rental for given confirmation number");
		}
		int cost = new GetRentalCost(confirmationID).execute(con);
		super.execute(con);
		new DeleteCorrespondingReservation(confirmationID).execute(con);
		con.commit();
		return cost;
	}

	@Override
	protected Integer parseResult(ResultSet rs) throws SQLException {
		return null;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format("DELETE FROM RentCost where ConfirmationID = %d", confirmationID);
	}
	
	
	private class CheckRentalExists extends AbstractQuery<Boolean> {


		private final int confirmID;


		public CheckRentalExists(int confirmID) {
			this.confirmID = confirmID;
		}

		@Override
		protected Boolean parseResult(ResultSet rs) throws SQLException {
			rs.next();
			return rs.getInt("Count") != 0;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("SELECT COUNT(*) AS COUNT FROM RentCost WHERE ConfirmationID = %d", confirmID);
		}

	}
	
	private class GetRentalCost extends AbstractQuery<Integer> {
		private final int confirmID;


		public GetRentalCost(int confirmID) {
			this.confirmID = confirmID;
		}

		@Override
		protected Integer parseResult(ResultSet rs) throws SQLException {
			rs.next();
			return rs.getInt("Totalcost");
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("SELECT TotalCost FROM RentCost WHERE ConfirmationID = %d", confirmID);
		}

	
	}

	private class DeleteCorrespondingReservation extends AbstractQuery<Void> {


		private final int confirmID;


		public DeleteCorrespondingReservation(int confirmID) {
			this.confirmID = confirmID;
		}

		@Override
		protected Void parseResult(ResultSet rs) throws SQLException {
			return null;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("DELETE FROM Reservation WHERE ConfirmationID = %d", confirmID);
		}

	}
}
