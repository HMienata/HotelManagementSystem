package components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;

import model.Room;
import queries.IQuery;
import queries.MinOrMaxPricedRoom;
import ui.AbstractMenu;
import ui.QueryControl;

public class MinOrMaxRoomComponent extends AbstractQueryComponent<List<Room>> {
	JRadioButton MaxButton;
	JRadioButton MinButton;

	public MinOrMaxRoomComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.text("Enter House Number: "), QueryControl.text("Enter Street: "),
				QueryControl.text("Enter Postal Code: ") };
	}

	@Override
	protected IQuery<List<Room>> createQuery(JFormattedTextField[] textFields) {
		String houseNo = textFields[0].getText();
		String street = textFields[1].getText();
		String postalCode = textFields[2].getText();
		boolean isMax = MaxButton.isSelected();
		return new MinOrMaxPricedRoom(isMax, street, houseNo, postalCode);
	}

	@Override
	public String getDescription() {
		return "Query Most/Least Expensive Room In Branch";
	}

	@Override
	protected List<List<String>> parseData(List<Room> t) {
		List<List<String>> data = new ArrayList<List<String>>();
		data.add(Arrays.asList("Room Number", "Room Price"));
		for (int i = 0; i < t.size(); i++) {
			Room b = t.get(i);
			data.add(Arrays.asList(Integer.toString(b.getRoomNumber()), Integer.toString(b.getRoomPrice())));
		}
		return data;
	}

	@Override
	protected JFormattedTextField[] makeGrid(QueryControl[] fields) {
		int numFields = fields.length;
		JFormattedTextField j[] = new JFormattedTextField[numFields];

		Insets insets = mainFrame.getInsets();
		mainFrame.setSize(new Dimension(insets.left + insets.right + 500, insets.top + insets.bottom + 500));
		Container contentPane = mainFrame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		JPanel p = new JPanel(new GridLayout(0, 2));
		mainFrame.setContentPane(p);

		for (int i = 0; i < numFields; i++) {
			JLabel l = new JLabel(fields[i].getLabel(), JLabel.TRAILING);
			p.add(l);
			JFormattedTextField textField = fields[i].getField();
			j[i] = textField;
			l.setLabelFor(textField);
			p.add(textField);
		}

		JLabel k = new JLabel("What would you like to retrieve?");
		p.add(k);

		MaxButton = new JRadioButton("Max Price");
		MinButton = new JRadioButton("Min Price");

		MaxButton.setSelected(true);

		p.add(MaxButton);
		k = new JLabel(" ");
		p.add(k);
		p.add(MinButton);

		MaxButton.addActionListener(this);
		MinButton.addActionListener(this);

		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(MaxButton);
		bg1.add(MinButton);

		k = new JLabel(" ");
		p.add(k);
		JButton finish = new JButton("Finish");
		p.add(finish);
		finish.addActionListener(this);
		finish.setActionCommand("Finish");

		k = new JLabel(" ");
		p.add(k);
		JButton returnB = new JButton("Return to Menu");
		p.add(returnB);
		returnB.addActionListener(this);
		returnB.setActionCommand("Return");

		p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainFrame.pack();
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);
		mainFrame.setVisible(true);

		return j;
	}

}
