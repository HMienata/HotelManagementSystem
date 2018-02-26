package model;

public class RoomWithAmenities {

	private final String roomType;
	private final int roomPrice;
	private final int InternetAccess;
	private final int Kitchen;
	private final int SatelliteTV;

	public RoomWithAmenities(String roomType, int roomPrice, int InternetAccess, int Kitchen, int SatelliteTV) {
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.InternetAccess = InternetAccess;
		this.Kitchen = Kitchen;
		this.SatelliteTV = SatelliteTV;
	}

	public String getRoomType() {
		return roomType;
	}

	public int getRoomPrice() {
		return roomPrice;
	}

	public int getInternetAccess() {
		return InternetAccess;
	}

	public int getKitched() {
		return Kitchen;
	}

	public int getSatelliteTV() {
		return SatelliteTV;
	}
}
