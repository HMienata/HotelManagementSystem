package ui;

import java.sql.Connection;

import javax.swing.JFrame;

import components.AbstractQueryComponent;
import components.AggregateBranchPriceComponent;
import components.AggregateManagerSalariesComponent;
import components.CustomerAllRoomsComponent;
import components.CustomerInfoComponent;
import components.DeleteCustomerComponent;
import components.LateCheckOutComponent;
import components.MinOrMaxRoomComponent;
import components.ModifyReservationComponent;
import components.RoomAmenitiesComponent;
import components.RoomPriceComponent;

public class ManagerMenu extends AbstractMenu {

	public ManagerMenu(Connection con, JFrame mainFrame) {
		super(con, mainFrame);
	}

	@Override
	protected AbstractQueryComponent<?>[] getComponents(Connection con, JFrame mainFrame) {
		return new AbstractQueryComponent<?>[] { new ModifyReservationComponent(con, mainFrame, this),
				new RoomAmenitiesComponent(con, mainFrame, this), new RoomPriceComponent(con, mainFrame, this),
				new LateCheckOutComponent(con, mainFrame, this), new CustomerInfoComponent(con, mainFrame, this),
				new CustomerAllRoomsComponent(con, mainFrame, this), new MinOrMaxRoomComponent(con, mainFrame, this),
				new AggregateBranchPriceComponent(con, mainFrame, this), new DeleteCustomerComponent(con, mainFrame, this),
				new AggregateManagerSalariesComponent(con, mainFrame, this)};
	}

}
