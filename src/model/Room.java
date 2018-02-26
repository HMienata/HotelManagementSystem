package model;

public class Room {

	private final int roomNumber;

	private final int roomPrice;

	public Room(int roomNumber, int roomPrice) {
		this.roomNumber = roomNumber;
		this.roomPrice = roomPrice;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public int getRoomPrice() {
		return roomPrice;
	}

}
