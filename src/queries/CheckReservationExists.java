package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class CheckReservationExists extends AbstractQuery<Boolean> {

	private final int confirmID;

	private final int custID;

	public CheckReservationExists(int confirmID, int custID) {
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
		String current = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		return String.format("SELECT COUNT(*) AS COUNT FROM Reservation WHERE ConfirmationID = %d AND CustomerID = %d "
				+ "AND to_date('%s', 'yyyymmdd') BETWEEN StartDate AND EndDate", confirmID, custID, current);
	}

}
