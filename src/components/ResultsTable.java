package components;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ResultsTable {

	private final List<String> titles;

	private final List<List<String>> data;

	public ResultsTable(List<String> titles, List<List<String>> data) {
		this.titles = titles;
		this.data = data;
	}

	public void addTable(JPanel p) {
		int cols = titles.size();
		int rows = data.size();
		for (int i = 0; i < cols; i++) {
			JLabel text = new JLabel(titles.get(i));
			Border paddingBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
			Border border = BorderFactory.createLineBorder(Color.BLUE);
			text.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
			text.setOpaque(true);
			text.setBackground(Color.WHITE);
			p.add(text);
		}

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				JLabel text = new JLabel(data.get(r).get(c));
				text.setOpaque(true);
				text.setBackground(Color.WHITE);
				Border paddingBorder = BorderFactory.createEmptyBorder(8, 8, 8, 8);
				Border border = BorderFactory.createLineBorder(Color.GRAY);
				text.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
				p.add(text);
			}
		}
	}

}
