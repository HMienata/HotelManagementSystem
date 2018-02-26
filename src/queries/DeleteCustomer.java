package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCustomer extends AbstractQuery<Void> {

	private final int customerID;

	public DeleteCustomer(int custID) {
		customerID = custID;
	}

	@Override
	public Void execute(Connection con) throws SQLException {
		super.execute(con);
		con.commit();
		return null;
	}

	@Override
	protected Void parseResult(ResultSet rs) throws SQLException {
		return null;
	}

	@Override
	protected String getQueryDefinition() {
		return String.format("DELETE FROM Customer where CustomerID = %d", customerID);
	}

}
