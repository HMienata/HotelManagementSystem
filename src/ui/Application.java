package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//We need to import the java.sql package to use JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//for the login window
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Application implements ActionListener {

	private static final boolean USE_LOCAL = true;

	private Connection con;

	private int loginAttempts = 3;

	private final JTextField usernameField;

	private final JPasswordField passwordField;

	private final JLabel errorLabel;

	private final JFrame mainFrame;

	/*
	 * constructs login window and loads JDBC driver
	 */
	public Application() {
		mainFrame = new JFrame("Hotel Application");

		new SpringUtilities().setUIFont(new javax.swing.plaf.FontUIResource("Serif",Font.ROMAN_BASELINE,22));
		
		JLabel usernameLabel = new JLabel("Enter username: ");
		JLabel passwordLabel = new JLabel("Enter password: ");

		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		passwordField.setEchoChar('*');

		JButton loginButton = new JButton("Log In");

		errorLabel = new JLabel(" ");
		errorLabel.setForeground(Color.RED);

		JPanel contentPane = new JPanel();
		mainFrame.setContentPane(contentPane);

		// layout components using the GridBag layout manager

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// place the username label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(usernameLabel, c);
		contentPane.add(usernameLabel);

		// place the text field for the username
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		contentPane.add(usernameField);

		// place password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		contentPane.add(passwordLabel);

		// place the password field
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		contentPane.add(passwordField);

		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		contentPane.add(loginButton);

		// place the error label
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 10, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(errorLabel, c);
		contentPane.add(errorLabel);

		// register password field and OK button with action event handler
		passwordField.addActionListener(this);
		loginButton.addActionListener(this);
		loginButton.setActionCommand("Log");

		// anonymous inner class for closing the window
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// size the window to obtain a best fit for the components
		mainFrame.pack();

		// center the frame
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);

		// make the window visible
		mainFrame.setVisible(true);

		// place the cursor in the text field for the username
		usernameField.requestFocus();

		try {
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(mainFrame, "Failed to register drivers. The application will now terminate.",
					"Setup Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	/*
	 * connects to Oracle database named ug using user supplied username and
	 * password
	 */
	private boolean connect(String username, String password) {
		String connectURL;
		if (USE_LOCAL)
			connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
		else
			connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";

		try {
			con = DriverManager.getConnection(connectURL, username, password);
			con.setAutoCommit(false);
			return true;
		} catch (SQLException ex) {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Log")) {
			if (connect(usernameField.getText(), String.valueOf(passwordField.getPassword()))) {
				mainFrame.dispose();
				new MainMenu(con, mainFrame).showMenu();
			} else {
				loginAttempts--;
				String s = loginAttempts != 1 ? "s" : "";
				errorLabel.setText(String.format("Login failed. %d attempt%s remaining.", loginAttempts, s));

				if (loginAttempts == 0) {
					JOptionPane.showMessageDialog(mainFrame,
							"Maximum login attempt reached. The application will now terminate.", "Login Error",
							JOptionPane.ERROR_MESSAGE);
					mainFrame.dispose();
					System.exit(-1);
				} else {
					// clear the password
					passwordField.setText("");
				}
			}
		}
	}

	public static void main(String args[]) {
		new Application();
	}

}
