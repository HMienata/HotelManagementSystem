package queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Room;

public class RoomAboveOrBelowPrice extends AbstractQuery<List<Room>>{

	private final int BorderPrice;
	private boolean above;
	private final String Street;
	private final String HouseNumber;
	private final String PostalCode;
	
	public RoomAboveOrBelowPrice(int BorderPrice, boolean above, String Street, String HouseNumber, String PostalCode) {
		this.BorderPrice = BorderPrice;
		this.above = above;
		this.Street =  Street;
		this.HouseNumber = HouseNumber;
		this.PostalCode = PostalCode;
	}

	@Override
	protected List<Room> parseResult(ResultSet rs) throws SQLException {
		List<Room> rooms = new ArrayList<>();
		while (rs.next()) {
			rooms.add(new Room(rs.getInt("RoomNumber"), rs.getInt("Price")));
		}
		return rooms;
	}


	@Override
	protected String getQueryDefinition() {
		char sort = above ? '>' : '<';
		return String.format(
				"SELECT r.RoomNumber, t.Price FROM Room r, RoomType t "
				+ "WHERE r.Street = '%s' AND r.HouseNumber = '%s' "
				+ "AND r.PostalCode = '%s' AND r.TypeName = t.TypeName "
				+ "AND t.Price %c %d",
				Street, HouseNumber, PostalCode, sort, BorderPrice);
	}
}
