package queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AggregateOperation;
import model.BranchLocation;
import model.NestedBranchPrice;

public class AggregatePriceByBranch implements IQuery<NestedBranchPrice> {

	private final AggregateOperation first;

	private final AggregateOperation second;

	public AggregatePriceByBranch(AggregateOperation first, AggregateOperation second) {
		this.first = first;
		this.second = second;

	}

	@Override
	public NestedBranchPrice execute(Connection con) throws SQLException {
		List<BranchLocation> branches = new FirstAggregation().execute(con);
		float price = new SecondAggregation().execute(con);
		return new NestedBranchPrice(branches, price);
	}

	private class FirstAggregation extends AbstractQuery<List<BranchLocation>> {

		@Override
		protected List<BranchLocation> parseResult(ResultSet rs) throws SQLException {
			List<BranchLocation> locations = new ArrayList<>();
			while (rs.next()) {
				locations.add(new BranchLocation(rs.getString("Street"), rs.getString("HouseNumber"),
						rs.getString("PostalCode"), rs.getFloat("AggregatePrice")));
			}
			return locations;
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("SELECT Street, HouseNumber, PostalCode, %s(TotalCost) AS AggregatePrice "
					+ "FROM RentCost INNER JOIN Reservation ON RentCost.ConfirmationID = Reservation.ConfirmationID "
					+ "GROUP BY Street, HouseNumber, PostalCode", first);
		}
	}

	private class SecondAggregation extends AbstractQuery<Float> {

		@Override
		protected Float parseResult(ResultSet rs) throws SQLException {
			rs.next();
			return rs.getFloat("Price");
		}

		@Override
		protected String getQueryDefinition() {
			return String.format("SELECT %s(AggregatePrice) AS Price FROM " + "(SELECT %s(TotalCost) AS AggregatePrice "
					+ "FROM RentCost INNER JOIN Reservation ON RentCost.ConfirmationID = Reservation.ConfirmationID "
					+ "GROUP BY Street, HouseNumber, PostalCode)", second, first);
		}

	}

}
