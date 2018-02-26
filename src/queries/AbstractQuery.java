package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractQuery<R> implements IQuery<R> {

	@Override
	public R execute(Connection con) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(getQueryDefinition());
			return parseResult(rs);
		}
	}

	protected abstract R parseResult(ResultSet rs) throws SQLException;

	protected abstract String getQueryDefinition();

}
