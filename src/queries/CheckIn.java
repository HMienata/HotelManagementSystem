package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckIn implements IQuery<Void> {

	private final int confirmID;

	private final int custID;

	private final int cost;

	public CheckIn(int confirmID, int custID, int cost) {
		this.confirmID = confirmID;
		this.custID = custID;
		this.cost = cost;
	}

	@Override
	public Void execute(Connection con) throws SQLException {
		boolean exists = new CheckReservationExists(confirmID, custID).execute(con);
		if (!exists) {
			throw new SQLException("Failed to find existing non expired reservation for the given customer");
		}
		new CheckInInsert().execute(con);
		con.commit();
		return null;
	}

	private class CheckInInsert extends AbstractQuery<Void> {

		@Override
		protected Void parseResult(ResultSet rs) throws SQLException {
			return null;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("INSERT into RentCost values (%d, %d)", confirmID, cost);
		}
	}

}
