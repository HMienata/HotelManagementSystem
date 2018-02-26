package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class LateCheckOut extends AbstractQuery<List<Customer>>{
	
	private String date;
	private int managerID;

	public LateCheckOut(String date, int managerID) {
		this.date = date;
		this.managerID = managerID;
	}
	
	@Override
	public List<Customer> execute(Connection con) throws SQLException {
		boolean branchExists = new CheckManagedBranchExists(managerID).execute(con);
		if (!branchExists) {
			throw new SQLException("Failed to find existing branch for given Manager ID");
		}
		return super.execute(con);
	}

	@Override
	protected List<Customer> parseResult(ResultSet rs) throws SQLException {
		List<Customer> lateCustomers = new ArrayList<>();
		while(rs.next()) {
			lateCustomers.add(new Customer(rs.getInt("CustomerID"), rs.getString("Name")));
		}
		return lateCustomers;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format("SELECT c.CustomerID, c.Name "
				+ "FROM Manager m INNER JOIN Reservation r ON m.Street = r.Street AND m.HouseNumber = r.HouseNumber AND m.PostalCode = r.PostalCode "
				+ "INNER JOIN Customer c ON c.CustomerID = r.CustomerID "
				+ "WHERE m.ManagerID = %s AND r.EndDate <= to_date(%s, 'yyyymmdd')", managerID, date);
	}
	
	private class CheckManagedBranchExists extends AbstractQuery<Boolean> {

		private int managerID;

		public CheckManagedBranchExists(int managerID) {
			this.managerID = managerID;
		}

		@Override
		protected Boolean parseResult(ResultSet rs) throws SQLException {
			rs.next();
			return rs.getInt("Count") != 0;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format(
					"SELECT COUNT(*) AS COUNT FROM Manager m INNER JOIN Branch b ON m.Street = b.Street AND "
					+ "m.HouseNumber = b.HouseNumber AND m.PostalCode = b.PostalCode "
							+ "WHERE m.ManagerID = %d",
					managerID);
		}

	}

}
