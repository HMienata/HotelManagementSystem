package ui;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class QueryControl {

	private final String label;

	private final JFormattedTextField field;

	private QueryControl(String label, JFormattedTextField field) {
		this.label = label;
		this.field = field;
	}

	public String getLabel() {
		return label;
	}

	public JFormattedTextField getField() {
		return field;
	}

	public static QueryControl text(String label) {
		return new QueryControl(label, new JFormattedTextField());
	}

	public static QueryControl integer(String label) {
		NumberFormat intFormat = NumberFormat.getIntegerInstance();
		NumberFormatter numberFormatter = new NumberFormatter(intFormat) {
			private static final long serialVersionUID = 5312234052717937655L;

			@Override
			public Object stringToValue(String string) throws ParseException {
				if (string.isEmpty()) {
					return null;
				}
				return super.stringToValue(string);
			}
		};
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setValueClass(Integer.class);

		return new QueryControl(label, new JFormattedTextField(numberFormatter));
	}

	public static QueryControl date(String label) {
		try {
			MaskFormatter dateMask = new MaskFormatter("####-##-##");
			JFormattedTextField field = new JFormattedTextField(dateMask);
			return new QueryControl(label, field);
		} catch (ParseException e) {
			throw new IllegalStateException();
		}
	}

}
