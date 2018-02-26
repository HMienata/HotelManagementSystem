package queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.AggregateOperation;

public class AggregateManagerSalaries extends AbstractQuery<Float> {

	private final AggregateOperation aggregate;

	public AggregateManagerSalaries(AggregateOperation aggregate) {
		this.aggregate = aggregate;
	}

	@Override
	protected Float parseResult(ResultSet rs) throws SQLException {
		rs.next();
		return rs.getFloat("AggregatePrice");
	}

	@Override
	protected String getQueryDefinition() {
		return String.format(
				"SELECT %s(Salary) AS AggregatePrice FROM Manager", aggregate);
	}

}
