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

import components.AbstractQueryComponent;

public abstract class AbstractMenu {

	private final Connection con;

	private final JFrame mainFrame;

	public AbstractMenu(Connection con, JFrame mainFrame) {
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

		for (AbstractQueryComponent<?> comp : getComponents(con, mainFrame)) {
			JButton button = new JButton(comp.getDescription());
			gb.setConstraints(button, c);
			contentPane.add(button);
			button.addActionListener((e) -> {
				mainFrame.dispose();
				comp.render();
			});
		}

		JButton b = new JButton("Change user type");
		gb.setConstraints(b, c);
		contentPane.add(b);
		b.addActionListener((e) -> {
			mainFrame.dispose();
			new MainMenu(con, mainFrame).showMenu();
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

	protected abstract AbstractQueryComponent<?>[] getComponents(Connection con, JFrame mainFrame);
}
