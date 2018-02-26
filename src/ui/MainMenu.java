package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu{

	private final Connection con;

	private final JFrame mainFrame;

	public MainMenu(Connection con, JFrame mainFrame) {
		this.con = con;
		this.mainFrame = mainFrame;
	}

	public void showMenu() {
		JPanel contentPane = new JPanel();
		mainFrame.setContentPane(contentPane);
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;

		JButton button1 = new JButton("Customer");
		gb.setConstraints(button1, c);
		contentPane.add(button1);
		button1.addActionListener((e) -> {
			mainFrame.dispose();
			new CustomerMenu(con, mainFrame).showMenu();
		});

		JButton button2 = new JButton("Manager");
		gb.setConstraints(button2, c);
		contentPane.add(button2);
		button2.addActionListener((e) -> {
			mainFrame.dispose();
			new ManagerMenu(con, mainFrame).showMenu();
		});

		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		mainFrame.pack();

		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

		mainFrame.setVisible(true);
	}
}
