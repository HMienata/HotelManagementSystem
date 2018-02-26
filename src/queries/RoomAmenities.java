package queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.RoomWithAmenities;

public class RoomAmenities extends AbstractQuery<RoomWithAmenities> {

	private final int RoomNumber;
	private final String Street;
	private final String HouseNumber;
	private final String PostalCode;

	public RoomAmenities(int RoomNumber, String Street, String HouseNumber, String PostalCode) {
		this.RoomNumber = RoomNumber;
		this.Street = Street;
		this.HouseNumber = HouseNumber;
		this.PostalCode = PostalCode;
	}

	@Override
	protected RoomWithAmenities parseResult(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return new RoomWithAmenities(rs.getString("RoomType"), rs.getInt("RoomPrice"), rs.getInt("InternetAccess"),
					rs.getInt("Kitchen"), rs.getInt("SatelliteTV"));
		}
		throw new SQLException("Failed to find room with specified properties.");
	}

	@Override
	protected String getQueryDefinition() {
		return String.format(
				"SELECT t.TypeName AS RoomType, " + "t.Price As RoomPrice, " + "t.InternetAccess As InternetAccess, "
						+ "t.Kitchen As Kitchen, " + "t.SatelliteTV AS SatelliteTV FROM Room r, RoomType t "
						+ "WHERE r.RoomNumber = %d AND r.Street = '%s' AND r.HouseNumber = '%s' "
						+ "AND r.PostalCode = '%s' AND r.TypeName = t.TypeName",
				RoomNumber, Street, HouseNumber, PostalCode);
	}
}
