package queries;

import java.sql.ResultSet;
import java.sql.SQLException;

class CheckBranchExists extends AbstractQuery<Boolean> {

	private final String street;

	private final String houseNo;

	private final String postalCode;

	public CheckBranchExists(String street, String houseNo, String postalCode) {
		this.street = street;
		this.houseNo = houseNo;
		this.postalCode = postalCode;
	}

	@Override
	protected Boolean parseResult(ResultSet rs) throws SQLException {
		rs.next();
		return rs.getInt("Count") != 0;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format(
				"SELECT COUNT(*) AS COUNT FROM Branch "
						+ "WHERE Street = '%s' AND HouseNumber = '%s' AND PostalCode = '%s'",
				street, houseNo, postalCode);
	}

}
