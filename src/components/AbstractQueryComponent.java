package components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import queries.IQuery;
import ui.AbstractMenu;
import ui.QueryControl;
import ui.SpringUtilities;

public abstract class AbstractQueryComponent<T> implements ActionListener {

	private final Connection con;

	protected final JFrame mainFrame;

	private JFormattedTextField textFields[];

	private final AbstractMenu menu;

	public AbstractQueryComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		this.con = con;
		this.mainFrame = mainFrame;
		this.menu = menu;
	}

	public void render() {
		textFields = makeGrid(getFields());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals("Finish")) {
			if (checkForNull(textFields)) {
				mainFrame.dispose();
				render();
			} else {
				executeQuery();
			}
		} else if (cmd.equals("Return")) {
			mainFrame.dispose();
			menu.showMenu();
		}
	}

	protected JFormattedTextField[] makeGrid(QueryControl[] fields) {
		int numPairs = fields.length;
		JFormattedTextField j[] = new JFormattedTextField[numPairs];

		JPanel p = setUpLayout();

		for (int i = 0; i < numPairs; i++) {
			JLabel l = new JLabel(fields[i].getLabel(), JLabel.TRAILING);
			p.add(l);
			JFormattedTextField textField = fields[i].getField();
			j[i] = textField;
			l.setLabelFor(textField);
			p.add(textField);
		}
		JLabel l = new JLabel("  ", JLabel.TRAILING);
		p.add(l);
		JButton finish = new JButton("Finish");
		l.setLabelFor(finish);
		p.add(finish);

		JLabel ret = new JLabel("  ", JLabel.TRAILING);
		p.add(ret);
		JButton returnB = new JButton("Return to Menu");
		ret.setLabelFor(returnB);
		p.add(returnB);

		SpringUtilities.makeCompactGrid(p, numPairs + 2, 2, 6, 6, 6, 6);

		j[0].requestFocus();
		mainFrame.pack();
		finish.addActionListener(this);
		finish.setActionCommand("Finish");
		returnB.addActionListener(this);
		returnB.setActionCommand("Return");

		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);
		mainFrame.setVisible(true);
		return j;
	}

	public abstract String getDescription();

	protected abstract QueryControl[] getFields();

	protected abstract IQuery<T> createQuery(JFormattedTextField[] textFields);

	protected abstract List<List<String>> parseData(T t);

	protected void displayData(List<List<String>> data) {
		List<String> titles = data.remove(0);
		int cols = titles.size();
		int rows = data.size();

		JPanel p = new JPanel(new SpringLayout());
		JScrollPane scrPane = new JScrollPane(p);
		mainFrame.setContentPane(scrPane);
		//mainFrame.setContentPane(p);

		new ResultsTable(titles, data).addTable(p);

		for (int i = 0; i < cols - 1; i++) {
			JLabel text = new JLabel("  ", JLabel.TRAILING);
			p.add(text);
		}
		JButton returnB = new JButton("Return to Menu");
		p.add(returnB);

		SpringUtilities.makeCompactGrid(p, rows + 2, cols, 3, 3, 3, 3);

		mainFrame.pack();
		mainFrame.setVisible(true);
		returnB.addActionListener(this);
		returnB.setActionCommand("Return");
		Dimension d = mainFrame.getToolkit().getScreenSize();
		Rectangle r = mainFrame.getBounds();
		mainFrame.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);
	}

	private void executeQuery() {
		IQuery<T> query = createQuery(textFields);
		try {
			T results = query.execute(con);
			mainFrame.dispose();
			displayData(parseData(results));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(mainFrame,
					String.format("An error occurred during query execution:\n%s", e.getMessage()), "Query Error",
					JOptionPane.ERROR_MESSAGE);
			mainFrame.dispose();
			render();
		}
	}

	protected boolean checkForNull(JTextField j[]) {
		for (int i = 0; i < j.length; i++) {
			if (j[i].getText().equals(""))
				return true;
		}
		return false;
	}

	protected JPanel setUpLayout() {
		Insets insets = mainFrame.getInsets();
		mainFrame.setSize(new Dimension(insets.left + insets.right + 500, insets.top + insets.bottom + 500));
		Container contentPane = mainFrame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		mainFrame.setVisible(true);
		JPanel p = new JPanel(new SpringLayout());
		mainFrame.setContentPane(p);
		return p;
	}

}
