package components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import model.AggregateOperation;
import queries.AggregateManagerSalaries;
import queries.IQuery;
import ui.AbstractMenu;
import ui.QueryControl;

public class AggregateManagerSalariesComponent extends AbstractQueryComponent<Float> {
	private JComboBox<AggregateOperation> aggregation;

	public AggregateManagerSalariesComponent(Connection con, JFrame mainFrame, AbstractMenu menu) {
		super(con, mainFrame, menu);
	}

	@Override
	protected QueryControl[] getFields() {
		return new QueryControl[] { QueryControl.text("What would you like to aggregate by?") };
	}

	@Override
	protected IQuery<Float> createQuery(JFormattedTextField[] textFields) {
		AggregateOperation a = (AggregateOperation) aggregation.getSelectedItem();
		return new AggregateManagerSalaries(a);
	}

	@Override
	protected JFormattedTextField[] makeGrid(QueryControl[] fields) {
		Insets insets = mainFrame.getInsets();
		mainFrame.setSize(new Dimension(insets.left + insets.right + 500, insets.top + insets.bottom + 500));
		Container contentPane = mainFrame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		mainFrame.setVisible(true);
		JPanel p = new JPanel(new GridLayout(0, 1));
		mainFrame.setContentPane(p);

		JLabel l = new JLabel(fields[0].getLabel());
		p.add(l);
		aggregation = createCombo();
		p.add(aggregation);

		JButton finish = new JButton("Finish");
		p.add(finish);
		finish.addActionListener(this);
		finish.setActionCommand("Finish");

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

		return null;
	}

	@Override
	protected List<List<String>> parseData(Float t) {
		List<List<String>> data = new ArrayList<List<String>>();
		List<String> s = new ArrayList<String>();
		s.add(Float.toString(t));
		data.add(s);
		return data;
	}
	
	@Override
	protected void displayData(List<List<String>> d) {
		String s = String.format("Aggregation result: %s", d.get(0).get(0));
		JOptionPane.showMessageDialog(mainFrame, s,
				"Query Successful", JOptionPane.INFORMATION_MESSAGE);
		render();
	}

	@Override
	public String getDescription() {
		return "Aggregate Manager Salaries";
	}
	
	private JComboBox<AggregateOperation> createCombo() {
		JComboBox<AggregateOperation> combo = new JComboBox<>();
		for (AggregateOperation a : AggregateOperation.values()) {
			combo.addItem(a);
		}
		return combo;
	}
	
	@Override
	protected boolean checkForNull(JTextField j[]) {
		return false;
	}
}
